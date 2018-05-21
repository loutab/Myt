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
public class NoticeChildFourFragment extends Fragment {


    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.noNotice)
    RelativeLayout noNotice;
    @BindView(R.id.list)
    ListView list;
    Unbinder unbinder;
    private List<NoticeItemJson.NoticeInfoBean> noticeInfo;
    private NoticeOneListAdapter noticeOneListAdapter;
    public NoticeChildFourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_child, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.setVisibility(View.GONE);
        noNotice.setVisibility(View.VISIBLE);
        noticeInfo = new ArrayList<>();
        noticeOneListAdapter = new NoticeOneListAdapter(getContext(), noticeInfo,2);
        isFirst=false;

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //changeNotice(position);
                String n_link = noticeInfo.get(position).getN_Link();
                Intent intent=new Intent(NoticeChildFourFragment.this.getContext(), WebActivity.class);
                intent.putExtra("Url",n_link);
                intent.putExtra("title","");
                startActivity(intent);
            }
        });
    }
    private void getDate() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId",""+ MyApplication.user.getUser_ID());
        params.put("type",""+3);
        OkhttpJsonUtil.getInstance().postByEnqueue(getActivity(), Url.GetNoticeUrl, params, NoticeItemJson.class,
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
                                noticeOneListAdapter = new NoticeOneListAdapter(getContext(), noticeInfo,2);
                                list.setAdapter(noticeOneListAdapter);
                                //noticeOneListAdapter.notifyDataSetChanged();
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public void onStart() {
        super.onStart();
        getDate();
    }
    private boolean isFirst=true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("w","setUserVisibleHint1");
        if (getUserVisibleHint()) {
            //可见时进行内容加载或逻辑操作等
            if(isFirst){

            }else{
                getDate();
            }
        } else {
            //不可见
        }
    }
}
