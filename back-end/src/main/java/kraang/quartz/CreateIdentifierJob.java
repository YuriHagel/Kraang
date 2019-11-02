package kraang.quartz;

import kraang.configuration.quartz.BaseJob;
import kraang.quartz.service.CreateIdentifierJobService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class CreateIdentifierJob extends BaseJob {
  @Autowired
  private CreateIdentifierJobService jobService;

  @Override
  protected void executeJob(JobExecutionContext context) {
    jobService.executeJob();
  }
}
