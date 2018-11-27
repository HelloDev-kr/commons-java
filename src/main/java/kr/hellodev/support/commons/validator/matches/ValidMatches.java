package kr.hellodev.support.commons.validator.matches;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 2:13 PM
 */
@Documented
@Constraint(validatedBy = MatchesConstraintValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMatches {

    String message() default "message.validation.constraint.matches";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields();

    String[] verifyFields();
}
