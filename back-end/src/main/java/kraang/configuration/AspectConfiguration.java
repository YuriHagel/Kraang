package kraang.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import kraang.aspect.JobMetricsAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {
  @Bean
  public JobMetricsAspect aspect(MeterRegistry registry) {
    return new JobMetricsAspect(registry);
  }
}
