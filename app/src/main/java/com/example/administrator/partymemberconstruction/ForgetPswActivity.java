package com.example.administrator.partymemberconstruction;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.example.administrator.partymemberconstruction.Bean.ImgCodeJson;
import com.example.administrator.partymemberconstruction.CustomView.ForgetCodeDialog;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.example.administrator.partymemberconstruction.utils.Validate;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPswActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.psw)
    EditText psw;
    @BindView(R.id.codeNum)
    EditText codeNum;
    @BindView(R.id.code)
    ImageView code;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.count)
    EditText count;
    @BindView(R.id.first)
    LinearLayout first;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code2)
    TextView code2;
    @BindView(R.id.edtCode)
    EditText edtCode;
    @BindView(R.id.second)
    LinearLayout second;
    @BindView(R.id.surePsw)
    EditText surePsw;
    @BindView(R.id.third)
    LinearLayout third;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.see)
    ImageView see;


    private int step = 2;
    private CountDownTimer timer;
    private int judge;
    private ForgetCodeDialog forgetCodeDialog;
    Boolean isShow=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
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
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        two.setTextColor(getResources().getColor(R.color.black));

        //第一步
        register.setOnClickListener(nextListener);

        secondStep();
        see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(isShow){
//                psw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                isShow=true;
//                }
//                else{
//                psw.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                isShow=false;
//                }
                pwdShow(psw);}
        });

    }
    public void pwdShow(EditText editText){
        int type = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        if(isShow){
            editText.setInputType(type);
            editText.setSelection(editText.getText().length());
            isShow=false;
        }

        if(editText.getInputType() == type){//密码可见
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setSelection(editText.getText().length());     //把光标设置到当前文本末尾

        }else{
            editText.setInputType(type);
            editText.setSelection(editText.getText().length());
        }


    }

    private void getImgCode() {
        HashMap<String, String> params = new HashMap<>();
        OkhttpJsonUtil.getInstance().postByEnqueue(ForgetPswActivity.this, Url.GetImaUrl, params, ImgCodeJson.class,
                new OkhttpJsonUtil.TextCallBack<ImgCodeJson>() {
                    @Override
                    public void getResult(ImgCodeJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            String imgCodeUrl = result.getImgCode();
                            Picasso.with(ForgetPswActivity.this).load(imgCodeUrl).into(forgetCodeDialog.getCode(),
                                    new Callback() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onError() {
                                            MyApplication.showToast("验证码加载失败", 0);
                                        }
                                    });
                            judge = result.getJudge();
                        } else {
                            MyApplication.showToast("获取验证码失败", 0);
                            timer.onFinish();
                            timer.cancel();
                        }

                    }
                });
    }

    View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (step) {
                case 1:
                    //验证数据是否为空
                    String countNum = count.getText() + "";
                    String codeNumber = codeNum.getText() + "";
                    if (TextUtils.isEmpty(codeNumber) || TextUtils.isEmpty(countNum)) {
                        MyApplication.showToast("请输入账号和验证码", 0);
                    } else {
                        //验证验证码
                        checkCode(codeNumber);
                    }
                    break;
                case 2:
                    //验证数据是否为空
                    String phoneNum = phone.getText() + "";
                    String codeNumber1 = edtCode.getText() + "";
                    if (TextUtils.isEmpty(codeNumber1) || TextUtils.isEmpty(phoneNum)) {
                        MyApplication.showToast("请输入手机和验证码", 0);
                    } else {
                        //验证验证码
                        checkCode2(phoneNum, codeNumber1);
                    }
                    break;
                case 3:
                    //验证密码是否为空
                    String onePsw = psw.getText() + "";
                    String twoPsw = surePsw.getText() + "";
                    if (TextUtils.isEmpty(onePsw) || TextUtils.isEmpty(twoPsw)) {
                        MyApplication.showToast("请输入密码", 0);
                    } else {
                        //验证验证码
                        if (onePsw.equals(twoPsw)) {
//调用修改密码接口
                            HashMap<String, String> params = new HashMap<>();
                            params.put("UserName", phone.getText() + "");
                            params.put("Password", onePsw);
                            OkhttpJsonUtil.getInstance().postByEnqueue(ForgetPswActivity.this, Url.ChangePassWordUrl, params, CheckCodeJson.class,
                                    new OkhttpJsonUtil.TextCallBack<CheckCodeJson>() {
                                        @Override
                                        public void getResult(CheckCodeJson result) {
                                            // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                                            if (result != null) {
                                                Log.d("p", result.getCode());
                                                if (result.getCode().equals("成功")) {
                                                    //修改成功返回登录页
                                                    finish();
                                                    MyApplication.showToast("修改成功", 0);
                                                } else
                                                    MyApplication.showToast(result.getException(), 0);
                                            }

                                        }
                                    });
                        } else {
                            MyApplication.showToast("两次密码输入不一致，请重新输入", 0);
                        }
                    }
                    //验证两次密码
                    break;

            }
        }
    };

    //验证第二步验证码
    private void checkCode2(String phoneNum, String codeNumber1) {
        HashMap<String, String> params = new HashMap<>();
        params.put("PhoneNum", phoneNum);
        params.put("code", codeNumber1);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.CheckCodeUrl, params, CheckCodeJson.class,
                new OkhttpJsonUtil.TextCallBack<CheckCodeJson>() {
                    @Override
                    public void getResult(CheckCodeJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            Log.d("p", result.getCode());
                            if (result.getCode().equals("成功")) {
                                //进入第三步
                                step = 3;
                                thirdStep();
                            } else{
                                MyApplication.showToast(result.getException(), 0);
                                timer.onFinish();
                                timer.cancel();
                            }

                            //测试
                            //进入第三步
//                            step = 3;
//                            thirdStep();
                        }

                    }
                });
    }

    //显示第三部页面
    private void thirdStep() {
        register.setText("确定");
        second.setVisibility(View.GONE);
        third.setVisibility(View.VISIBLE);
        three.setTextColor(getResources().getColor(R.color.black));
        two.setTextColor(getResources().getColor(R.color.gray));

    }

    //验证第一步验证码
    private void checkCode(String codeNumber) {
        if (codeNumber.equals(judge + "")) {
            //验证码正确
            step = 2;
            secondStep();
        } else {
            MyApplication.showToast("验证码错误", 0);
        }

    }

    //显示第二部页面
    private void secondStep() {
        first.setVisibility(View.GONE);
        second.setVisibility(View.VISIBLE);
        one.setTextColor(getResources().getColor(R.color.gray));
        two.setTextColor(getResources().getColor(R.color.black));
        code2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = phone.getText() + "";
                //验证手机号码
                if (phoneNum != null & phoneNum.length() == 11& Validate.isTrue(phoneNum)) {
                    //请求验证码接口
                    forgetCodeDialog = new ForgetCodeDialog(ForgetPswActivity.this);
                    forgetCodeDialog.show();
                    forgetCodeDialog.getSure().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = forgetCodeDialog.getCodeNum().getText() + "";
                            if (TextUtils.isEmpty(s)) {
                                MyApplication.showToast("请输入验证码", 0);
                            } else {
                                //测试
                                //getCodeTwo(phone.getText() + "");
                                if (s.equals(judge + "")) {
                                    //验证码正确
                                    getCodeTwo(phone.getText() + "");
                                    forgetCodeDialog.cancel();
                                } else {
                                    MyApplication.showToast("验证码错误", 0);
                                }
                            }
                        }
                    });
                    //验证码点击刷新
                    forgetCodeDialog.getCode().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getImgCode();
                        }
                    });
                    //获得图片验证码
                    getImgCode();
                } else {
                    MyApplication.showToast("请输入正确手机号码", 0);
                }
            }
        });


    }

    //获得第二部验证码
    private void getCodeTwo(String phoneNum) {
        code2.setEnabled(false);
        code2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_code_gray));
        //设置60秒验证码时效
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                code2.setText(millisUntilFinished / 1000 + "S");
            }

            @Override
            public void onFinish() {
                code2.setEnabled(true);
                code2.setText("获取验证码");
                code2.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_code_red));
            }
        }.start();
        //调用验证码接口
        getForgetCheck(phoneNum);
    }

    private void getForgetCheck(String phoneNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("PhoneNum", phoneNum);
        params.put("chkType", "1");
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
                        } else {
                            MyApplication.showToast("获取验证码失败", 0);
                            timer.onFinish();
                            timer.cancel();
                        }

                    }
                });
    }

}
