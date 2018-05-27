package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.PartMJson;
import com.example.administrator.partymemberconstruction.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class ImgGrideAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ImgGrideAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public ImgGrideAdapter() {
        super();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.img_gride_layout, parent, false);
        ImageView viewById = convertView.findViewById(R.id.img);
        String s = list.get(position);
        String replace = s.replace(" ", "");
        if(replace=="")
        replace="www";
        Picasso.with(context).load(replace).into(viewById);
        return convertView;
    }


}
