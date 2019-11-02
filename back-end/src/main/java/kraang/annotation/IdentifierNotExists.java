package kraang.annotation;

import kraang.annotation.impl.IdentifierNotExistsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdentifierNotExistsImpl.class})
public @interface IdentifierNotExists {
  String message() default "Указанный identifier уже существует";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
