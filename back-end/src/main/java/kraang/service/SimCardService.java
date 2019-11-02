package kraang.service;

import db.entity.SimCardEntity;
import db.kraang.entity.SimCardLockEntity;
import db.kraang.repository.SimCardLockRepository;
import db.repository.SimCardRepository;
import kraang.controller.dto.AvailableSimCardDto;
import kraang.controller.dto.CreateSimCardListDto;
import kraang.controller.dto.CreateSpecifiedSimCardDto;
import kraang.controller.dto.CreateSpecifiedSimCardResponseDto;
import kraang.exception.KraangException;
import kraang.util.Utils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;

@Service
@AllArgsConstructor
public class SimCardService {
  private static final Logger logger = LoggerFactory.getLogger(SimCardService.class);
  private final SimCardRepository simCardRepository;
  private final SimCardLockRepository simCardLockRepository;
  private final CacheService cacheService;

  public static final List<String> IMSI_LIST = asList("350771000983508", "350771000983509", "350771000983510",
          "350771000983511", "350771000983512", "350771000983513", "350771000983514", "350771000983515",
          "350771000983516", "350771000983517", "350771000983518", "350771000983519", "350771000983520",
          "350771000983521", "350771000983522", "350771000983523", "350771000983524", "350771000983525",
          "350771000983526", "350771000983527");

  public SimCardEntity createSpecifiedSimCard(CreateSpecifiedSimCardDto newSimCard) {
    var entity = new SimCardEntity();
    entity.setIdSimCardInst(simCardRepository.sequenceFwSimCardsNext());
    entity.setStart(now());
    entity.setStop(Utils.FORWARD_INFINITY);
    entity.setIccid(String.valueOf(newSimCard.getIccid()));
    entity.setImsi(String.valueOf(newSimCard.getImsi()));
    entity.setIdPointOfSaleInst(2);
    entity.setIdManager(2);
    entity.setIdSimCardType(2000);
    entity.setDtInsert(now());
    entity.setDeleted(false);

    return simCardRepository.save(entity);
  }

  public List<SimCardEntity> createSimCardList(CreateSimCardListDto createSimCardListDto) {
    var createSpecifiedSimCardDto = Stream.generate(() -> CreateSpecifiedSimCardDto.builder()
            .iccid(simCardRepository.sequenceTestingIccIdNext())
            .imsi(Long.parseLong("35077" + simCardRepository.sequenceTestingImsiNext()))
            .build())
            .limit(createSimCardListDto.getCount())
            .collect(Collectors.toList());

    return createSpecifiedSimCardDto.stream()
            .parallel()
            .map(dto -> createSpecifiedSimCard(dto))
            .collect(Collectors.toList());
  }

  /**
   * Получить и "залочить" отдаваемы SIM-карты.
   */
  public List<SimCardEntity> getAndLockAvailableSimCard(AvailableSimCardDto availableSimCardDto) {
    List<SimCardEntity> result = new ArrayList();

    while (result.size() != availableSimCardDto.getSimCardCount()) {
      List<SimCardEntity> availableSimCards = new ArrayList<>();

      //считаем сколько еще необходимо sim-карт
      var requiredNumber = availableSimCardDto.getSimCardCount() - result.size();
      availableSimCards.addAll(getAvailableSimCards(requiredNumber, availableSimCardDto.getProvisioningEnabled()));

      for (var availableSimCard : availableSimCards) {
        if (lockSimCard(availableSimCard.getIdSimCardInst(), availableSimCard.getImsi(),
                now().plusSeconds(availableSimCardDto.getTimeToLockSeconds()))) {
          result.add(availableSimCard);
        }
      }
    }
    return result;
  }

  /**
   * @param simCardCount необходимое кол-во свободных сим-карт
   * @param provisioningEnabled     выдавать сим-карты из фиксированного списка
   */
  private List<SimCardEntity> getAvailableSimCards(int simCardCount, Boolean provisioningEnabled) {
    var idSimCardInstFromFixedList = cacheService.getSimCardFromFixedList()
            .stream()
            .map(CreateSpecifiedSimCardResponseDto::getId)
            .collect(Collectors.toList());

    List<SimCardEntity> result;
    //sim-карты которые возвращать нельзя
    var notIdSimCardInst = simCardLockRepository.findAll()
            .stream()
            .map(SimCardLockEntity::getIdSimCardInst)
            .collect(Collectors.toList());
    notIdSimCardInst.add(1L);

    if (BooleanUtils.isTrue(provisioningEnabled)) {
      result = simCardRepository
              .findByAvailableSimCard(now(), now(), simCardCount, notIdSimCardInst, idSimCardInstFromFixedList)
              .stream()
              .collect(Collectors.toList());
    } else {
      notIdSimCardInst.addAll(idSimCardInstFromFixedList);
      result = simCardRepository
              .findByAvailableSimCard(now(), now(), simCardCount, notIdSimCardInst)
              .stream()
              .collect(Collectors.toList());
    }
    if (result.size() < simCardCount) {
      throw new KraangException("В базе данных недостаточно свободных SIM-карт");
    }
    return result;
  }

  private boolean lockSimCard(Long idSimCardInst, String imsi, LocalDateTime removeLock) {
    try {
      simCardLockRepository.lockSimCard(idSimCardInst, imsi, removeLock);
    } catch (ConstraintViolationException | DataIntegrityViolationException e) {
      logger.error("Error simCard {},", imsi, e);
      return false;
    }
    logger.debug("lockSimCard {}", imsi);
    return true;
  }
}