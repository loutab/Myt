package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.ContactsJson;
import com.example.administrator.partymemberconstruction.ContactsActivity;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.utils.ChineseToEnglish;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class ContactsAdapter extends BaseAdapter {
    List<ContactsJson.UserInfoListBean> list;
    List<ContactsActivity.targe> targes;


    public ContactsAdapter() {
        super();
    }

    Context context;

    public ContactsAdapter(List<ContactsJson.UserInfoListBean> list, List<ContactsActivity.targe> targes, Context context) {
        this.list = list;
        this.targes = targes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int j=0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
        view = LayoutInflater.from(context).inflate(R.layout.contracts_adapter_layout, parent, false);
        viewHolder =new ViewHolder(view);
        view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.e("a",position+"位置");
        if(position==0||targes.get(j).getPosition()==position){
            if(targes.get(j).getPosition()==position){
                if(targes.size()-1>j)
                j++;
            }
            viewHolder.letter.setVisibility(View.VISIBLE);
            viewHolder.letter.setText(ChineseToEnglish.getPinYinHeadChar(list.get(position).getName()).charAt(0)+"");
        }

        viewHolder.name.setText(list.get(position).getName()==null?"":list.get(position).getName());
        String ss = TextUtils.isEmpty(list.get(position).getHeadImg()) ? "www" : list.get(position).getHeadImg();
        Picasso.with(context).load(ss).into(viewHolder.head, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
               // viewHolder.head.setImageResource(R.mipmap.head_img);
                //MyApplication.showToast("头像加载失败",0);
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.letter)
        TextView letter;
        @BindView(R.id.head)
        ImageView head;
        @BindView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
