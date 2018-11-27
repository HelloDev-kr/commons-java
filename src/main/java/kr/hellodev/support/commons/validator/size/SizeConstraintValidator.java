package kr.hellodev.support.commons.validator.size;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeConstraintValidator implements ConstraintValidator<ValidSize, CharSequence> {

    //	private static final Log log = LoggerFactory.make();

    private int min;
    private int max;
    private int koreanByteLength;

    public void initialize(ValidSize parameters) {
        min = parameters.min();
        max = parameters.max();
        koreanByteLength = parameters.koreanByteLength();
        validateParameters();
    }

    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {
        if (charSequence == null || "".equals(charSequence.toString()))
            return true;

        int length = getCharacterLength(charSequence.toString(), koreanByteLength);

        return length >= min && length <= max;
    }

    private static int getCharacterLength(String str, int koreanByteLength) {
        int len = str.length();
        int cnt = 0;

        for (int i = 0; i < len; i++) {
            if (str.charAt(i) < 256)
                cnt++;
            else
                cnt += koreanByteLength;
        }

        return cnt;
    }

    private void validateParameters() {
		/*if(min < 0)
			throw log.getMinCannotBeNegativeException();
		if(max < 0)
			throw log.getMaxCannotBeNegativeException();
		if(max < min)
			throw log.getLengthCannotBeNegativeException();
		if(koreanByteLength < 0)
			log.error("The koreanByteLength parameter cannot be negative.");*/
    }
}
