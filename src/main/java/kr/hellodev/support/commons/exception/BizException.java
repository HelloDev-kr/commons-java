package kr.hellodev.support.commons.exception;

import kr.hellodev.support.commons.binding.IResult;
import lombok.Getter;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 2:47 PM
 */
public class BizException extends Exception {

    private static final long serialVersionUID = -2668685279062718823L;

    @Getter
    private IResult result;

    public BizException(IResult result) {
        this.result = result;
    }
}
