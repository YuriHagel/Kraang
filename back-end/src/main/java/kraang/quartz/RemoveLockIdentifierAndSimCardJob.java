package kraang.quartz;

import kraang.configuration.quartz.BaseJob;
import kraang.quartz.service.RemoveLockIdentifierAndSimCardJobService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class RemoveLockIdentifierAndSimCardJob extends BaseJob {
  @Autowired
  RemoveLockIdentifierAndSimCardJobService removeLockSimCardJobService;

  @Override
  protected void executeJob(JobExecutionContext context) {
    removeLockSimCardJobService.executeJob();
  }
}
