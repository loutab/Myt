package com.example.administrator.partymemberconstruction.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lwx on 2016/12/12.
 */

public class ChangeHeadImgDialog extends Dialog {

    Context context;
    @BindView(R.id.take)
    TextView take;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.cancle)
    TextView cancle;

    public TextView getTake() {
        return take;
    }

    public void setTake(TextView take) {
        this.take = take;
    }

    public TextView getPhone() {
        return phone;
    }

    public void setPhone(TextView phone) {
        this.phone = phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(false);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected ChangeHeadImgDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ChangeHeadImgDialog(Context context) {
        super(context, R.style.changeHead);
        this.context = context;
        setContentView(R.layout.change_head_img_layout);
        ButterKnife.bind(this);
    }
}
