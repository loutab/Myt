package com.example.administrator.partymemberconstruction;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.partymemberconstruction.CustomView.MyTabHost;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {



    @BindView(R.id.img)
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        File file=new File("/storage/emulated/0/temp.png");
        Picasso.with(this).load(file).into(img);
        // getFragment(NoticeMessageFragment.class);
        //  NoticeMessageFragment noticeMessageFragment = new NoticeMessageFragment();
    }


}
