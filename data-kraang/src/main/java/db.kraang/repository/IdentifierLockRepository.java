package db.kraang.repository;

import db.kraang.entity.IdentifierLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
public interface IdentifierLockRepository extends JpaRepository<IdentifierLockEntity, String> {
  @Modifying
  @Query(value = "insert into identifiers_lock values(:msisdn, :removeLock)", nativeQuery = true)
  void lockSimCard(@Param("msisdn") String msisdn,
                   @Param("removeLock") LocalDateTime removeLock);
}