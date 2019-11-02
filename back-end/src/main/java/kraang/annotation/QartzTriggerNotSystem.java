package kraang.annotation;

import kraang.annotation.impl.QartzTriggerNotSystemImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {QartzTriggerNotSystemImpl.class})
public @interface QartzTriggerNotSystem {
  String message() default "Системная задача";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
