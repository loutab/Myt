package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class ImproveAdapter extends BaseAdapter {
    private Context context;
    private List<GroupJson.TissueTreeBean> list;

    public ImproveAdapter(Context context, List<GroupJson.TissueTreeBean> list) {
        this.context = context;
        this.list = list;
    }

    public ImproveAdapter() {
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
        View view;
        ViewHolder viewHolder;
//        if(convertView==null){
        view = LayoutInflater.from(context).inflate(R.layout.improve_list_layout, parent, false);
        viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
//        }else{
//            view=convertView;
//            viewHolder= (ViewHolder) view.getTag();
//        }
        TextView txt = viewHolder.txt;
        txt.setText(list.get(position).getOrganizationName());
if(position==0){
    txt.setTextColor(Color.parseColor("#585454"));
}
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.txt)
        TextView txt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
