package kraang.annotation.impl;

import kraang.annotation.IdentifierClassExists;
import kraang.service.IdentifierClassService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static kraang.ApplicationContextProvider.getApplicationContext;


public class IdentifierClassExistsImpl implements ConstraintValidator<IdentifierClassExists, Integer> {
  @Override
  public void initialize(IdentifierClassExists constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }
    return (getApplicationContext()
            .getBean(IdentifierClassService.class)
            .findByDeletedFalseAndId((long) value) == null) ? false : true;
  }
}
