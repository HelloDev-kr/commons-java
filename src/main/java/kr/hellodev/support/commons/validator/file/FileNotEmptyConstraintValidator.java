package kr.hellodev.support.commons.validator.file;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 2:09 PM
 */
public class FileNotEmptyConstraintValidator implements ConstraintValidator<ValidFileNotEmpty, CommonsMultipartFile> {

    public void initialize(ValidFileNotEmpty constraint) {
        // nothing
    }

    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.isEmpty() && file.getSize() != 0;
    }
}
