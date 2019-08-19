package cn.scauaie.result;

/**
 * 描述: 错误响应对象
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-08 19:59
 */
public class ErrorResponse {

    /**
     * 错误码
     */
    private String error;

    /**
     * 错误信息
     */
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
