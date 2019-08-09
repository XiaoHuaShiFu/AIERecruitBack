package cn.scauaie.common.error;

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
    INVALID_PARAMETER(
            "InvalidParameter", "The {Parameter} is not valid."),

    /**
     * 参数为null
     */
    INVALID_PARAMETER_IS_NULL(
            "InvalidParameter.IsNull", "The required {Parameter} must be not null."),

    /**
     * 参数为空
     */
    INVALID_PARAMETER_IS_BLANK(
            "InvalidParameter.IsBlank", "The required {Parameter} must be not blank."),

    /**
     * 参数值超过限定范围，Number类型
     */
    INVALID_PARAMETER_VALUE_EXCEEDED(
            "InvalidParameter.ValueExceeded", "The value of {Parameter} exceeded, max: {Value}."),

    /**
     * 参数值小于限定范围，Number类型
     */
    INVALID_PARAMETER_VALUE_BELOW(
            "InvalidParameter.ValueBelow", "The value of {Parameter} below, min: {Value}."),

    /**
     * 参数长度不在规定范围内，String类型
     */
    INVALID_PARAMETER_SIZE(
            "InvalidParameter.Size", "The size of {Parameter} must be from {Min} to {Max}."),

    /**
     * 参数长度不在规定范围内，String类型
     */
    OPERATION_CONFLICT(
            "OperationConflict", "Request was denied due to conflict with a previous request.");





    private final String error;
    private final String message;

    ErrorCode(String code, String message) {
        this.error = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return error;
    }

}
