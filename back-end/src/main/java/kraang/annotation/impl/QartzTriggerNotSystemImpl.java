package kraang.annotation.impl;

import kraang.annotation.QartzTriggerNotSystem;
import kraang.exception.KraangException;
import kraang.service.QuartzService;
import kraang.service.dto.TriggerDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static kraang.ApplicationContextProvider.getApplicationContext;
import static kraang.configuration.quartz.BaseJob.SYSTEM_JOB;

public class QartzTriggerNotSystemImpl implements ConstraintValidator<QartzTriggerNotSystem, String> {
  @Override
  public void initialize(QartzTriggerNotSystem constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }
    TriggerDto trigger = null;
    try {
      trigger = getApplicationContext()
              .getBean(QuartzService.class)
              .getTriggerByKey(value);
    } catch (KraangException e) {
      return true;
    }

    return !trigger.getTrigger().getJobDataMap().getBooleanFromString(SYSTEM_JOB);
  }
}
