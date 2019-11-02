package kraang.service;

import db.kraang.repository.SequenceRepository;
import kraang.controller.dto.CreateIdentifierListDto;
import kraang.controller.dto.CreateSpecifiedIdentifierDto;
import kraang.service.dto.IdentifierDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.valueOf;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@AllArgsConstructor
public class IdentifierBatchService {
  private static final Logger logger = LoggerFactory.getLogger(IdentifierBatchService.class);
  private static final int MAX_BATCH = 100;

  private final IdentifierService identifierService;
  private final SequenceRepository sequenceRepository;

  public List<IdentifierDto> createIdentifiersBatch(CreateIdentifierListDto newIdentifier) {
    List<IdentifierDto> result = new ArrayList();

    while (result.size() != newIdentifier.getCount()) {
      var identifierCount = newIdentifier.getCount() - result.size();
      if (identifierCount > MAX_BATCH) {
        identifierCount = MAX_BATCH;
      }
      var createdIdentifiers =
              createBatch(generateCreateSpecifiedIdentifierDtoList(newIdentifier, identifierCount));
      if (!isEmpty(createdIdentifiers)) {
        result.addAll(createdIdentifiers);
      }
    }
    return result;
  }

  private List<IdentifierDto> createBatch(List<CreateSpecifiedIdentifierDto> createSpecifiedIdentifierDtoList) {
    try {
      return identifierService.createIdentifierList(createSpecifiedIdentifierDtoList);
    } catch (DataIntegrityViolationException ce) {
      logger.error("Error create batch identifiers ", ce);
      return null;
    }
  }

  private List<CreateSpecifiedIdentifierDto>
  generateCreateSpecifiedIdentifierDtoList(CreateIdentifierListDto createIdentifierListDto, int identifierCount) {
    return Stream.generate(() -> CreateSpecifiedIdentifierDto.builder()
            .identifier(valueOf(sequenceRepository.sequenceMsisdnNext()))
            .identifierClass(createIdentifierListDto.getIdentifierClass())
            .identifierGroup(createIdentifierListDto.getIdentifierGroup())
            .mnpRegionCode(createIdentifierListDto.getIdentifierRegionId())
            .build())
            .limit(identifierCount)
            .collect(Collectors.toList());
  }
}
