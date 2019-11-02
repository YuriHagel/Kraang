package kraang.annotation.impl;

import kraang.annotation.MnpRegionExists;
import kraang.service.MnpRegionService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Objects.isNull;
import static kraang.ApplicationContextProvider.getApplicationContext;

public class MnpRegionExistsImpl implements ConstraintValidator<MnpRegionExists, String> {
  @Override
  public void initialize(MnpRegionExists constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (isNull(value)) {
      return true;
    }
    return (getApplicationContext()
            .getBean(MnpRegionService.class)
            .findByRegionCode(value) == null) ? false : true;
  }
}
