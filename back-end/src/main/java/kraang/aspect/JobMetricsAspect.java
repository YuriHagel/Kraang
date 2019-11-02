package kraang.aspect;

import io.micrometer.core.annotation.Incubating;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.lang.NonNullApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static io.micrometer.core.aop.TimedAspect.EXCEPTION_TAG;

/**
 * @author y.hagel
 */
@Aspect
@NonNullApi
@Incubating(since = "1.0.0")
public class JobMetricsAspect {
  private static final Logger logger = LoggerFactory.getLogger(JobMetricsAspect.class);

  private final MeterRegistry registry;
  private final Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint;

  public JobMetricsAspect(MeterRegistry registry) {
    this(registry, pjp ->
            Tags.of("class", pjp.getStaticPart().getSignature().getDeclaringTypeName(),
                    "method", pjp.getStaticPart().getSignature().getName())
    );
  }

  public JobMetricsAspect(MeterRegistry registry, Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint) {
    this.registry = registry;
    this.tagsBasedOnJoinPoint = tagsBasedOnJoinPoint;
  }

  @Around("execution (@org.springframework.scheduling.annotation.Scheduled * *.*(..))")
  public Object timedMethod(ProceedingJoinPoint pjp) throws Throwable {
    final String metricName = "job";
    Timer.Sample sample = Timer.start(registry);
    String exceptionClass = "none";
    try {
      return pjp.proceed();
    } catch (Exception ex) {
      exceptionClass = ex.getClass().getSimpleName();
      throw ex;
    } finally {
      try {
        sample.stop(Timer.builder(metricName)
                .tags(EXCEPTION_TAG, exceptionClass)
                .tag("jobName", pjp.getSignature().getDeclaringTypeName())
                .register(registry));
      } catch (Exception e) {
        logger.error("Error! ", e);
      }
    }
  }
}