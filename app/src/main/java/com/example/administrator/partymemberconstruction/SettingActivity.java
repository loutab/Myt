package com.example.administrator.partymemberconstruction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.CustomView.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.change_psw)
    RelativeLayout changePsw;
    @BindView(R.id.bind_account)
    RelativeLayout bindAccount;
    @BindView(R.id.voice)
    Switch voice;
    @BindView(R.id.shock)
    Switch shock;
    @BindView(R.id.delete)
    RelativeLayout delete;
    @BindView(R.id.about)
    RelativeLayout about;
    @BindView(R.id.out)
    TextView out;
    private String about1;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        about1 = intent.getStringExtra("about");
        back.setOnClickListener(this);
        out.setOnClickListener(this);
        changePsw.setOnClickListener(this);
        bindAccount.setOnClickListener(this);
        about.setOnClickListener(this);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("是否退出登录？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SignOut();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.out:
                builder.create().show();
                break;
            case R.id.change_psw:
                Intent intent1 = new Intent(this, ChangeMyPwsActivity.class);
                startActivity(intent1);
                break;
            case R.id.bind_account:
                Intent intent2 = new Intent(this, ChangePhoneActivity.class);
                startActivity(intent2);
                break;
            case R.id.about:
                Intent intent3 = new Intent(this, WebActivity.class);
                intent3.putExtra("Url", about1);
                intent3.putExtra("title", "关于党建");
                startActivity(intent3);
                break;
        }
    }

    private void SignOut() {
        finish();
        Intent intent = new Intent(this, FirstActivity.class);
        intent.putExtra("exit", true);
        startActivity(intent);
    }
}
