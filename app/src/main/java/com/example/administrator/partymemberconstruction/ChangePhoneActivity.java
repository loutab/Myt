package com.example.administrator.partymemberconstruction;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.CheckCodeJson;
import com.example.administrator.partymemberconstruction.Bean.CodeJson;
import com.example.administrator.partymemberconstruction.Bean.PostImgJson;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePhoneActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.headtitle)
    RelativeLayout headtitle;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.code2)
    TextView code2;
    @BindView(R.id.edtCode)
    EditText edtCode;
    @BindView(R.id.second)
    LinearLayout second;
    @BindView(R.id.sure)
    TextView sure;
    private CountDownTimer timer;
    private String newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        code2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = ChangePhoneActivity.this.phone.getText() + "";
                if (!TextUtils.isEmpty(phone) && phone.length() == 11) {
                    getCodeTwo(phone);
                } else {
                    MyApplication.showToast("请输入正确手机号码", 0);
                }
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhone = phone.getText() + "";
                String s1 = edtCode.getText() + "";
                if (TextUtils.isEmpty(newPhone) || TextUtils.isEmpty(s1)) {
                    MyApplication.showToast("数据不能为空", 0);
                } else {
                    checkCode(s1);

                }
            }
        });
    }

    private void checkCode(String codeNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("PhoneNum", MyApplication.phone);
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
                                changePhone(newPhone);
                            } else
                                MyApplication.showToast(result.getException(), 0);
                        }

                    }
                });
    }

    private void changePhone(String s) {
        //绑定用户手机
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", MyApplication.user.getUser_ID() + "");
        params.put("newPhone", s);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.CodeUrl, params, PostImgJson.class,
                new OkhttpJsonUtil.TextCallBack<PostImgJson>() {
                    @Override
                    public void getResult(PostImgJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                MyApplication.showToast(result.getSuccess(), 0);
                            } else {
                                MyApplication.showToast(result.getError(), 0);
                            }
                        } else {
                            MyApplication.showToast("改绑失败", 0);
                        }
                    }
                });
    }

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
