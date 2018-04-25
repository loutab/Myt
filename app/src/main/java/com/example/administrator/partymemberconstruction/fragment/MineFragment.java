package com.example.administrator.partymemberconstruction.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.MineListAdapter;
import com.example.administrator.partymemberconstruction.ContactsActivity;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.SettingActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class MineFragment extends Fragment {


    @BindView(R.id.headImg)
    CircleImageView headImg;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.introduction)
    TextView introduction;
    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.right)
    TextView right;
    @BindView(R.id.contacts)
    LinearLayout contacts;
    @BindView(R.id.group)
    LinearLayout group;
    @BindView(R.id.personalData)
    LinearLayout personalData;
    @BindView(R.id.integral)
    LinearLayout integral;
    @BindView(R.id.list)
    ListView list;
    Unbinder unbinder;
    private List<String[]> lists;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        MineListAdapter mineListAdapter=new MineListAdapter(getContext(),lists);
        list.setAdapter(mineListAdapter);
        //设置联系人点击事件
        contacts.setClickable(true);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoActivity(ContactsActivity.class);
            }
        });
        //设置设置点击事件
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SettingActivity.class);
            }
        });
    }

    private void gotoActivity(Class c) {
        Intent intent=new Intent(getContext(),c);
        startActivity(intent);
    }

    private void initDate() {
        lists = new ArrayList<>();
        lists.add(new String[]{"我发的消息",R.mipmap.mymessage+""});
        lists.add(new String[]{"我的收藏",R.mipmap.mycollect+""});
        String userName=MyApplication.user.getUi_NickName()==null?"":MyApplication.user.getUi_NickName();
        name.setText(userName);
        String url=MyApplication.user.getUi_Headimg()==null|MyApplication.user.getUi_Headimg()==""?"wwww":MyApplication.user.getUi_Headimg();
        Picasso.with(this.getContext()).load(url).into(headImg, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                headImg.setImageResource(R.mipmap.default_head);
MyApplication.showToast("头像加载失败",0);
            }
        });
        String introduce=MyApplication.user.getUi_Introduction()==null?"":MyApplication.user.getUi_Introduction();
        introduction.setText(introduce);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
