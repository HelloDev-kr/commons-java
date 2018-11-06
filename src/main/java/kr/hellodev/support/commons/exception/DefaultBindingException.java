package kr.hellodev.support.commons.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 2:49 PM
 */
public class DefaultBindingException extends Exception {

    private static final long serialVersionUID = -2973350373115917974L;

    public static final int RET_TYPE_PAGE = 0;
    public static final int RET_TYPE_JSON = 1;

    @Getter
    private BindingResult bindingResult;
    @Getter
    private int retType;
    @Getter
    private String page;

    public DefaultBindingException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public DefaultBindingException(BindingResult bindingResult, int retType) {
        this.bindingResult = bindingResult;
        this.retType = retType;
    }

    public DefaultBindingException(BindingResult bindingResult, int retType, String page) {
        this.bindingResult = bindingResult;
        this.retType = retType;
        this.page = page;
    }
}
