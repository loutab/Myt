package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/4/26/026.
 */

public class PostImgJson {
    /**
     * Code : 成功
     * success : http://101.201.109.90:3333/Upload/Head/20180427_560509.jpg
     * Error : 请选择上传的图片
     */

    private String Code;
    private String success;
    private String Error;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }
}
