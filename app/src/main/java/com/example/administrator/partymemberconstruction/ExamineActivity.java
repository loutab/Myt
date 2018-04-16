package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examine);
        ButterKnife.bind(this);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(MyApplication.user==null){}
        else{
        String state = getIntent().getStringExtra("state");
        if(state.equals("2")){
            //需要传入用户ID
            userId = getIntent().getStringExtra("userId");
            newbtn.setVisibility(View.VISIBLE);
            newbtn.setClickable(true);
            newbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            img.setImageDrawable(getResources().getDrawable(R.mipmap.cross));
            txt.setText("审核失败");
            txt.setTextColor(getResources().getColor(R.color.black));
        }else if(state.equals("3")){
            Intent  intent=new Intent(ExamineActivity.this,ImprovePersonalInformationActivity.class);
            intent.putExtra("userId",userId);
            startActivity(intent);
        }
    }}
}
