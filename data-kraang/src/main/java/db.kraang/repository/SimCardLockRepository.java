package db.kraang.repository;

import db.kraang.entity.SimCardLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
public interface SimCardLockRepository extends JpaRepository<SimCardLockEntity, String> {
  @Modifying
  @Query(value = "insert into simcards_lock values(:idSimCardInst, :msisdn, :removeLock)", nativeQuery = true)
  void lockSimCard(@Param("idSimCardInst") Long idSimCardInst,
                   @Param("msisdn") String msisdn,
                   @Param("removeLock") LocalDateTime removeLock);
}
