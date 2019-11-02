package kraang.annotation;

import kraang.annotation.impl.MnpRegionExistsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MnpRegionExistsImpl.class})
public @interface MnpRegionExists {
  String message() default "Регион не существует";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
