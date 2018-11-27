package kr.hellodev.support.commons.validator.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 12:27 PM
 */
@Documented
@Constraint(validatedBy = FileExtConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface ValidFileExt {

    String message() default "message.validation.constraint.file.extension";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] ext() default {"*"};
}
