package kraang.service;

import db.entity.MnpRegionEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MnpRegionService {
  private final CacheService cacheService;

  public List<MnpRegionEntity> findAll() {
    return cacheService.getMnpRegions();
  }

  public MnpRegionEntity findByRegionCode(String regionCode) {
    return cacheService.getMnpRegions()
            .stream()
            .filter(mnpRegionEntity -> Objects.equals(mnpRegionEntity.getRegionCode(), regionCode))
            .findFirst()
            .orElse(null);
  }
}
