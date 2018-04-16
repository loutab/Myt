package com.example.administrator.partymemberconstruction.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 */

public class MineListAdapter extends BaseAdapter {

    private Context context;
    private List<String[]>list;

    public MineListAdapter(Context context, List<String[]> list) {
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
        view = LayoutInflater.from(context).inflate(R.layout.mine_list_layout, viewGroup, false);
        ImageView head = view.findViewById(R.id.head);
        TextView txt = view.findViewById(R.id.txt);
        head.setImageResource(Integer.valueOf(list.get(i)[1]));
        txt.setText(list.get(i)[0]);
        return view;
    }
}
