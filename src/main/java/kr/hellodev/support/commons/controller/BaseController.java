package kr.hellodev.support.commons.controller;

import kr.hellodev.support.commons.binding.BasePacket;
import kr.hellodev.support.commons.binding.BaseResult;
import kr.hellodev.support.commons.binding.IResult;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import javax.annotation.Resource;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 26/11/2018 3:38 PM
 */
public class BaseController {

    @Resource(name = "messageSourceAccessor")
    private MessageSourceAccessor accessor;

    public BasePacket doMessagePacket(IResult result) {
        BasePacket packet = new BasePacket();
        return doMessagePacket(result, packet);
    }

    public BasePacket doMessagePacket(IResult result, BasePacket packet) {
        return doMessagePacket(result, packet, System.currentTimeMillis());
    }

    public BasePacket doMessagePacket(IResult result, BasePacket packet, long responseTime) {
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
        return doMessagePacket(BaseResult.SUCCESS_OK, packet, responseTime);
    }
}
