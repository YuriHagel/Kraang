package db.kraang.repository;

import db.kraang.entity.SequenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SequenceRepository extends JpaRepository<SequenceEntity, Long> {
  @Query(value = "select msisdn_seq.nextval from dual", nativeQuery = true)
  long sequenceMsisdnNext();
}
