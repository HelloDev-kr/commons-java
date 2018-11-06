package kr.hellodev.support.commons.binding;

import lombok.AllArgsConstructor;

/**
 * Project : commons-java
 * Developer : acu77
 * Date : 06/11/2018 3:31 PM
 */
@AllArgsConstructor
public enum BaseResult implements IResult {
    SUCCESS_OK(0), // 성공

    FATAL_BINDING_ERROR(991), // 잘못된 값이 넘어왔습니다.
    FATAL_USER_ILLEGAL_ARGUMENT(992), // 잘못된 파라미터가 넘어왔습니다.
    FATAL_REQUEST_METHOD_NOT_SUPPORTED(993), // {0} 메소드는 지원하지 않는 메소드입니다.
    FATAL_COULD_NOT_READ_JSON(994), // 잘못된 JSON 데이터가 넘어왔습니다.
    FATAL_SQL_EXCEPTION(995), // SQL 에러가 발생하였습니다.
    FATAL_SYS_EXCEPTION(999); // 서버 에러가 발생하였습니다.

    private final int id;

    @Override
    public int code() {
        return this.id;
    }

    @Override
    public String msg() {
        return SERVER_MSG + this.id;
    }
}
