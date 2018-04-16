package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.FirstJson;
import com.example.administrator.partymemberconstruction.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class PartyListAdapter extends BaseAdapter {
    List<FirstJson.MenuListBean>list;
    private Context context;
    int type;

    public PartyListAdapter(List<FirstJson.MenuListBean> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type=type;
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
        if(type==1)
        view= LayoutInflater.from(context).inflate(R.layout.party_list_layout, viewGroup, false);
        else
            view= LayoutInflater.from(context).inflate(R.layout.party_list_one_layout, viewGroup, false);
        TextView txt=view.findViewById(R.id.txt);
        ImageView img=view.findViewById(R.id.img);
        txt.setText(list.get(i).getMenu_Name());
        Picasso.with(context).load(list.get(i).getMenu_Logo_Url()).into(img);
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
