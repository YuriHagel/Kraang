package kraang.service;

import db.entity.IdentifierGroupEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@AllArgsConstructor
public class IdentifierGroupService {
  private final CacheService cacheService;

  public IdentifierGroupEntity findByDeletedFalseAndId(long id) {
    return cacheService.getIdentifierGroups()
            .stream()
            .filter(identifierClassEntity -> isFalse(identifierClassEntity.getDeleted()))
            .filter(identifierClassEntity -> identifierClassEntity.getId() == id)
            .findFirst()
            .orElse(null);
  }
}
