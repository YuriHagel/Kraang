package kraang.annotation;

import kraang.annotation.impl.IdentifierGroupExistsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdentifierGroupExistsImpl.class})
public @interface IdentifierGroupExists {
  String message() default "Некорректный identifier group";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
