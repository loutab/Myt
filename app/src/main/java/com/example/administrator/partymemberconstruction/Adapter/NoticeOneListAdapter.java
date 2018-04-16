package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.NoticeItemJson;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.WebActivity;
import com.example.administrator.partymemberconstruction.utils.ComenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/21.
 */

public class NoticeOneListAdapter extends BaseAdapter {

    private Context context;
    private List<NoticeItemJson.NoticeInfoBean> list;
    private int type;
    private String n_text;

    public NoticeOneListAdapter(Context context, List<NoticeItemJson.NoticeInfoBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        NoticeItemJson.NoticeInfoBean noticeInfoBean = list.get(i);
        ViewHolder2 viewHolder2 = null;
        ViewHolder0 viewHolder0 = null;
        ViewHolder1 viewHolder1 = null;
        ViewHolder3 viewHolder3 = null;
        if (view == null) {
            switch (noticeInfoBean.getN_Type()) {
                case 2://请求
                    v = LayoutInflater.from(context).inflate(R.layout.notice_one_list_layout, viewGroup, false);
                    viewHolder2 = new ViewHolder2(v);
                    v.setTag(viewHolder2);//讲ViewHolder存储在View中
                    break;

                case 1://回复
                    v = LayoutInflater.from(context).inflate(R.layout.notice_reply_list_layout, viewGroup, false);
                    viewHolder1 = new ViewHolder1(v);
                    v.setTag(viewHolder1);
                    break;
                case 0://@我
                    v = LayoutInflater.from(context).inflate(R.layout.list_ask_mine_layout, viewGroup, false);
                    viewHolder0 = new ViewHolder0(v);
                    v.setTag(viewHolder0);
                    break;
                case 3://系统
                    v = LayoutInflater.from(context).inflate(R.layout.notice_system_list_layout, viewGroup, false);
                    viewHolder3 = new ViewHolder3(v);
                    v.setTag(viewHolder3);
                    break;
                default:
                    v = null;
            }
        } else {
            v = view;
            switch (noticeInfoBean.getN_Type()) {
                case 2:
                    viewHolder2 = (ViewHolder2) view.getTag();//重获取viewHolder;
                    break;
                case 0:
                    viewHolder0 = (ViewHolder0) view.getTag();
                    break;
                case 1:
                    viewHolder1 = (ViewHolder1) view.getTag();
                    break;
                case 3:
                    viewHolder3 = (ViewHolder3) view.getTag();
                    break;
                default:
                    viewHolder0 = null;
                    break;
            }
        }
        switch (noticeInfoBean.getN_Type()) {
            case 0:
                viewHolder0.title.setText(noticeInfoBean.getN_Title());
                viewHolder0.time.setText(ComenUtils.ChangeTime(noticeInfoBean.getN_SendTime()));
                viewHolder0.content.setText(noticeInfoBean.getN_Text());
                break;
            case 1:
                viewHolder1.name.setText(noticeInfoBean.getN_Title());
                viewHolder1.time.setText(ComenUtils.ChangeTime(noticeInfoBean.getN_SendTime()));
                viewHolder1.content.setText(noticeInfoBean.getN_Text());
                break;
            case 2:
                viewHolder2.name.setText(noticeInfoBean.getUserName());
                viewHolder2.time.setText(ComenUtils.ChangeTime(noticeInfoBean.getN_SendTime()));
                viewHolder2.content.setText(noticeInfoBean.getN_Text());
                if (noticeInfoBean.getN_IsNew() == 1) {
                    viewHolder2.action.setVisibility(View.GONE);
                    viewHolder2.noaction.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                //链接
                if(noticeInfoBean.getN_ArticleId()==1){
                    n_text = noticeInfoBean.getN_Text();
                    viewHolder3.head.setImageDrawable(context.getResources().getDrawable(R.mipmap.systemhead2));
                    viewHolder3.link.setVisibility(View.VISIBLE);
                    viewHolder3.link.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(context, WebActivity.class);
                            intent.putExtra("Url",n_text);
                            intent.putExtra("title","链接");
                            context.startActivity(intent);
                        }
                    });
                }
                //正常
                else{

                }
                viewHolder3.name.setText(noticeInfoBean.getN_Title());
                viewHolder3.content.setText(noticeInfoBean.getN_Text());
                viewHolder3.time.setText(ComenUtils.ChangeTime(noticeInfoBean.getN_SendTime()));
                break;
        }
        return v;
    }


    static class ViewHolder2 {
        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.step)
        TextView step;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.forbid)
        TextView forbid;
        @BindView(R.id.agree)
        ImageView agree;
        @BindView(R.id.ignore)
        ImageView ignore;
        @BindView(R.id.noaction)
        LinearLayout noaction;
        @BindView(R.id.actionstatus)
        ImageView actionstatus;
        @BindView(R.id.actioncontent)
        TextView actioncontent;
        @BindView(R.id.action)
        LinearLayout action;

        ViewHolder2(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder0 {
        @BindView(R.id.askMe)
        ImageView askMe;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;

        ViewHolder0(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 {
        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.step)
        TextView step;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.agree)
        TextView agree;
        @BindView(R.id.ignore)
        TextView ignore;
        @BindView(R.id.noaction)
        LinearLayout noaction;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder3 {
        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.link)
        LinearLayout link;

        ViewHolder3(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
