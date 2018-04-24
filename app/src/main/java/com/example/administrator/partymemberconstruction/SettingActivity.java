package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.out:
                finish();
                Intent intent=new Intent(this,FirstActivity.class);
                intent.putExtra("exit", true);
                startActivity(intent);
                break;
        }
    }
}
