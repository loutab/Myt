package com.example.administrator.partymemberconstruction.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.PartyListAdapter;
import com.example.administrator.partymemberconstruction.Bean.FirstJson;
import com.example.administrator.partymemberconstruction.CustomView.CustomerGridView;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.WebActivity;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class PartyConstructionFragment extends Fragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.list)
    CustomerGridView list;
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.listOne)
    CustomerGridView listOne;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.listTwo)
    CustomerGridView listTwo;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.listThree)
    CustomerGridView listThree;
    Unbinder unbinder;
    private PartyListAdapter listAdapter;
    private PartyListAdapter listAdapter1;
    private PartyListAdapter listAdapter2;
    private PartyListAdapter listAdapter3;
    int typeScreen;
    List<FirstJson.MenuListBean> oneList=new ArrayList<>();
    List<FirstJson.MenuListBean> twoList=new ArrayList<>();
    List<FirstJson.MenuListBean> threeList=new ArrayList<>();
    List<FirstJson.MenuListBean> fourList=new ArrayList<>();

    public PartyConstructionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_party, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getScreenDensity_ByWindowManager();
        listAdapter = new PartyListAdapter(oneList,this.getContext(),1);
        listAdapter1 = new PartyListAdapter(twoList,this.getContext(),0);
        listAdapter2 = new PartyListAdapter(threeList,this.getContext(),0);
        listAdapter3 = new PartyListAdapter(fourList,this.getContext(),0);
        list.setAdapter(listAdapter);
        listOne.setAdapter(listAdapter1);
        listTwo.setAdapter(listAdapter2);
        listThree.setAdapter(listAdapter3);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menu_url = oneList.get(position).getMenu_Url();
                gotoWeb(""+oneList.get(position).getMenu_Name(),""+menu_url);
            }
        });
        listOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menu_url = twoList.get(position).getMenu_Url();
                gotoWeb(""+twoList.get(position).getMenu_Name(),""+menu_url);
            }
        });
        listTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menu_url = threeList.get(position).getMenu_Url();
                gotoWeb(""+threeList.get(position).getMenu_Name(),""+menu_url);
            }
        });
        listThree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String menu_url = fourList.get(position).getMenu_Url();
                gotoWeb(""+fourList.get(position).getMenu_Name(),""+menu_url);
            }
        });

        connect();
    }

    private void gotoWeb(String title, String url) {
        Intent intent=new Intent(this.getContext(), WebActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("Url",url);
        startActivity(intent);
    }


    //获得首页主要数据
    private void connect() {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID", MyApplication.user.getUser_ID()+"");
        params.put("Resol_Type",typeScreen+"");
        params.put("type",1+"");
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.FirstUrl, params, FirstJson.class,
                new OkhttpJsonUtil.TextCallBack<FirstJson>() {
                    @Override
                    public void getResult(FirstJson result) {
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                oneList.clear();
                                twoList.clear();
                                threeList.clear();
                                fourList.clear();
                                List<FirstJson.MenuListBean> menu_list = result.getMenu_List();
                                for(int i=0;i<menu_list.size();i++){
                                    FirstJson.MenuListBean menuListBean = menu_list.get(i);
                                    if(menuListBean.getMenu_Region().equals("总部专区")){
                                        twoList.add(menuListBean);
                                    }
                                    else if(menuListBean.getMenu_Region().equals("交流互动")){
                                        threeList.add(menuListBean);
                                    }
                                    else if(menuListBean.getMenu_Region().equals("运营工具")){
                                        fourList.add(menuListBean);
                                    }
                                    else{
                                        oneList.add(menuListBean);
                                    }

                                }
                                listAdapter.notifyDataSetChanged();
                                listAdapter1.notifyDataSetChanged();
                                listAdapter2.notifyDataSetChanged();
                                listAdapter3.notifyDataSetChanged();
                            }
                        }

                    }}
        );}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    //获得屏幕分辨率
    public void getScreenDensity_ByWindowManager(){
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d("p",""+width+"  "+height);
        if(width<=480){
            typeScreen = 1;
        }else if(width<=720){
            typeScreen=2;
        }else{
            typeScreen=3;
        }
    }
}
