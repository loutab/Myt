package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.MessageListJson;
import com.example.administrator.partymemberconstruction.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class MessageListAdapter extends BaseAdapter {

    private Context context;
    private List<MessageListJson.MessageListBean>list;

    public MessageListAdapter(Context context, List<MessageListJson.MessageListBean> list) {
        this.context = context;
        this.list = list;
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
        MessageListJson.MessageListBean messageListBean = list.get(i);
        ViewHolder viewHolder;
        if(view == null){
            v = LayoutInflater.from(context).inflate(R.layout.message_list_layout, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.head = v.findViewById(R.id.head);
            viewHolder.name = v.findViewById(R.id.name);
            viewHolder.content = v.findViewById(R.id.content);
            viewHolder.time = v.findViewById(R.id.time);
            v.setTag(viewHolder);//讲ViewHolder存储在View中
        }else{
            v = view;
            viewHolder = (ViewHolder) view.getTag();//重获取viewHolder
        }
        viewHolder.name.setText(messageListBean.getM_Form_User_Id()+"用户");
        viewHolder.content.setText(messageListBean.getM_Text());
        viewHolder.time.setText(messageListBean.getM_Date());
        return v;
    }
    class ViewHolder{
        ImageView head;
        TextView name;
        TextView content;
        TextView time;
    }
}
