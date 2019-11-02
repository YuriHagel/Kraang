package kraang.annotation;

import kraang.annotation.impl.IdentifierValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdentifierValidImpl.class})
public @interface IdentifierValid {
  String message() default "Некорректный identifier";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
