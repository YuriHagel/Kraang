package kraang.annotation.impl;

import kraang.annotation.QartzTriggerExists;
import kraang.exception.KraangException;
import kraang.service.QuartzService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static kraang.ApplicationContextProvider.getApplicationContext;

public class QartzTriggerExistsImpl implements ConstraintValidator<QartzTriggerExists, String> {
  @Override
  public void initialize(QartzTriggerExists constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }
    try {
      getApplicationContext()
              .getBean(QuartzService.class)
              .getTriggerByKey(value);
    } catch (KraangException e) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(String.format(kraang.service.QuartzService.MSG_TASK_NOT_FOUND, value))
              .addConstraintViolation();
      return false;
    }
    return true;
  }
}
