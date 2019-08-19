package cn.scauaie.model.result;

import cn.scauaie.error.ErrorCode;
import org.junit.Test;

public class ResultTest {

    @Test
    public void success() {
    }

    @Test
    public void fail() {
        System.out.println(Result.fail(ErrorCode.INVALID_OPERATION_NOT_FOUND.getError(), "Not found."));
    }

    @Test
    public void isSuccess() {
    }

    @Test
    public void setSuccess() {
    }

    @Test
    public void getData() {
    }

    @Test
    public void setData() {
    }

    @Test
    public void getErrorCode() {
    }

    @Test
    public void setErrorCode() {
    }

    @Test
    public void getMessage() {
    }

    @Test
    public void setMessage() {
    }
}