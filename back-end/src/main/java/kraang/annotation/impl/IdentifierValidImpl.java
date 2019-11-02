package kraang.annotation.impl;

import kraang.annotation.IdentifierValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentifierValidImpl implements ConstraintValidator<IdentifierValid, String> {
  private static final String ERROR_MESSAGE = "Возможна загрузка следующей номерной емкости: 7026*******, 7027*******";

  @Override
  public void initialize(IdentifierValid constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    Long longValue = null;
    try {
      longValue = Long.parseLong(value);
    } catch (NumberFormatException e) {
      return notValid(value, context);
    }

    if (longValue >= 70260000000L && longValue <= 70279999999L) {
      return true;
    }
    return notValid(value, context);
  }

  private boolean notValid(String value, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("Некорректный identifier " + value + ". " + ERROR_MESSAGE)
            .addConstraintViolation();
    return false;
  }
}
