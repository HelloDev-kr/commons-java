package kr.hellodev.support.commons.validator.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 27/11/2018 12:29 PM
 */
public class FileExtConstraintValidator implements ConstraintValidator<ValidFileExt, CommonsMultipartFile> {

    private String[] ext;

    public void initialize(ValidFileExt constraint) {
        ext = constraint.ext();
    }

    public boolean isValid(CommonsMultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            return true;
        }

        if (ext == null || ext.length == 0 || "*".equals(ext[0])) {
            return true;
        }

        for (String str : ext) {
            if (str.equalsIgnoreCase(FilenameUtils.getExtension(file.getOriginalFilename()))) {
                return true;
            }
        }

        return false;
    }
}
