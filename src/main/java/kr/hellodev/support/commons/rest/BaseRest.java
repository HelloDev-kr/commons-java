package kr.hellodev.support.commons.rest;

import kr.hellodev.support.commons.binding.BasePacket;
import kr.hellodev.support.commons.binding.BaseResult;
import kr.hellodev.support.commons.binding.IResult;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import javax.annotation.Resource;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 07/11/2018 11:24 AM
 */
public class BaseRest {

    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor accessor;

    public BasePacket doMessagePacket(IResult result) {
        BasePacket packet = new BasePacket();
        return doMessagePacket(packet, result);
    }

    public BasePacket doMessagePacket(BasePacket packet, IResult result) {
        return doMessagePacket(packet, result, System.currentTimeMillis());
    }

    public BasePacket doMessagePacket(BasePacket packet, IResult result, long responseTime) {
        packet.setResult(result);
        packet.setMsg(accessor.getMessage(result.msg(), LocaleContextHolder.getLocale()));
        packet.setResponseTime(responseTime);
        return packet;
    }

    public BasePacket doSuccessPacket() {
        BasePacket packet = new BasePacket();
        return doSuccessPacket(packet);
    }

    public BasePacket doSuccessPacket(BasePacket packet) {
        return doSuccessPacket(packet, System.currentTimeMillis());
    }

    public BasePacket doSuccessPacket(BasePacket packet, long responseTime) {
        return doMessagePacket(packet, BaseResult.SUCCESS_OK, responseTime);
    }
}
