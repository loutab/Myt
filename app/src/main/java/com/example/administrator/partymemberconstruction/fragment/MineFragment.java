package com.example.administrator.partymemberconstruction.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.MineListAdapter;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class MineFragment extends Fragment {


    @BindView(R.id.headImg)
    CircleImageView headImg;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.introduction)
    TextView introduction;
    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.right)
    TextView right;
    @BindView(R.id.contacts)
    LinearLayout contacts;
    @BindView(R.id.group)
    LinearLayout group;
    @BindView(R.id.personalData)
    LinearLayout personalData;
    @BindView(R.id.integral)
    LinearLayout integral;
    @BindView(R.id.list)
    ListView list;
    Unbinder unbinder;
    private List<String[]> lists;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        MineListAdapter mineListAdapter=new MineListAdapter(getContext(),lists);
        list.setAdapter(mineListAdapter);
    }

    private void initDate() {
        lists = new ArrayList<>();
        lists.add(new String[]{"我发的消息",R.mipmap.mymessage+""});
        lists.add(new String[]{"我的收藏",R.mipmap.mycollect+""});
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
