package kr.hellodev.support.commons.validator.enumeration;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 12:24 PM
 */
@Documented
@Constraint(validatedBy = EnumConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface ValidEnum {

    String message() default "message.validation.constraint.enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values() default {};
}
