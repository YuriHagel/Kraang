package kraang.annotation.impl;

import kraang.annotation.CronValidator;
import org.quartz.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author y.hagel
 */
public class CronValidatorImpl implements ConstraintValidator<CronValidator, String> {
  @Override
  public void initialize(final CronValidator cronValidator) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isValid(final String cron,
                         final ConstraintValidatorContext context) {
    return CronExpression.isValidExpression(cron);
  }
}