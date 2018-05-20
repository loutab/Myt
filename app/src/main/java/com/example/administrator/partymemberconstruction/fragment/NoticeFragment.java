package com.example.administrator.partymemberconstruction.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.partymemberconstruction.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class NoticeFragment extends Fragment {


    @BindView(R.id.notice)
    RadioButton notice;
    @BindView(R.id.message)
    RadioButton message;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.frame)
    FrameLayout frame;
    Unbinder unbinder;
    private FragmentTransaction transaction;
    private NoticeNoticeFragment noticeNoticeFragment;
    private NoticeMessageFragment noticeMessageFragment;

    public NoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transaction = getChildFragmentManager().beginTransaction();
        inttFragment();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = getChildFragmentManager().beginTransaction();
                switch (checkedId){
                    case R.id.notice:
                        if(noticeMessageFragment!=null)
                            transaction.hide(noticeMessageFragment);
                        transaction.show(noticeNoticeFragment).commit();
                        break;
                    case R.id.message:
                        if(noticeMessageFragment==null){
                            noticeMessageFragment = new NoticeMessageFragment();
                            transaction.add(R.id.frame,noticeMessageFragment);
                        }
                         transaction.hide(noticeNoticeFragment);

                         transaction.show(noticeMessageFragment).commit();


                        break;
                }
            }
        });
    }

    private void inttFragment() {
        noticeNoticeFragment = new NoticeNoticeFragment();
        //noticeMessageFragment = new NoticeMessageFragment();
        transaction.add(R.id.frame, noticeNoticeFragment);
        transaction.show(noticeNoticeFragment).commit();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
