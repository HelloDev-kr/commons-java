package kr.hellodev.support.commons.validator.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 2:08 PM
 */
@Documented
@Constraint(validatedBy = FileNotEmptyConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface ValidFileNotEmpty {

    String message() default "message.validation.constraint.file.not.empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
