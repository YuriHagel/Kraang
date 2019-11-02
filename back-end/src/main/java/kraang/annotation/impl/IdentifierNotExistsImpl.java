package kraang.annotation.impl;

import db.repository.IdentifierRepository;
import kraang.annotation.IdentifierNotExists;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static kraang.ApplicationContextProvider.getApplicationContext;

public class IdentifierNotExistsImpl implements ConstraintValidator<IdentifierNotExists, String> {
  @Override
  public void initialize(IdentifierNotExists constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    var currentDateTime = LocalDateTime.now();
    if (isNull(value) || isNull(getApplicationContext()
            .getBean(IdentifierRepository.class)
            .findByMsisdn(value, currentDateTime, currentDateTime))) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("Identifier " + value + " уже заведен")
            .addConstraintViolation();
    return false;
  }
}
