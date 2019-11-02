package db.kraang.repository.quartz;

import db.kraang.entity.quartz.JobLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
public interface JobLogRepository extends JpaRepository<JobLogEntity, Long> {
  @Modifying
  @Query(value = "delete from JobLogEntity j where j.startDate <= ?1")
  void deleteAnythingOlderThanTheDate(LocalDateTime localDateTime);
}
