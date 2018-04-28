package com.example.administrator.partymemberconstruction;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.utils.CrashHandler;

/**
 * Created by Administrator on 2018/3/19/019.
 */

public class MyApplication extends Application {
    public static Context context;
    private static Toast toast;
    public static UserJson.UserInfoBean user;
    public static String psw;
    public static String phone;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //CrashHandler.getInstance().initCrashHandler(this);
    }
    public static void showToast(String msg, int length) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, length);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
