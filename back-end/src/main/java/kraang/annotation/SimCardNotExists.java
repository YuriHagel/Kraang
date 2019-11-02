package kraang.annotation;

import kraang.annotation.impl.SimCardNotExistsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SimCardNotExistsImpl.class})
public @interface SimCardNotExists {
  String message() default "Указанная SIM-карта существует";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
