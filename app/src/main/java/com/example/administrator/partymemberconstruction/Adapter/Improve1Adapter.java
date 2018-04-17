package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.Bean.PartJson;
import com.example.administrator.partymemberconstruction.Bean.PartMJson;
import com.example.administrator.partymemberconstruction.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class Improve1Adapter extends BaseAdapter {
    private Context context;
    private List<PartMJson.DepartListBean> list;

    public Improve1Adapter(Context context, List<PartMJson.DepartListBean> list) {
        this.context = context;
        this.list = list;
    }

    public Improve1Adapter() {
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
        convertView= LayoutInflater.from(context).inflate(R.layout.improve_list_layout, parent, false);
        TextView txt = convertView.findViewById(R.id.txt);
        txt.setText(list.get(position).getDepartName());
        return convertView;
    }


}
