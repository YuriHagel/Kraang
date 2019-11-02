package db.kraang.entity.quartz;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "qrtz_job_logs")
public class JobLogEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "qrtz_job_logs_seq")
  @SequenceGenerator(name = "qrtz_job_logs_seq", sequenceName = "qrtz_job_logs_seq", allocationSize = 1)
  public Long id;
  @Column(name = "trigger_id")
  private String triggerName;
  @Column(name = "job_id")
  private String jobId;
  @Column(name = "start_date")
  private LocalDateTime startDate;
  @Column(name = "end_date")
  private LocalDateTime endDate;
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private JobState jobState;
  @Column(name = "exception")
  private String exception;

  public enum JobState {IN_PROGRESS, FINISHED, ERROR}
}
