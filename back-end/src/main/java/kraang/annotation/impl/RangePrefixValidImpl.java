package kraang.annotation.impl;

import kraang.annotation.RangePrefixValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;

public class RangePrefixValidImpl implements ConstraintValidator<RangePrefixValid, Integer> {
  private static final List<Integer> VALID_RANGE_PREFIX_LIST = Arrays.asList(7026, 7027, 7028, 7095);

  @Override
  public void initialize(RangePrefixValid constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }

    if (!VALID_RANGE_PREFIX_LIST.contains(value)) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(
              context.getDefaultConstraintMessageTemplate() + ". Поддерживаемые значения: "
                      + VALID_RANGE_PREFIX_LIST)
              .addConstraintViolation();
      return false;
    }
    return true;
  }
}
