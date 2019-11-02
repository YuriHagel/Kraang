package kraang.annotation;

import kraang.annotation.impl.QartzTriggerExistsImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {QartzTriggerExistsImpl.class})
public @interface QartzTriggerExists {
  String message() default "Задача не найдена";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
