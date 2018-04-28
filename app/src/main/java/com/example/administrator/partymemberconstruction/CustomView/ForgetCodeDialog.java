package com.example.administrator.partymemberconstruction.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lwx on 2016/12/12.
 */

public class ForgetCodeDialog extends Dialog {
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.codeNum)
    EditText codeNum;
    @BindView(R.id.code)
    ImageView code;
    @BindView(R.id.sure)
    TextView sure;
    Context context;
    @BindView(R.id.two)
    LinearLayout two;

    public EditText getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(EditText codeNum) {
        this.codeNum = codeNum;
    }

    public ImageView getCode() {
        return code;
    }

    public void setCode(ImageView code) {
        this.code = code;
    }

    public TextView getSure() {
        return sure;
    }

    public void setSure(TextView sure) {
        this.sure = sure;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_code_layout);
        ButterKnife.bind(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.95); // 宽度设置为屏幕的0.8
        lp.y = 180;
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        dialogWindow.setAttributes(lp);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected ForgetCodeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public ForgetCodeDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

}
