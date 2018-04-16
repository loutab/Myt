package com.example.administrator.partymemberconstruction.fragment;


import android.content.Context;
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

import com.example.administrator.partymemberconstruction.Adapter.MessageListAdapter;
import com.example.administrator.partymemberconstruction.Bean.ChangeMessageJson;
import com.example.administrator.partymemberconstruction.Bean.MessageListJson;
import com.example.administrator.partymemberconstruction.Bean.UploadJson;
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
public class NoticeMessageFragment extends Fragment {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.noMessage)
    RelativeLayout noMessage;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.email)
    ImageView email;
    Unbinder unbinder;
    private List<MessageListJson.MessageListBean> messageListBeans;
    private MessageListAdapter messageListAdapter;

    public NoticeMessageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageListBeans = new ArrayList<>();
        messageListAdapter = new MessageListAdapter(this.getContext(), messageListBeans);
        listview.setAdapter(messageListAdapter);
        //获得消息列表数据
       // getMessageList();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //更改消息状态
                changeMessageStatue(position);
            }
        });
    }

    private void changeMessageStatue(int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("EntityId",""+messageListBeans.get(position).getEntityId());
        params.put("m_IsRead",""+ 1);
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.ChangeMessageUrl, params, ChangeMessageJson.class,
                new OkhttpJsonUtil.TextCallBack<ChangeMessageJson>() {
                    @Override
                    public void getResult(ChangeMessageJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
MyApplication.showToast("消息状态更改"+result.getCode(),0);
                        }else{

                        }

                    }
                });
    }

    private void getMessageList() {
        HashMap<String, String> params = new HashMap<>();
        //params.put("userId",""+ MyApplication.user.getUser_ID());
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.GetMessageUrl, params, MessageListJson.class,
                new OkhttpJsonUtil.TextCallBack<MessageListJson>() {
                    @Override
                    public void getResult(MessageListJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            Log.d("p",result.getMessageList().get(1).getM_Date());
                            if(result.getMessageList().size()>0){
                                messageListBeans.clear();
                                messageListBeans.addAll(result.getMessageList());
                            messageListAdapter.notifyDataSetChanged();
                            listview.setVisibility(View.VISIBLE);
                            noMessage.setVisibility(View.GONE);
                            }else{
                                //展示消息为空页面
                                listview.setVisibility(View.GONE);
                                noMessage.setVisibility(View.VISIBLE);
                            }
                        }else{
                            //展示消息为空页面
                            listview.setVisibility(View.GONE);
                            noMessage.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMessageList();
    }
}
