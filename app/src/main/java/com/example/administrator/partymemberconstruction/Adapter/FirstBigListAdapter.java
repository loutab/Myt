package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class FirstBigListAdapter extends BaseAdapter {
    List<String[]>list;
    private Context context;

    public FirstBigListAdapter(List<String[]> list, Context context) {
        this.list = list;
        this.context = context;
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
        view= LayoutInflater.from(context).inflate(R.layout.first_big_list_layout, viewGroup, false);
        TextView txt=view.findViewById(R.id.txt);
        ImageView img=view.findViewById(R.id.img);
        txt.setText(list.get(i)[1]);
        Picasso.with(context).load(list.get(i)[0]).into(img);
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
