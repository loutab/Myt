package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/4/27/027.
 */

public class ChangePwdJson {

    /**
     * Code : 失败
     * Exception : 新密码与原密码相同！
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
