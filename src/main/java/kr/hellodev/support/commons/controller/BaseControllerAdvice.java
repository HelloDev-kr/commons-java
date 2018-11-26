package kr.hellodev.support.commons.controller;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 3:43 PM
 */
@ControllerAdvice
public class BaseControllerAdvice {

    private final static Logger REQ_ERR = LoggerFactory.getLogger("REQ_ERR");

    private final WebUtil webUtil;
    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor accessor;

    @Autowired
    public BaseControllerAdvice(WebUtil webUtil) {
        this.webUtil = webUtil;
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<String> handleBindException(BindException e, HttpServletResponse response) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.BAD_REQUEST, BaseResult.FATAL_BINDING_ERROR, e, e.getBindingResult());
    }

    @ExceptionHandler(DefaultBindingException.class)
    @ResponseBody
    public ResponseEntity<String> handleDefaultBindingException(DefaultBindingException e, HttpServletResponse response) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.BAD_REQUEST, BaseResult.FATAL_BINDING_ERROR, e, e.getBindingResult());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(HttpRequestMethodNotSupportedException e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));

        IResult result = BaseResult.FATAL_REQUEST_METHOD_NOT_SUPPORTED;
        String msg = accessor.getMessage(result.msg(), new Object[]{e.getMethod()}, LocaleContextHolder.getLocale());
        return setResponse(HttpStatus.METHOD_NOT_ALLOWED, result, e, null, msg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(HttpMessageNotReadableException e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.BAD_REQUEST, BaseResult.FATAL_COULD_NOT_READ_JSON, e);
    }

    @ExceptionHandler({SQLException.class, DataAccessException.class/*, MySQLSyntaxErrorException.class*/})
    @ResponseBody
    public ResponseEntity<String> handleDAOException(Exception e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.INTERNAL_SERVER_ERROR, BaseResult.FATAL_SQL_EXCEPTION, e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.BAD_REQUEST, BaseResult.FATAL_BINDING_ERROR, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.BAD_REQUEST, BaseResult.FATAL_BINDING_ERROR, e);
    }

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseEntity<String> handleBizException(BizException e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.OK, e.getResult(), e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception e) {
        REQ_ERR.info(ExceptionUtil.traceException(e));
        return setResponse(HttpStatus.INTERNAL_SERVER_ERROR, BaseResult.FATAL_SYS_EXCEPTION, e);
    }

    private ResponseEntity<String> setResponse(HttpStatus status, IResult result, Exception e) {
        return setResponse(status, result, e, null);
    }

    private ResponseEntity<String> setResponse(HttpStatus status, IResult result, Exception e, BindingResult bindingResult) {
        String msg = accessor.getMessage(result.msg(), LocaleContextHolder.getLocale());
        return setResponse(status, result, e, bindingResult, msg);
    }

    private ResponseEntity<String> setResponse(HttpStatus status, IResult result, Exception e, BindingResult bindingResult, String msg) {
        BasePacket packet = new BasePacket();
        packet.setResult(result);
        packet.setMsg(msg);
        if (bindingResult != null) {
            packet.setErrors(webUtil.getBindingErrors(bindingResult));
        }
        packet.setResponseTime(System.currentTimeMillis());
        if (e.getLocalizedMessage() != null) {
            packet.setException(e.getLocalizedMessage());
        }

        return setResponse(packet.toJsonBody(), status);
    }

    private ResponseEntity<String> setResponse(String body, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return new ResponseEntity<>(body, headers, status);
    }
}
