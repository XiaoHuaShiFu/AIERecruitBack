package cn.scauaie.common.error;

import org.springframework.http.HttpStatus;

/**
 * 描述: 错误码与错误信息的映射
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-08 19:09
 */
public enum ErrorCode {

    /**
     * 参数为null
     */
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,
            "InvalidParameter", "The {Parameter} is not valid."),

    /**
     * 参数为null
     */
    INVALID_PARAMETER_IS_NULL(HttpStatus.BAD_REQUEST,
            "InvalidParameter.IsNull", "The required {Parameter} must be not null."),

    /**
     * 参数为空
     */
    INVALID_PARAMETER_IS_BLANK(HttpStatus.BAD_REQUEST,
            "InvalidParameter.IsBlank", "The required {Parameter} must be not blank."),

    /**
     * 参数值超过限定范围，Number类型
     */
    INVALID_PARAMETER_VALUE_EXCEEDED(HttpStatus.BAD_REQUEST,
            "InvalidParameter.ValueExceeded", "The value of {Parameter} exceeded, max: {Value}."),

    /**
     * 参数值小于限定范围，Number类型
     */
    INVALID_PARAMETER_VALUE_BELOW(HttpStatus.BAD_REQUEST,
            "InvalidParameter.ValueBelow", "The value of {Parameter} below, min: {Value}."),

    /**
     * 参数长度不在规定范围内，String类型
     */
    INVALID_PARAMETER_SIZE(HttpStatus.BAD_REQUEST,
            "InvalidParameter.Size", "The size of {Parameter} must be from {Min} to {Max}."),

    /**
     * 参数长度不在规定范围内，String类型
     */
    OPERATION_CONFLICT(HttpStatus.CONFLICT,
            "OperationConflict", "Request was denied due to conflict with a previous request.");


    private final HttpStatus httpStatus;
    private final String error;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.error = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
