package kr.hellodev.support.commons.validator.matches;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 2:14 PM
 */
public class MatchesConstraintValidator implements ConstraintValidator<ValidMatches, Object> {

    private String[] fields;
    private String[] verifyFields;

    public void initialize(ValidMatches constraintAnnotation) {
        fields = constraintAnnotation.fields();
        verifyFields = constraintAnnotation.verifyFields();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean matches = true;

        for (int i = 0; i < fields.length; i++) {
            Object fieldObj, verifyFieldObj;
            try {
                fieldObj = BeanUtils.getProperty(value, fields[i]);
                verifyFieldObj = BeanUtils.getProperty(value, verifyFields[i]);
            } catch (Exception e) {
                continue;
            }

            boolean neitherSet = (fieldObj == null) && (verifyFieldObj == null);
            if (neitherSet) {
                continue;
            }

            boolean tempMatches = (fieldObj != null) && fieldObj.equals(verifyFieldObj);

            if (!tempMatches) {
                addConstraintViolation(context, fields[i] + ".field.donot.match", verifyFields[i]);
            }

            matches = matches ? tempMatches : matches;
        }
        return matches;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message, String field) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addNode(field).addConstraintViolation();
    }
}
