package com.example.administrator.partymemberconstruction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.CheckCodeJson;
import com.example.administrator.partymemberconstruction.Bean.CodeJson;
import com.example.administrator.partymemberconstruction.Bean.RegisterJson;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.edtCode)
    EditText edtCode;
    @BindView(R.id.psw)
    EditText psw;
    @BindView(R.id.surePsw)
    EditText surePsw;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.line1)
    LinearLayout line1;
    private String phoneNum;
    private CountDownTimer timer;
    private String codeNum;
    private String passWord;
    private String surePassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        line1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                return false;
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        link.setOnClickListener(listener);
        back.setOnClickListener(listener);
        //获得验证码
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验证手机号码
                phoneNum = phone.getText() + "";
                if (phoneNum != null & phoneNum.length() == 11) {
                    //调用验证码接口
                    getCode();
                } else {
                    MyApplication.showToast("请输入正确手机号码", 0);
                }
            }
        });
        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //所有数据非空判断
                if (isEmptyDate())
                    MyApplication.showToast("请完善信息", 0);
                else {
                    if(passWord.length()<6||passWord.length()>12){
                        MyApplication.showToast("密码位数超限，请重输", 0);
                    }
                    else if(!isContainAll(passWord)){
                        MyApplication.showToast("密码不符合规则，必须包含数字与字母", 0);
                    }
                    //判断两次密码是否正确
                    else if (passWord.equals(surePassWord)) {
                        //判断验证码
                        checkCode(codeNum);
                    } else {
                        MyApplication.showToast("两次密码不一致，请重输", 0);
                    }
                }
            }
        });
    }

    //验证验证码
    private void checkCode(String codeNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("PhoneNum", phoneNum);
        params.put("code", codeNum);
        params.put("chkType", "0");
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.CheckCodeUrl, params, CheckCodeJson.class,
                new OkhttpJsonUtil.TextCallBack<CheckCodeJson>() {
                    @Override
                    public void getResult(CheckCodeJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            Log.d("p", result.getCode());
                            if (result.getCode().equals("成功")) {
                                //调用注册接口
                                gotoRegister();
                            } else
                                MyApplication.showToast(result.getException(), 0);
                        }

                    }
                });
    }

    //调用注册接口
    private void gotoRegister() {
        HashMap<String, String> params = new HashMap<>();
        params.put("UserName", phoneNum);
        params.put("PWD", passWord);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.RegisterUrl, params, RegisterJson.class,
                new OkhttpJsonUtil.TextCallBack<RegisterJson>() {
                    @Override
                    public void getResult(RegisterJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            Log.d("p", result.getCode());
                            if (result.getCode().equals("成功")) {
                                //注册成功跳入完善页面
                                Intent intent = new Intent(RegisterActivity.this, ImprovePersonalInformationActivity.class);
                                intent.putExtra("userId",result.getUserId());
                                startActivity(intent);
                                finish();
                            } else
                                MyApplication.showToast(result.getException(), 0);
                        }

                    }
                });
    }


    private boolean isEmptyDate() {
        phoneNum = phone.getText() + "";
        codeNum = edtCode.getText() + "";
        passWord = psw.getText() + "";
        surePassWord = surePsw.getText() + "";
        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(codeNum) || TextUtils.isEmpty(passWord) ||
                TextUtils.isEmpty(surePassWord)) {
            return true;
        }
        return false;
    }

    private void getCode() {
        code.setEnabled(false);
        code.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_code_gray));
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                code.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                code.setEnabled(true);
                code.setText("获取验证码");
                code.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_code_red));
            }
        }.start();
        HashMap<String, String> params = new HashMap<>();
        params.put("PhoneNum", phoneNum);
        params.put("chkType", "0");
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.CodeUrl, params, CodeJson.class,
                new OkhttpJsonUtil.TextCallBack<CodeJson>() {
                    @Override
                    public void getResult(CodeJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            Log.d("p", result.getCode());
                            if (!result.getCode().equals("成功")) {
                                MyApplication.showToast(result.getException(), 0);
                            }
                        }else{
                            MyApplication.showToast("获取验证码失败", 0);
                            timer.onFinish();
                            timer.cancel();
                        }

                        //code.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_code_red));
                    }
                });
    }
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        boolean isCase=isLowerCase|isUpperCase;
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit &&isCase && str.matches(regex);
        return isRight;
    }
}
