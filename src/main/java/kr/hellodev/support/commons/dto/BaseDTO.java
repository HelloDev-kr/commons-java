package kr.hellodev.support.commons.dto;

import kr.hellodev.support.commons.form.BaseForm;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 4:22 PM
 */
public abstract class BaseDTO {

    public void setForm(@NotNull BaseForm form, String... ignoreProperties) {
        if (form == null) {
            return;
        }
        BeanUtils.copyProperties(form, this, ignoreProperties);
    }
}
