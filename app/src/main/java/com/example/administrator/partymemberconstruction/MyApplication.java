package com.example.administrator.partymemberconstruction;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.administrator.partymemberconstruction.Bean.ContactsPersonBean;
import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.Bean.PartMJson;
import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.utils.APIWebviewTBS;
import com.example.administrator.partymemberconstruction.utils.CrashHandler;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/3/19/019.
 */

public class MyApplication extends Application {
    public static Context context;
    private static Toast toast;
    public static UserJson.UserInfoBean user;
    public static String psw;
    public static String phone;
    public static String otherHead="";
    public static ContactsPersonBean.UserinfoBean otherBean=null;
    public static List<GroupJson.TissueTreeBean> GroupDate=null;
    public static List<PartMJson.DepartListBean> PartDate=null;

    APIWebviewTBS mAPIWebviewTBS;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //CrashHandler.getInstance().initCrashHandler(this);

        //个人封装，针对升级----开始
        mAPIWebviewTBS=APIWebviewTBS.getAPIWebview();
        mAPIWebviewTBS.initTbs(getApplicationContext());
        //个人封装，针对升级----结束
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

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
