package kr.hellodev.support.commons.validator.number;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumberConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumber {

    String message() default "message.validation.constraint.number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    double min() default Long.MIN_VALUE;

    double max() default Long.MAX_VALUE;
}
