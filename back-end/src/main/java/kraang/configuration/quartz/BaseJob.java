package kraang.configuration.quartz;

import db.kraang.entity.quartz.JobLogEntity;
import db.kraang.repository.quartz.JobLogRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public abstract class BaseJob implements InterruptableJob {
  private static final Logger logger = LoggerFactory.getLogger(BaseJob.class);

  public static final String CRON_GROUP = "CRON";
  public static final String MANUAL_GROUP = "MANUAL";
  public static final String MANUALLY_TRIGGER = "manually-trigger-";
  public static final String LOG_ENABLED_KEY = "logEnabled";
  public static final String SYSTEM_JOB = "systemJob";
  @Getter
  @Setter
  private boolean logEnabled;
  @Getter
  @Setter
  private boolean systemJob;
  @Getter
  @Setter
  private String jobGroupName;

  @Autowired
  private JobLogRepository jobLogRepository;


  @Override
  public void execute(JobExecutionContext context) {
    long start = System.currentTimeMillis();
    String jobClassName = getClass().getSimpleName();
    logger.trace("starting job: {}", jobClassName);

    var jobLog = new JobLogEntity();
    if (logEnabled) {
      jobLog.setTriggerName(context.getTrigger().getKey().getName());
      jobLog.setJobId(context.getJobDetail().getKey().getName());
      jobLog.setStartDate(LocalDateTime.now());
      jobLog.setJobState(JobLogEntity.JobState.IN_PROGRESS);
      jobLogRepository.save(jobLog);
    }
    try {
      executeJob(context);
      jobLog.setJobState(JobLogEntity.JobState.FINISHED);
    } catch (Exception e) {
      jobLog.setJobState(JobLogEntity.JobState.ERROR);
      logger.error("failed to execute job " + jobClassName, e);
      jobLog.setException(ExceptionUtils.getStackTrace(e));
    } finally {
      if (logEnabled) {
        jobLog.setEndDate(LocalDateTime.now());
        jobLogRepository.save(jobLog);
      }
      logger.trace("job finished in {}ms: {}", (System.currentTimeMillis() - start), jobClassName);
    }
  }

  @Override
  public void interrupt() {
    logger.debug("interrupt job " + getClass().getSimpleName());
  }

  protected abstract void executeJob(JobExecutionContext context);
}
