package db.repository;

import db.entity.IdentifierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface IdentifierRepository extends JpaRepository<IdentifierEntity, Long> {
  @Query("select s.identifier " +
          " from ServiceIdentifierEntity s" +
          " where s.deleted = false" +
          "   and s.identifier.deleted = false" +
          "   and s.start <= :dtStart" +
          "   and s.stop   > :dtStop" +
          "   and s.identifier.msisdn = :msisdn")
  IdentifierEntity findByMsisdn(@Param("msisdn") String msisdn,
                                @Param("dtStart") LocalDateTime start,
                                @Param("dtStop") LocalDateTime stop);

  @Query(value = "select s_identifiers.nextval from dual", nativeQuery = true)
  long fwIdentifiersNext();
}
