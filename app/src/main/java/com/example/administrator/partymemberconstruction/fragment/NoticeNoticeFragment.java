package com.example.administrator.partymemberconstruction.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.partymemberconstruction.Adapter.FragmentAdapter;
import com.example.administrator.partymemberconstruction.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class NoticeNoticeFragment extends Fragment {


    @BindView(R.id.tab)
    android.support.design.widget.TabLayout tab;
    @BindView(R.id.notice)
    ViewPager notice;
    Unbinder unbinder;
    private String[] titles;
    private List<Fragment> fragments;

    public NoticeNoticeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_notice, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        notice.setAdapter(new FragmentAdapter(getChildFragmentManager(), getContext(), fragments, titles, titles.length));
        //tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(notice);
    }

    private void initDate() {
        titles = new String[]{"最新","@我","回复","请求","系统"};
        fragments = new ArrayList<>();
        fragments.add(new NoticeChildFragment());
        fragments.add(new NoticeChildOneFragment());
        fragments.add(new NoticeChildTwoFragment());
        fragments.add(new NoticeChildThreeFragment());
        fragments.add(new NoticeChildFourFragment());
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
