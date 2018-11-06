package kr.hellodev.support.commons.binding;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 2:55 PM
 */
public interface IResult {

    static final String SERVER_MSG = "SERVER.MSG.";

    int code();

    String msg();
}
