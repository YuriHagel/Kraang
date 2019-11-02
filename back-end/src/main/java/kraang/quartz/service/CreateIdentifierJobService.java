package kraang.quartz.service;

import db.entity.MnpRegionEntity;
import db.repository.ServiceIdentifierRepository;
import kraang.controller.dto.CreateIdentifierListDto;
import kraang.service.IdentifierBatchService;
import kraang.service.IdentifierClassService;
import kraang.service.MnpRegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateIdentifierJobService {
  /**
   * Классы номерной емкости которые не будут создаваться
   */
  private static final List<Long> IGNORE_CLASSES = Arrays.asList(2001L, 2012L);
  /**
   * Группа создаваемой номерной емкости
   */
  private static final long CREATE_GROUP = 2001L;
  /**
   * Минимальный порог(в регионе по конкретному классу и группе) при
   * пересичении которого будет создаваться номерная емкость
   */
  private static final int MIN_UNUSED_IDENTIFIERS = 100;

  private final ServiceIdentifierRepository serviceIdentifierRepository;
  private final IdentifierBatchService identifierBatchService;
  private final IdentifierClassService identifierClassService;
  private final MnpRegionService mnpRegionService;

  public void executeJob() {
    identifierClassService
            .findByDeletedFalseAndIdNotIn(IGNORE_CLASSES)
            .parallelStream()
            .forEach(identifierClassEntity -> {
              mnpRegionService
                      .findAll()
                      .parallelStream()
                      .forEach(mnpRegionEntity ->
                              createIdentifiers(identifierClassEntity.getId(), CREATE_GROUP, mnpRegionEntity));
            });
  }

  private void createIdentifiers(long identifierClass,
                                 long identifierGroup,
                                 MnpRegionEntity mnpRegionEntity) {
    int unusedIdentifiers = serviceIdentifierRepository
            .getCountUnusedIdentifiers(LocalDateTime.now(),
                    identifierClass,
                    identifierGroup,
                    mnpRegionEntity.getId());

    if (unusedIdentifiers < MIN_UNUSED_IDENTIFIERS) {
      int needCreateIdentifiers = MIN_UNUSED_IDENTIFIERS - unusedIdentifiers + 100;
      identifierBatchService.createIdentifiersBatch(new CreateIdentifierListDto(
              needCreateIdentifiers,
              (int) identifierClass,
              (int) identifierGroup,
              mnpRegionEntity.getRegionCode()));
    }
  }
}
