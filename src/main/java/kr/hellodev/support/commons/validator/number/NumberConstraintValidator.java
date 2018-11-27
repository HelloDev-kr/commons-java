package kr.hellodev.support.commons.validator.number;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberConstraintValidator implements ConstraintValidator<ValidNumber, Object> {

    private double min;
    private double max;

    @Override
    public void initialize(ValidNumber constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        double val = 0;

        try {
            if (value instanceof String) {
                val = Double.parseDouble((String) value);
            } else if (value instanceof Integer) {
                val = ((Integer) value).doubleValue();
            } else if (value instanceof Long) {
                val = ((Long) value).doubleValue();
            } else if (value instanceof Float) {
                val = ((Float) value).doubleValue();
            } else if (value instanceof Double) {
                val = (Double) value;
            } else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }

        return min <= val && val <= max;
    }
}
