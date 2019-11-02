package kraang.annotation;

import kraang.annotation.impl.IdentifierClassExistsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdentifierClassExistsImpl.class})
public @interface IdentifierClassExists {
  String message() default "Некорректный identifier class";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
