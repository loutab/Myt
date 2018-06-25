package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/6/25.
 */

public class ChangePhoneBean {
    /**
     * Code : 改绑成功
     * Success : 新账号为：13764929873
     */

    private String Code;
    private String Success;
    private String Exception;

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }
}
