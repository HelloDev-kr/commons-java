package kr.hellodev.support.commons.result;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:10 AM
 */
public class JsonResult implements Serializable {

    private static final long serialVersionUID = 890964627958163403L;

    @Getter
    @Setter
    private boolean success;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private Object result;
    @Accessors(chain = true)
    @Getter
    @Setter
    private String token;

    public JsonResult() {
        this(false);
    }

    public JsonResult(boolean success) {
        this(success, null);
    }

    public JsonResult(boolean success, String message) {
        this(success, message, null);
    }

    public JsonResult(boolean success, String message, Object result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }
}
