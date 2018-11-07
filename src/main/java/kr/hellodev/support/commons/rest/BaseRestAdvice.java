package kr.hellodev.support.commons.rest;

import kr.hellodev.support.commons.binding.BasePacket;
import kr.hellodev.support.commons.binding.BaseResult;
import kr.hellodev.support.commons.binding.IResult;
import kr.hellodev.support.commons.exception.BizException;
import kr.hellodev.support.commons.exception.DefaultBindingException;
import kr.hellodev.support.commons.utils.ExceptionUtil;
import kr.hellodev.support.commons.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:26 AM
 */
@RestControllerAdvice
public class BaseRestAdvice {

    private final static Logger REQ_ERR = LoggerFactory.getLogger("REQ_ERR");

    @Autowired
    private WebUtil webUtil;
    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor accessor;

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<String> handleBindException(BindException ex, HttpServletResponse response) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_BINDING_ERROR, ex, ex.getBindingResult());
    }

    @ExceptionHandler(DefaultBindingException.class)
    @ResponseBody
    public ResponseEntity<String> handleDefaultBindingException(DefaultBindingException ex, HttpServletResponse response) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_BINDING_ERROR, ex, ex.getBindingResult());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(HttpRequestMethodNotSupportedException ex) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));

        IResult result = BaseResult.FATAL_REQUEST_METHOD_NOT_SUPPORTED;
        String msg = accessor.getMessage(result.msg(), new Object[]{
                ex.getMethod()
        }, LocaleContextHolder.getLocale());
        return setResponse(result, ex, null, msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(HttpMessageNotReadableException ex) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_COULD_NOT_READ_JSON, ex);
    }

    @ExceptionHandler({
            SQLException.class, DataAccessException.class
    })
    @ResponseBody
    public ResponseEntity<String> handleSQLException(Exception ex) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_SQL_EXCEPTION, ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_SYS_EXCEPTION, ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_USER_ILLEGAL_ARGUMENT, ex);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(BaseResult.FATAL_BINDING_ERROR, ex);
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<String> handleMethodBizException(BizException ex, HttpServletResponse response) {
        REQ_ERR.info(ExceptionUtil.traceException(ex));
        return setResponse(ex.getResult(), ex);
    }

    private ResponseEntity<String> setResponse(IResult result, Exception ex) {
        return setResponse(result, ex, null);
    }

    private ResponseEntity<String> setResponse(IResult result, Exception ex, BindingResult bindingResult) {
        String msg = accessor.getMessage(result.msg(), LocaleContextHolder.getLocale());
        return setResponse(result, ex, bindingResult, msg);
    }

    private ResponseEntity<String> setResponse(IResult result, Exception ex, BindingResult bindingResult, String msg) {
        BasePacket packet = new BasePacket();
        packet.setResult(result);
        packet.setMsg(msg);
        if (bindingResult != null)
            packet.setErrors(webUtil.getBindingErrors(bindingResult));
        packet.setResponseTime(System.currentTimeMillis());
        if (ex.getLocalizedMessage() != null)
            packet.setException(ex.getLocalizedMessage());

        return setResponse(packet.toJsonBody());
    }

    private ResponseEntity<String> setResponse(String body) {
        return setResponse(body, HttpStatus.OK);
    }

    private ResponseEntity<String> setResponse(String body, HttpStatus statusCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        return new ResponseEntity<>(body, headers, statusCode);
    }
}
