package com.example.administrator.partymemberconstruction.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.NoticeAllListAdapter;
import com.example.administrator.partymemberconstruction.Adapter.NoticeOneListAdapter;
import com.example.administrator.partymemberconstruction.Bean.NoticeItemJson;
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
public class NoticeChildFragment extends Fragment {


    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.noNotice)
    RelativeLayout noNotice;
    @BindView(R.id.list)
    ListView list;
    Unbinder unbinder;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.delet)
    TextView delet;
    @BindView(R.id.action)
    RelativeLayout action;
    private List<NoticeItemJson.NoticeInfoBean> noticeInfo;
    private NoticeAllListAdapter noticeOneListAdapter;
    private boolean isFirst=true;
    private List<Integer> delteAll;

    public NoticeChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_child_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        Log.e("w","oncreateview");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("w","onViewCreated");
        noNotice.setVisibility(View.VISIBLE);
        action.setVisibility(View.GONE);
        noticeInfo = new ArrayList<>();
        noticeOneListAdapter = new NoticeAllListAdapter(getContext(), noticeInfo, 2);
        if(isFirst){
            try {
                getListDate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        isFirst=false;
        delet.setClickable(true);
        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delteAll.size()>0)
                changeAllNotice();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //changeNotice(position);
                String n_link = noticeInfo.get(position).getN_Link();
                Intent intent=new Intent(NoticeChildFragment.this.getContext(), WebActivity.class);
                intent.putExtra("Url",n_link);
                intent.putExtra("title","");
                startActivity(intent);
            }
        });
    }

    private void changeNotice(int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("ids", noticeInfo.get(position).getEntityId()+"");
        OkhttpJsonUtil.getInstance().postByEnqueue(getActivity(), Url.ChangeNotice, params, NoticeItemJson.class,
                new OkhttpJsonUtil.TextCallBack<NoticeItemJson>() {
                    @Override
                    public void getResult(NoticeItemJson result) {
                        if(result!=null){
                            if(result.getCode().equals("成功")){
                               // MyApplication.showToast("删除成功",0);

                            }else{
                                MyApplication.showToast("查看失败",0);
                            }

                        }else{
                            MyApplication.showToast("连接服务器失败",0);
                        }
                    }
                });
    }

    //更改所有通知状态
    private void changeAllNotice() {
        StringBuffer param=new StringBuffer("");
        for(int i=0;i<delteAll.size();i++){
            if (i==delteAll.size()-1){
                param.append(delteAll.get(i));
            }else{
                param.append(delteAll.get(i)+",");
            }
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("ids", param+"");
        OkhttpJsonUtil.getInstance().postByEnqueue(getActivity(), Url.ChangeNotice, params, NoticeItemJson.class,
                new OkhttpJsonUtil.TextCallBack<NoticeItemJson>() {
                    @Override
                    public void getResult(NoticeItemJson result) {
                                   if(result!=null){
                                   if(result.getCode().equals("成功")){
                                       MyApplication.showToast("删除成功",0);
                                       noNotice.setVisibility(View.VISIBLE);
                                       action.setVisibility(View.GONE);
                                   }else{
                                       MyApplication.showToast("删除失败",0);
                                   }

                                   }else{
                                       MyApplication.showToast("连接服务器失败",0);
                                   }
                    }
        });
    }
    private void getListDate() throws Exception {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + MyApplication.user.getUser_ID());
        OkhttpJsonUtil.getInstance().postByEnqueue(getActivity(), Url.GetAllNoticeUrl, params, NoticeItemJson.class,
                new OkhttpJsonUtil.TextCallBack<NoticeItemJson>() {
                    @Override
                    public void getResult(NoticeItemJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                List<NoticeItemJson.NoticeInfoBean> trueList=new ArrayList<>();
                                for(NoticeItemJson.NoticeInfoBean a:result.getNoticeInfo()){
                                    if(a.getN_IsNew()==1){
                                        trueList.add(a);
                                    }
                                }
                                if(trueList.size()>0) {
                                    noNotice.setVisibility(View.GONE);
                                    action.setVisibility(View.VISIBLE);
                                    noticeInfo.clear();
                                    noticeInfo.addAll(trueList);
                                    noticeOneListAdapter.notifyDataSetChanged();
                                    num.setText("全部（" + trueList.size() + "）");
                                    // MyApplication.showToast("宽度" + list.getHeight(), 0);
                                    // clearList(noticeInfo);
                                    noticeOneListAdapter = new NoticeAllListAdapter(getContext(), noticeInfo, 2);
                                    list.setAdapter(noticeOneListAdapter);
                                    delteAll = new ArrayList<>();
                                    for (NoticeItemJson.NoticeInfoBean a : noticeInfo) {
                                        if (a.getN_IsNew() == 1) {
                                            delteAll.add(a.getEntityId());
                                        }
                                    }
                                }else{
                                    noNotice.setVisibility(View.VISIBLE);
                                    action.setVisibility(View.GONE);
                                }
                            } else {
                                noNotice.setVisibility(View.VISIBLE);
                                action.setVisibility(View.GONE);
                            }
                        } else {
                            noNotice.setVisibility(View.VISIBLE);
                            action.setVisibility(View.GONE);
                        }

                    }
                });
    }


    private void clearList(List<NoticeItemJson.NoticeInfoBean> noticeInfo) {
        List<NoticeItemJson.NoticeInfoBean> list=new ArrayList<>();
        for (int i=0;i<noticeInfo.size();i++) {
            NoticeItemJson.NoticeInfoBean bean = noticeInfo.get(i);
            if(bean.getN_Type()==0||bean.getN_Type()==2){
                      list.add(bean);
                  }
        }
        noticeInfo.clear();
        noticeInfo.addAll(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        //getListDate();
        Log.e("w","onStart");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("w","setUserVisibleHint");
        if (getUserVisibleHint()) {
            if(isFirst){

            }else{
                try {
                    getListDate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //可见时进行内容加载或逻辑操作等
        } else {
            //不可见
        }
    }
}
