package com.example.administrator.partymemberconstruction.Adapter;

/**
 * Created by lwx on 2017/1/11.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class FragmentAdapter extends FragmentPagerAdapter {

    private int page_count;
    private Context context;
    List<Fragment> list;
    private String[] tabTitles;


    public FragmentAdapter(FragmentManager fm, Context context, List<Fragment> list, String[] tabTitles, int page_count) {
        super(fm);
        this.context = context;
        this.list = list;
        this.tabTitles = tabTitles;
        this.page_count = page_count;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return page_count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}

