package com.example.administrator.partymemberconstruction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.ChangePwdJson;
import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeMyPwsActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.headtitle)
    RelativeLayout headtitle;
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.old_psw)
    EditText oldPsw;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.new_psw)
    EditText newPsw;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.sure_psw)
    EditText surePsw;
    @BindView(R.id.sure)
    TextView sure;
    private String oldPass;
    private String newPass;
    private String surePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_my_pws);
        ButterKnife.bind(this);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDate();
            }
        });
    }

    private void checkDate() {
        oldPass = oldPsw.getText() + "";
        newPass = newPsw.getText() + "";
        surePass = surePsw.getText() + "";
        if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(surePass)) {
            MyApplication.showToast("数据不能为空", 0);
        } else {
            if (!oldPass.equals(MyApplication.psw)) {
                MyApplication.showToast("密码错误", 0);
                oldPsw.setText("");
            } else {
                if (!RegisterActivity.isContainAll(newPass) || newPass.length() < 6 || newPass.length() > 12) {
                    MyApplication.showToast("密码不符合规则，必须包含数字与字母且位数为6-12位", 0);
                    newPsw.setText("");
                    surePsw.setText("");
                } else {
                    if (!newPass.equals(surePass)) {
                        MyApplication.showToast("两次密码输入不一致", 0);
                        newPsw.setText("");
                        surePsw.setText("");
                    } else {
                        changeDate(surePass);
                    }
                }
            }
        }
    }

    private void changeDate(String passWord) {
        HashMap<String, String> params = new HashMap<>();
        params.put("UserName", MyApplication.user.getUser_ID() + "");
        params.put("Password", MyApplication.psw);
        params.put("NewPassword", passWord);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.ChangePassW, params, ChangePwdJson.class,
                new OkhttpJsonUtil.TextCallBack<ChangePwdJson>() {
                    @Override
                    public void getResult(ChangePwdJson result) {
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                MyApplication.showToast("修改成功", 0);
                                finish();
                            } else {
                                MyApplication.showToast(result.getException(), 0);
                            }
                        } else {

                        }
                    }
                }
        );
    }
}
