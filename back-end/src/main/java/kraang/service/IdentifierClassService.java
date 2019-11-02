package kraang.service;

import db.entity.IdentifierClassEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Service
@AllArgsConstructor
public class IdentifierClassService {
  private final CacheService cacheService;

  public IdentifierClassEntity findByDeletedFalseAndId(long id) {
    return cacheService.getIdentifierClasses()
            .stream()
            .filter(identifierClassEntity -> isFalse(identifierClassEntity.getDeleted()))
            .filter(identifierClassEntity -> identifierClassEntity.getId() == id)
            .findFirst()
            .orElse(null);
  }

  public List<IdentifierClassEntity> findByDeletedFalseAndIdNotIn(List<Long> idList) {
    return cacheService.getIdentifierClasses()
            .stream()
            .filter(identifierClassEntity -> isFalse(identifierClassEntity.getDeleted()))
            .filter(identifierClassEntity -> !idList.contains(identifierClassEntity.getId()))
            .collect(Collectors.toList());
  }
}
