package kraang.service;

import db.entity.IdentifierEntity;
import db.entity.ServiceIdentifierBaseEntity;
import db.entity.ServiceIdentifierEntity;
import db.enums.IdentifierStatusEnm;
import db.kraang.entity.IdentifierLockEntity;
import db.kraang.repository.IdentifierLockRepository;
import db.repository.IdentifierRepository;
import db.repository.ServiceIdentifierBaseRepository;
import db.repository.ServiceIdentifierRepository;
import kraang.controller.dto.AvailableIdentifierDto;
import kraang.controller.dto.CreateSpecifiedIdentifierDto;
import kraang.exception.KraangException;
import kraang.service.dto.IdentifierDto;
import kraang.util.Utils;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static db.enums.ServiceIdentifierStatusEnm.A;
import static java.lang.String.valueOf;

@Service
@AllArgsConstructor
public class IdentifierService {
  private static final Logger logger = LoggerFactory.getLogger(IdentifierService.class);
  private final IdentifierRepository identifierRepository;
  private final ServiceIdentifierBaseRepository serviceIdentifiersBaseRepository;
  private final ServiceIdentifierRepository serviceIdentifierRepository;
  private final IdentifierClassService identifierClassService;
  private final IdentifierGroupService identifierGroupService;
  private final MnpRegionService mnpRegionService;
  private final IdentifierLockRepository identifierLockRepository;
  private final ConversionService conversionService;

  public IdentifierDto createSpecifiedIdentifier(CreateSpecifiedIdentifierDto newIdentifier) {
    var identifierEntity = new IdentifierEntity();
    identifierEntity.setId(identifierRepository.fwIdentifiersNext());
    identifierEntity.setIdServiceIdentifier(4);
    identifierEntity.setMsisdn(valueOf(newIdentifier.getIdentifier()));
    identifierEntity.setStatus(IdentifierStatusEnm.N);
    identifierEntity.setExtIdent(identifierEntity.getId());
    identifierEntity.setMnpRegion(mnpRegionService.findByRegionCode(newIdentifier.getMnpRegionCode()));
    identifierEntity.setDeleted(false);

    var serviceIdentifierEntity = new ServiceIdentifierEntity();
    serviceIdentifierEntity.setIdServiceIdentifierInst(serviceIdentifierRepository.fwServiceIdentifiersNext());
    serviceIdentifierEntity.setIdServiceIdentifier(4L);
    var currentDateTime = LocalDateTime.now();
    serviceIdentifierEntity.setStart(currentDateTime);
    serviceIdentifierEntity.setUpdated(currentDateTime);
    serviceIdentifierEntity.setStop(Utils.FORWARD_INFINITY);
    serviceIdentifierEntity.setDeleted(false);
    serviceIdentifierEntity.setIdManager(2);
    serviceIdentifierEntity.setBase(false);
    serviceIdentifierEntity.setExtIdent(valueOf(serviceIdentifierEntity.getIdServiceIdentifierInst()));
    serviceIdentifierEntity.setIdentifierClass(
            identifierClassService.findByDeletedFalseAndId(newIdentifier.getIdentifierClass()));
    serviceIdentifierEntity.setIdentifierGroup(
            identifierGroupService.findByDeletedFalseAndId(newIdentifier.getIdentifierGroup()));
    serviceIdentifierEntity.setStatus(A);

    var serviceIdentifiersBaseEntity = new ServiceIdentifierBaseEntity();
    serviceIdentifiersBaseEntity.setIdServiceIdentifierInst(serviceIdentifierEntity.getIdServiceIdentifierInst());

    identifierEntity = identifierRepository.save(identifierEntity);
    serviceIdentifierEntity.setIdentifier(identifierEntity);

    serviceIdentifiersBaseRepository.save(serviceIdentifiersBaseEntity);
    serviceIdentifierRepository.save(serviceIdentifierEntity);

    var identifierDto = new IdentifierDto();
    identifierDto.setMsisdn(identifierEntity.getMsisdn());
    identifierDto.setIdIdentifier(identifierEntity.getId());
    identifierDto.setRegionCode(identifierEntity
            .getMnpRegion()
            .getRegionCode());
    return identifierDto;
  }

  public List<IdentifierDto> createIdentifierList(List<CreateSpecifiedIdentifierDto> identifierDtoList) {
    return identifierDtoList
            .parallelStream()
            .map(dto -> createSpecifiedIdentifier(dto))
            .collect(Collectors.toList());
  }

  public List<IdentifierDto> getAndLockAvailableIdentifier(AvailableIdentifierDto availableIdentifierDto) {
    List<IdentifierDto> result = new ArrayList();
    var mnpRegion = mnpRegionService.findByRegionCode(availableIdentifierDto.getMnpRegionCode());
    while (result.size() != availableIdentifierDto.getIdentifierCount()) {
      var availableIdentifiers = getAvailableIdentifiers(
              availableIdentifierDto.getIdentifierCount(),
              mnpRegion.getId(),
              Long.valueOf(availableIdentifierDto.getIdentifierClass()),
              Long.valueOf(availableIdentifierDto.getIdentifierGroup()),
              String.valueOf(availableIdentifierDto.getRangePrefix()));
      for (var availableIdentifier : availableIdentifiers) {
        if (lockIdentifier(availableIdentifier.getIdentifier().getMsisdn(),
                LocalDateTime.now().plusSeconds(availableIdentifierDto.getTimeToLockSeconds()))) {
          result.add(conversionService.convert(availableIdentifier, IdentifierDto.class));
        }
      }
    }
    return result;
  }

  private List<ServiceIdentifierEntity> getAvailableIdentifiers(long identifierCount,
                                                                long mnpRegionId,
                                                                long identifierClass,
                                                                long identifierGroup,
                                                                String rangePrefix) {
    var notAvailableMsisdn = identifierLockRepository.findAll()
            .stream()
            .map(IdentifierLockEntity::getMsisdn)
            .collect(Collectors.toList());
    notAvailableMsisdn.add("123");

    var result = serviceIdentifierRepository.getUnusedIdentifiers(LocalDateTime.now(),
            LocalDateTime.now(),
            identifierClass,
            identifierGroup,
            mnpRegionId,
            rangePrefix,
            A,
            notAvailableMsisdn,
            identifierCount);

    if (result.size() < identifierCount) {
      throw new KraangException("В регионе " + mnpRegionId + " недостаточно свободной номерной емкости");
    }
    return result;
  }

  private boolean lockIdentifier(String msisnd, LocalDateTime removeLock) {
    try {
      identifierLockRepository.lockSimCard(msisnd, removeLock);
    } catch (ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Error lockIdentifier {},", msisnd, e);
      return false;
    }
    logger.debug("lockIdentifier {}", msisnd);
    return true;
  }
}
