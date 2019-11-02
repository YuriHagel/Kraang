package kraang.configuration.quartz;

import org.apache.commons.lang3.ArrayUtils;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class QuartzConfiguration {
  private final DataSource appDataSource;
  private final List kraangQuartzTriggers;
  private final PlatformTransactionManager platformTransactionManager;

  @Autowired
  public QuartzConfiguration(@Qualifier("kraangDataSource") DataSource appDataSource,
                             @Qualifier("quartzTriggers") List kraangQuartzTriggers,
                             @Qualifier("kraangPlatformTransactionManager") PlatformTransactionManager platformTransactionManager) {
    this.appDataSource = appDataSource;
    this.kraangQuartzTriggers = kraangQuartzTriggers;
    this.platformTransactionManager = platformTransactionManager;
  }

  @Bean
  public AutowiringSpringBeanJobFactory quartzJobFactory() {
    var autowiringSpringBeanJobFactory = new AutowiringSpringBeanJobFactory();
    autowiringSpringBeanJobFactory.setIgnoredUnknownProperties("applicationContext");
    return autowiringSpringBeanJobFactory;
  }

  @Bean(name = "quartzScheduler")
  public SchedulerFactoryBean kraangQuartz() {
    var factoryBean = new SchedulerFactoryBean();
    var resource = new ClassPathResource("quartz.properties");
    factoryBean.setConfigLocation(resource);
    factoryBean.setDataSource(appDataSource);
    factoryBean.setJobFactory(quartzJobFactory());
    factoryBean.setOverwriteExistingJobs(false);
    factoryBean.setAutoStartup(true);
    factoryBean.setSchedulerName("kraangQuartzScheduler");
    factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
    factoryBean.setWaitForJobsToCompleteOnShutdown(false);
    factoryBean.setTransactionManager(platformTransactionManager);

    Trigger[] quartzTriggerArray = ((List<Trigger>) kraangQuartzTriggers)
            .toArray(new Trigger[kraangQuartzTriggers.size()]);
    Trigger[] triggers = ArrayUtils.addAll(quartzTriggerArray);
    factoryBean.setTriggers(triggers);
    return factoryBean;
  }
}
