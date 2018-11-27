package kr.hellodev.support.commons.validator.url;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UrlConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUrl {

    String message() default "message.validation.constraint.url";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
