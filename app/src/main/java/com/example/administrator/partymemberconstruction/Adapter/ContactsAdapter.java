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
import com.example.administrator.partymemberconstruction.Bean.ContactsSort;
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
    List<ContactsSort> list;

    public ContactsAdapter() {
        super();
    }

    Context context;

    public ContactsAdapter(List<ContactsSort> list, Context context) {
        this.list = list;
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
        if(list.get(position).isShow()){
            viewHolder.letter.setVisibility(View.VISIBLE);
            viewHolder.letter.setText(ChineseToEnglish.getPinYinHeadChar(list.get(position).getBean().getName()).charAt(0)+"");
        }

        viewHolder.name.setText(list.get(position).getBean().getName()==null?"":list.get(position).getBean().getName());
        String ss = TextUtils.isEmpty(list.get(position).getBean().getHeadImg()) ? "www" : list.get(position).getBean().getHeadImg();
        Picasso.with(context).load(ss).error(R.mipmap.search_p).into(viewHolder.head);

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
