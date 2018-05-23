package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExamineActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.newbtn)
    TextView newbtn;
    @BindView(R.id.restartChange)
    TextView restartChange;
    private String userId;
    private int userIdtwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine);
        ButterKnife.bind(this);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamineActivity.this, LoadingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        String state = getIntent().getStringExtra("state");
        if (TextUtils.isEmpty(state)) {
        } else {
            if (state.equals("2")) {
                //需要传入用户ID
                userIdtwo = getIntent().getIntExtra("userId", 0);
                newbtn.setVisibility(View.VISIBLE);
                newbtn.setClickable(true);
                newbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ExamineActivity.this, ImprovePersonalInformationActivity.class);
                        intent.putExtra("userId", userIdtwo + "");
                        startActivity(intent);
                    }
                });
                img.setImageDrawable(getResources().getDrawable(R.mipmap.cross));
                txt.setText("审核失败");
                txt.setTextColor(getResources().getColor(R.color.black));
            } else if (state.equals("3")) {
                userId = getIntent().getStringExtra("userId");
                Intent intent = new Intent(ExamineActivity.this, ImprovePersonalInformationActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }else  if(state.equals("0")){
                restartChange.setClickable(true);
                restartChange.setVisibility(View.VISIBLE);
                restartChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userId = TextUtils.isEmpty(getIntent().getStringExtra("userId"))?"":getIntent().getStringExtra("userId");
                        Intent intent = new Intent(ExamineActivity.this, ImprovePersonalInformationActivity.class);
                        intent.putExtra("userId", userId);
                        intent.putExtra("isRestart","yes");
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
