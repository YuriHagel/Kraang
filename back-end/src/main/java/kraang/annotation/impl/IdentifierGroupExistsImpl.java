package kraang.annotation.impl;

import kraang.annotation.IdentifierGroupExists;
import kraang.service.IdentifierGroupService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static kraang.ApplicationContextProvider.getApplicationContext;

public class IdentifierGroupExistsImpl implements ConstraintValidator<IdentifierGroupExists, Integer> {
  @Override
  public void initialize(IdentifierGroupExists constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }
    return (getApplicationContext()
            .getBean(IdentifierGroupService.class)
            .findByDeletedFalseAndId((long) value) == null) ? false : true;
  }
}
