package com.example.administrator.partymemberconstruction.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.partymemberconstruction.MyApplication;

/**
 * Created by lwx on 2016/11/18.
 */

public class Validate {
   static String isPhone="[1][34578]\\d{9}";
    //验证身份证
    public static Boolean isTrue(String phone){
        String mobile=phone;
        if (mobile.startsWith("+86"))
            mobile = mobile.substring(3);
        if(!(mobile.matches(isPhone))){
           // MyApplication.showToast( "手机输入格式不正确", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
