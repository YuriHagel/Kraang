package kraang.annotation;


import kraang.annotation.impl.CronValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * @author y.hagel
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CronValidatorImpl.class})
@Documented
@NotNull(message = "Не указано cron выражение")
public @interface CronValidator {
  String message() default "Не валидное cron выражение";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}