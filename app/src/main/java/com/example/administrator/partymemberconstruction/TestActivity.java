package com.example.administrator.partymemberconstruction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.partymemberconstruction.CustomView.MTabHost;
import com.example.administrator.partymemberconstruction.CustomView.MyTabHost;
import com.example.administrator.partymemberconstruction.fragment.FirstFragment;
import com.example.administrator.partymemberconstruction.fragment.MineFragment;
import com.example.administrator.partymemberconstruction.fragment.NoticeFragment;
import com.example.administrator.partymemberconstruction.fragment.NoticeMessageFragment;
import com.example.administrator.partymemberconstruction.fragment.PartyConstructionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {


    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabhost)
    MyTabHost th;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
       // getFragment(NoticeMessageFragment.class);
      //  NoticeMessageFragment noticeMessageFragment = new NoticeMessageFragment();
        initTable();
    }

    private void initTable() {
        th.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        th.setTextPosition(MTabHost.BOTTOM_TEXTPOSITION);

        th.addTab(R.mipmap.study2, R.mipmap.study1, "学习", 3, 1,FirstFragment.class);
        th.addTab(R.mipmap.partyconstruction2, R.mipmap.partyconstruction1, "党建", 3, 1,PartyConstructionFragment.class);
        th.addTab(R.mipmap.notice2, R.mipmap.notice1, "通知", 3, 1,NoticeFragment.class);
        th.addTab(R.mipmap.mine2, R.mipmap.mine1, "个人中心", 3, 1,MineFragment.class);
    }

    private void getFragment(Class<?> noticeMessageFragmentClass) {
        String name = noticeMessageFragmentClass.getName();
        MyApplication.showToast(name, 1);
        try {
            Fragment aClass = (Fragment) Class.forName(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
