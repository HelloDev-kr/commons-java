package kr.hellodev.support.commons.utils;

import kr.hellodev.support.commons.binding.BindingError;
import kr.hellodev.support.commons.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:08 AM
 */
@Component
public class WebUtil {

    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

    private final MessageSource messageSource;
    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor accessor;

    @Autowired
    public WebUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public List<BindingError> getBindingErrors(BindingResult bindingResult) {
        String code;
        String message;

        List<BindingError> errors = new ArrayList<>();
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            message = messageSource.getMessage(objectError, null);

            if (StringUtils.hasText(message) && message.startsWith("E_")) {
                code = message;
                message = messageSource.getMessage(message, null, null);
                log.debug("CODE_ERR:{},{}", code, message);
            } else {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    code = fieldError.getField() + "." + fieldError.getCode();
                    log.debug("FIELD_ERR:{},{}", code, message);
                } else {
                    code = objectError.getObjectName() + "." + objectError.getCode();
                    log.debug("GLOBAL_ERR:{},{}", code, message);
                }
            }

            errors.add(new BindingError(code, message));
        }
        return errors;
    }

    public JsonResult getJsonBindingResult(BindingResult bindingResult) {
        JsonResult jsonResult = new JsonResult(false);
        List<BindingError> errors;

        errors = getBindingErrors(bindingResult);
        jsonResult.setResult(errors);

        return jsonResult;
    }

    public JsonResult getJsonResultSuccess() {
        return getJsonResult(true, null, null);
    }

    public JsonResult getJsonResultFail() {
        return getJsonResult(false, null, null);
    }

    public JsonResult getJsonResultSuccess(String message) {
        return getJsonResult(true, message, null);
    }

    public JsonResult getJsonResultFail(String message) {
        return getJsonResult(false, message, null);
    }

    public JsonResult getJsonResult(boolean success, String message, Object result) {
        JsonResult jsonResult = new JsonResult(success);
        jsonResult.setMessage(message);
        jsonResult.setResult(result);
        return jsonResult;
    }
}
