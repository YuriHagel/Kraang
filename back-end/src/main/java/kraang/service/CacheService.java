package kraang.service;

import db.entity.IdentifierClassEntity;
import db.entity.IdentifierGroupEntity;
import db.entity.MnpRegionEntity;
import db.repository.IdentifierClassRepository;
import db.repository.IdentifierGroupRepository;
import db.repository.MnpRegionRepository;
import db.repository.SimCardRepository;
import kraang.controller.dto.CreateSpecifiedSimCardResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kraang.service.SimCardService.IMSI_LIST;

@Service
@AllArgsConstructor
public class CacheService {
  private final IdentifierClassRepository identifierClassRepository;
  private final IdentifierGroupRepository identifierGroupRepository;
  private final MnpRegionRepository mnpRegionRepository;
  private final ConversionService conversionService;
  private final SimCardRepository simCardRepository;

  @Cacheable("getIdentifierClasses")
  public List<IdentifierClassEntity> getIdentifierClasses() {
    return identifierClassRepository.findAll();
  }

  @Cacheable("getIdentifierGroups")
  public List<IdentifierGroupEntity> getIdentifierGroups() {
    return identifierGroupRepository.findAll();
  }

  @Cacheable("getMnpRegions")
  public List<MnpRegionEntity> getMnpRegions() {
    return mnpRegionRepository.findAll();
  }

  /**
   * @return Симкарты из списка https://jira.tcsbank.ru/browse/AQA-21660
   */
  @Cacheable("getSimCardFromFixedList")
  public List<CreateSpecifiedSimCardResponseDto> getSimCardFromFixedList() {
    var currentDatTime = LocalDateTime.now();
    return simCardRepository.findByImsiIn(currentDatTime, currentDatTime, IMSI_LIST)
            .stream()
            .map(simCardEntity -> conversionService.convert(simCardEntity, CreateSpecifiedSimCardResponseDto.class))
            .collect(Collectors.toList());
  }
}
