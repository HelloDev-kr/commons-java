package kr.hellodev.support.commons.validator.enumeration;

import org.apache.commons.lang3.ArrayUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 12:00 PM
 */
public class EnumConstraintValidator implements ConstraintValidator<ValidEnum, String> {

    private String[] values;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return ArrayUtils.indexOf(values, value) > -1;
    }
}
