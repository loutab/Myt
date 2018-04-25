package com.example.administrator.partymemberconstruction.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.MineListAdapter;
import com.example.administrator.partymemberconstruction.Bean.SelfInfo;
import com.example.administrator.partymemberconstruction.Bean.SignJson;
import com.example.administrator.partymemberconstruction.ContactsActivity;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.SettingActivity;
import com.example.administrator.partymemberconstruction.WebActivity;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
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
    private SelfInfo.MenuListBean menuListBean;
    private SelfInfo.MenuListBean collect;
    private SelfInfo.MenuListBean inter;
    private SelfInfo.MenuListBean selfInfo;

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
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){
                    if (collect!=null)
                    gotoNewActivity(collect.getMenu_Url(),collect.getMenu_Name());
                }
            }
        });
        integral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inter!=null)
                gotoNewActivity(inter.getMenu_Url(),inter.getMenu_Name());
            }
        });
        personalData.setClickable(true);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selfInfo!=null)
                gotoNewActivity(MineFragment.this.selfInfo.getMenu_Url(), MineFragment.this.selfInfo.getMenu_Name());
            }
        });
        getSelf();
    }

    private void getSelf() {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID", MyApplication.user.getUser_ID() + "");
        params.put("Resol_Type", "1");
        params.put("type", "3");
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.FirstUrl, params, SelfInfo.class,
                new OkhttpJsonUtil.TextCallBack<SelfInfo>() {
                    @Override
                    public void getResult(SelfInfo result) {
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                final List<SelfInfo.MenuListBean> menu_list = result.getMenu_List();
                                for (SelfInfo.MenuListBean bean:menu_list) {
                                    menuListBean = bean;
                                    switch (menuListBean.getMenu_Name()){
                                        case "个人资料":
                                            selfInfo = MineFragment.this.menuListBean;

                                            break;
                                        case  "积分":
                                            inter = MineFragment.this.menuListBean;

                                            break;
                                        case "我的收藏":
                                            collect = MineFragment.this.menuListBean;
                                            break;

                                    }
                                }

                            }
                        }
                            else
                                MyApplication.showToast("获取个人信息失败",0);
                    }
        }
                    );
    }

    private void gotoNewActivity(String currentmenu_url,String title) {
        Intent intent=new Intent(getContext(), WebActivity.class);
        intent.putExtra("Url",currentmenu_url);
        intent.putExtra("title",title);
        startActivity(intent);
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

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

}
