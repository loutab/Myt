package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/3/22.
 */

public class CheckCodeJson {

    /**
     * Code : 失败
     * Exception : 请填写正确验证码！
     */

    private String Code;
    private String Exception;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String Exception) {
        this.Exception = Exception;
    }
}
