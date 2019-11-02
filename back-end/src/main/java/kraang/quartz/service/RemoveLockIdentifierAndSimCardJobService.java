package kraang.quartz.service;

import db.kraang.repository.IdentifierLockRepository;
import db.kraang.repository.SimCardLockRepository;
import db.repository.ServiceIdentifierRepository;
import db.repository.SimCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static db.enums.ServiceIdentifierStatusEnm.A;
import static java.time.LocalDateTime.now;
import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class RemoveLockIdentifierAndSimCardJobService {
  private final SimCardRepository simCardRepository;
  private final SimCardLockRepository simCardLockRepository;
  private final ServiceIdentifierRepository serviceIdentifierRepository;
  private final IdentifierLockRepository identifierLockRepository;

  public void executeJob() {
    var currentDateTime = now();
    removeLockSimCard(currentDateTime);
    removeLockIdentifier(currentDateTime);
  }

  private void removeLockSimCard(LocalDateTime currentDateTime) {
    for (var simCardLockEntity : simCardLockRepository.findAll()) {
      if (currentDateTime.isAfter(simCardLockEntity.getRemoveLock())) {
        simCardLockRepository.delete(simCardLockEntity);
      } else {
        var simCardEntity = simCardRepository
                .findByIdSimCardInst(currentDateTime, currentDateTime, simCardLockEntity.getIdSimCardInst());
        if (nonNull(simCardEntity) && nonNull(simCardEntity.getIdentifier())) {
          simCardLockRepository.delete(simCardLockEntity);
        }
      }
    }
  }

  private void removeLockIdentifier(LocalDateTime currentDateTime) {
    for (var identifierLockEntity : identifierLockRepository.findAll()) {
      if (currentDateTime.isAfter(identifierLockEntity.getRemoveLock())) {
        identifierLockRepository.delete(identifierLockEntity);
      } else {
        var serviceIdentifierEntities
                = serviceIdentifierRepository
                .findByMsisdn(currentDateTime, currentDateTime, identifierLockEntity.getMsisdn());
        if (serviceIdentifierEntities.size() == 1 && serviceIdentifierEntities.get(0).getStatus() != A) {
          identifierLockRepository.delete(identifierLockEntity);
        }
      }
    }
  }
}
