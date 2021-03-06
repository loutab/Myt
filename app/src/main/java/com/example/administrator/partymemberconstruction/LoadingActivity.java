package com.example.administrator.partymemberconstruction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.Bean.PartMJson;
import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.utils.ComenUtils;
import com.example.administrator.partymemberconstruction.utils.MobileInfoUtil;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.example.administrator.partymemberconstruction.utils.Validate;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;


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
        if(!getScreenDensity_ByWindowManager()){
            float scale=getResources().getDisplayMetrics().density;
            party.setHeight((int)(80*scale+0.5f));
        }
        JPushInterface.setAlias(this, 101, MobileInfoUtil.getIMEI(this));
        //JPushInterface.setAlias(this, 101, "12345678a");
        SharedPreferences load = getSharedPreferences("load", Context.MODE_PRIVATE);
        sp = getPreferences(Context.MODE_PRIVATE);
        boolean yes = load.getBoolean("yes", false);
        if (yes) {
            MyApplication.phone = sp.getString("userPhone", "");
            MyApplication.psw = sp.getString("userPsw", "");
            MyApplication.user = new UserJson.UserInfoBean();
            MyApplication.user.setUser_ID(sp.getInt("useId", 0));
            Intent intent = new Intent(this, FirstActivity.class);
            FirstActivity.YES = 1;
            startActivity(intent);
            finish();
        }
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        //加载组织与部门数据
        getGroupDate();
        getPartDate();

        Log.d("w", ComenUtils.ChangeTime("2018-03-13 11:20:36"));

        String userName = sp.getString("userName", "");
        Log.e("tsd", userName + "7777");
        if (TextUtils.isEmpty(MyApplication.phone))
            userEdt.setText(userName);
        else
            userEdt.setText(MyApplication.phone);
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
                if (ComenUtils.isFastClick())
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

    //获得屏幕分辨率
    public boolean getScreenDensity_ByWindowManager() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        return ((float) width / (float) height) == ((float) (9.00 / 16.00));
    }

    private void gotoLoading() {
        //验证数据不为空
        String userName = userEdt.getText() + "";
        trueName = userEdt.getText() + "";
        final String passWord = passwordEdt.getText() + "";
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            MyApplication.showToast("用户名或密码不能为空", 0);
        } else if (!Validate.isTrue(userName)) {
            userEdt.setText("");
            MyApplication.showToast("请输入正确手机号码", 0);
        } else if (passWord.length() < 6 || passWord.length() > 12) {
            //限制密码位数
            MyApplication.showToast("密码位数为6-12位", 0);
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("UserName", userName);
            params.put("Password", passWord);
            MyApplication.psw = passWord;
            OkhttpJsonUtil.getInstance().postByEnqueue(LoadingActivity.this, Url.LoadingUrl, params, UserJson.class,
                    new OkhttpJsonUtil.TextCallBack<UserJson>() {
                        @Override
                        public void getResult(UserJson result) {
                            // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                            if (result != null) {
                                Log.d("p", result.getCode());
                                if (result.getCode().equals("成功")) {

                                    String pwd = passwordEdt.getText() + "";

                                    MyApplication.phone = userEdt.getText() + "";
                                    //记住用户名
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putString("userPhone", MyApplication.phone);
                                    edit.putString("userPsw", MyApplication.psw);
                                    edit.putString("userName", trueName);
                                    edit.commit();
                                    //根据状态选择进入的页面
                                    //finish();
                                    int status = result.getStatus();
                                    switch (status) {
                                        case 0:
                                            Intent intent = new Intent(LoadingActivity.this, ExamineActivity.class);
                                            intent.putExtra("state", "" + result.getStatus());
                                            intent.putExtra("userId", result.getUser_id() + "");
                                            startActivity(intent);
                                            break;
                                        //跳转到首页
                                        case 1:
                                            //记住登录状态
                                            SharedPreferences load = getSharedPreferences("load", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor edit1 = load.edit();
                                            edit1.putBoolean("yes", true);
                                            edit1.commit();

                                            FirstActivity.YES = 1;
                                            //全局化用户信息
                                            MyApplication.outUserId = result.getUserInfo().getUser_ID() + "";
                                            MyApplication.user = result.getUserInfo() == null ? new UserJson.UserInfoBean() : result.getUserInfo();
                                            sp.edit().putInt("userId", MyApplication.user.getUser_ID()).commit();
                                            HashMap<String, String> params = new HashMap<>();
                                            String imei = MobileInfoUtil.getIMEI(LoadingActivity.this);
                                            params.put("userId", MyApplication.user.getUser_ID() + "");
                                            params.put("equitmentId", imei + "");
                                            OkhttpJsonUtil.getInstance().postByEnqueue(LoadingActivity.this, Url.BindIMEI, params, UserJson.class,
                                                    new OkhttpJsonUtil.TextCallBack<UserJson>() {
                                                        @Override
                                                        public void getResult(UserJson result) {
                                                            boolean b = result == null;
                                                        }
                                                    });
                                            gotoActivity(FirstActivity.class);
                                            finish();


//                                            //测试完善信息页面
//                                            Intent intentTest = new Intent(LoadingActivity.this, ExamineActivity.class);
//                                            intentTest.putExtra("userId","1");
//                                            intentTest.putExtra("state","0");
//                                            startActivity(intentTest);

                                            break;
                                        case 2:
                                            Intent intent1 = new Intent(LoadingActivity.this, ExamineActivity.class);
                                            intent1.putExtra("state", "" + result.getStatus());
                                            intent1.putExtra("userId", result.getUser_id());
                                            intent1.putExtra("Error", result.getError());
                                            startActivity(intent1);
                                            break;
                                        case 3:
                                            //跳转到完善信息页面
                                            Intent intent3 = new Intent(LoadingActivity.this, ImprovePersonalInformationActivity.class);
                                            intent3.putExtra("userId", result.getUserInfo().getEntityId() + "");
                                            startActivity(intent3);
                                            break;
                                    }
                                    //登录成功进入首页
                                } else {
                                    isOne = true;
                                    MyApplication.showToast(result.getException(), 0);

                                }
                            } else {
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

    boolean isOne = true;
    View.OnKeyListener onKey = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //这里写发送信息的方法
                if (isOne) {
                    gotoLoading();
                    isOne = false;
                }
            }
            return false;
        }
    };

    private void getGroupDate() {
        HashMap<String, String> params = new HashMap<>();
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.GroupDateUrl, params, GroupJson.class,
                new OkhttpJsonUtil.TextCallBack<GroupJson>() {
                    @Override
                    public void getResult(GroupJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                List<GroupJson.TissueTreeBean> tissue_tree = result.getTissue_tree();
                                if (tissue_tree.size() > 0) {
                                    MyApplication.GroupDate = tissue_tree;
                                }
                            }
                        }
                    }
                });
    }

    private void getPartDate() {
        HashMap<String, String> params = new HashMap<>();
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.PartDateUrl, params, PartMJson.class,
                new OkhttpJsonUtil.TextCallBack<PartMJson>() {
                    @Override
                    public void getResult(PartMJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                List<PartMJson.DepartListBean> departList = result.getDepartList();
                                if (departList.size() > 0) {
                                    MyApplication.PartDate = departList;
                                }
                            }
                        }
                    }
                });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long mExitTime;

    //返回键重写
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(LoadingActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
