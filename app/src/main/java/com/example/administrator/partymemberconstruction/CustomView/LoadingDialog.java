package com.example.administrator.partymemberconstruction.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.administrator.partymemberconstruction.R;


/**
 * Created by lwx on 2016/12/12.
 */

public class LoadingDialog extends Dialog {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        GifView gif = (GifView) findViewById(R.id.gif);
        gif.setMovieResource(R.raw.loading_ic);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public LoadingDialog(Context context) {
        super(context, R.style.MyDialog);
    }
}
