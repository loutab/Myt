package com.example.administrator.partymemberconstruction.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.NoticeAllListAdapter;
import com.example.administrator.partymemberconstruction.Adapter.NoticeOneListAdapter;
import com.example.administrator.partymemberconstruction.Bean.NoticeItemJson;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
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
        list.setVisibility(View.GONE);
        noticeInfo = new ArrayList<>();
        noticeOneListAdapter = new NoticeAllListAdapter(getContext(), noticeInfo, 2);
        if(isFirst){
            getListDate();
        }
        isFirst=false;
    }

    private void getListDate() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "" + MyApplication.user.getUser_ID());
        OkhttpJsonUtil.getInstance().postByEnqueue(getActivity(), Url.GetAllNoticeUrl, params, NoticeItemJson.class,
                new OkhttpJsonUtil.TextCallBack<NoticeItemJson>() {
                    @Override
                    public void getResult(NoticeItemJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                noNotice.setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                noticeInfo.clear();
                                noticeInfo.addAll(result.getNoticeInfo());
                                noticeOneListAdapter.notifyDataSetChanged();
                                num.setText("全部（"+noticeInfo.size()+"）");
                               // MyApplication.showToast("宽度" + list.getHeight(), 0);
                               // clearList(noticeInfo);
                                noticeOneListAdapter = new NoticeAllListAdapter(getContext(), noticeInfo,2);
                                list.setAdapter(noticeOneListAdapter);
                            } else {
                                noNotice.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
                            }
                        } else {
                            noNotice.setVisibility(View.VISIBLE);
                            list.setVisibility(View.GONE);
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
                getListDate();
            }
            //可见时进行内容加载或逻辑操作等
        } else {
            //不可见
        }
    }
}
