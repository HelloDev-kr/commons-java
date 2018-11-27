package kr.hellodev.support.commons.validator.url;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UrlConstraintValidator implements ConstraintValidator<ValidUrl, String> {

    private final static Pattern URL_PATTERN = Pattern.compile("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");

    public void initialize(ValidUrl constraintAnnotation) {
        // nothing to initialize
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || "".equals(value))
            return true;

        return URL_PATTERN.matcher(value).matches();
    }
}
