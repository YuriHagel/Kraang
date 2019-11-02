package kraang.annotation;

import kraang.annotation.impl.RangePrefixValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RangePrefixValidImpl.class})
public @interface RangePrefixValid {
  String message() default "Некорректный rangePrefix";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
