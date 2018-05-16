package com.example.administrator.partymemberconstruction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.utils.ComenUtils;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.example.administrator.partymemberconstruction.utils.Validate;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.party)
    TextView party;
    @BindView(R.id.userImg)
    ImageView userImg;
    @BindView(R.id.userEdt)
    EditText userEdt;
    @BindView(R.id.user)
    LinearLayout user;
    @BindView(R.id.passwordImg)
    ImageView passwordImg;
    @BindView(R.id.passwordEdt)
    EditText passwordEdt;
    @BindView(R.id.password)
    LinearLayout password;
    @BindView(R.id.loading)
    Button loading;
    @BindView(R.id.center)
    TextView center;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.forget)
    TextView forget;
    @BindView(R.id.line1)
    RelativeLayout line1;
    private SharedPreferences sp;
    private String trueName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }

        Log.d("w", ComenUtils.ChangeTime("2018-03-13 11:20:36"));
        sp = getPreferences(Context.MODE_PRIVATE);
        String userName = sp.getString("userName", "");
        userEdt.setText(userName);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        line1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                return false;
            }
        });
        passwordEdt.setOnKeyListener(onKey);
        //initState();
        loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //测试
//                Intent intent=new Intent(LoadingActivity.this,TestActivity.class);
//                startActivity(intent);
//                HashMap<String, String> params = new HashMap<>();
//                params.put("UserName", "13261011499");
//                params.put("Password", "12345678");
//                OkhttpJsonUtil.getInstance().postByEnqueue(LoadingActivity.this, Url.LoadingUrl, params, UserJson.class,
//                        new OkhttpJsonUtil.TextCallBack<UserJson>() {
//                            @Override
//                            public void getResult(UserJson result) {
//                                // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
//                                if (result != null) {
//                                    Log.d("p", result.getCode());
//                                    if (result.getCode().equals("成功")) {
//                                        //根据状态选择进入的页面
//                                        //finish();
//                                        int status = result.getStatus();
//                                        switch (status) {
//                                            case 0:
//                                                Intent intent = new Intent(LoadingActivity.this, ExamineActivity.class);
//                                                intent.putExtra("userId", "" + result.getStatus());
//                                                startActivity(intent);
//                                                break;
//                                            //跳转到首页
//                                            case 1:
//                                                //全局化用户信息
//                                                MyApplication.user = result.getUserInfo();
//                                                gotoActivity(FirstActivity.class);
//                                                //gotoActivity(TestWeb2Activity.class);
//                                                finish();
//                                                break;
//                                            case 2:
//                                                Intent intent1 = new Intent(LoadingActivity.this, ExamineActivity.class);
//                                                intent1.putExtra("userId", "" + result.getStatus());
//                                                startActivity(intent1);
//                                                break;
//                                            case 3:
//                                                //跳转到完善信息页面
//                                                Intent intent3 = new Intent(LoadingActivity.this, ImprovePersonalInformationActivity.class);
//                                                intent3.putExtra("userId", result.getUserInfo().getUser_ID());
//                                                startActivity(intent3);
//                                                break;
//                                        }
//                                        //登录成功进入首页
//                                    } else
//                                        MyApplication.showToast(result.getException(), 0);
//                                }
//
//                            }
//                        });
                //登录接口
                //MyApplication.showToast("登录",0);
                gotoLoading();
            }
        });

        register.setClickable(true);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(RegisterActivity.class);
            }
        });
        forget.setClickable(true);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(ForgetPswActivity.class);
            }
        });

    }

    private void gotoLoading() {
        //验证数据不为空
        String userName = userEdt.getText() + "";
        trueName = userEdt.getText() + "";
        final String passWord = passwordEdt.getText() + "";
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            MyApplication.showToast("用户名或密码不能为空", 0);
        }else if(!Validate.isTrue(userName)){
            userEdt.setText("");
            MyApplication.showToast("请输入正确手机号码", 0);
        }
        else if (passWord.length() < 6 || passWord.length() > 12) {
            //限制密码位数
            MyApplication.showToast("密码位数为6-12位", 0);
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("UserName", userName);
            params.put("Password", passWord);
            MyApplication.psw=passWord;
            OkhttpJsonUtil.getInstance().postByEnqueue(LoadingActivity.this, Url.LoadingUrl, params, UserJson.class,
                    new OkhttpJsonUtil.TextCallBack<UserJson>() {
                        @Override
                        public void getResult(UserJson result) {
                            // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                            if (result != null) {
                                Log.d("p", result.getCode());
                                if (result.getCode().equals("成功")) {

                                            String pwd=passwordEdt.getText() + "";

                                    MyApplication.phone=userEdt.getText() + "";
                                    //记住用户名
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putString("userName",trueName);
                                    edit.commit();
                                    //根据状态选择进入的页面
                                    //finish();
                                    int status = result.getStatus();
                                    switch (status) {
                                        case 0:
                                            Intent intent = new Intent(LoadingActivity.this, ExamineActivity.class);
                                            intent.putExtra("state", "" + result.getStatus());
                                            startActivity(intent);
                                            break;
                                        //跳转到首页
                                        case 1:
                                            //全局化用户信息
                                            MyApplication.user = result.getUserInfo();
                                            gotoActivity(FirstActivity.class);
                                            finish();
                                            break;
                                        case 2:
                                            Intent intent1 = new Intent(LoadingActivity.this, ExamineActivity.class);
                                            intent1.putExtra("state", "" + result.getStatus());
                                            intent1.putExtra("userId",result.getUser_id());
                                            startActivity(intent1);
                                            break;
                                        case 3:
                                            //跳转到完善信息页面
                                            Intent intent3 = new Intent(LoadingActivity.this, ImprovePersonalInformationActivity.class);
                                            intent3.putExtra("userId",result.getUserInfo().getEntityId()+"");
                                            startActivity(intent3);
                                            break;
                                    }
                                    //登录成功进入首页
                                } else{
                                    isOne=true;
                                    MyApplication.showToast(result.getException(), 0);

                                }
                            }else{
                                MyApplication.showToast("连接服务器失败", 0);
                            }

                        }
                    });
        }
    }

    private void gotoActivity(Class<?> registerActivityClass) {
        Intent intent = new Intent(LoadingActivity.this, registerActivityClass);
        startActivity(intent);
    }


    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
boolean isOne=true;
    View.OnKeyListener onKey = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //这里写发送信息的方法
                if(isOne){
                gotoLoading();
                isOne=false;
                }
            }
            return false;
        }
    };

}
