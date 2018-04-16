package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class RegisterJson {

    /**
     * Code : 成功
     * UserId : 2
     * Exception : 该手机号已被注册！
     */

    private String Code;
    private int UserId;
    private String Exception;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String Exception) {
        this.Exception = Exception;
    }
}
