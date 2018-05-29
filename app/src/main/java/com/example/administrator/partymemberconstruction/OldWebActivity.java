package com.example.administrator.partymemberconstruction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.CustomView.LoadingDialog;
import com.example.administrator.partymemberconstruction.utils.BrowserJsInject;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldWebActivity extends AppCompatActivity {

    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.headtitle)
    RelativeLayout headtitle;
    private String user_id;
    private String ui_nickName;

    private LoadingDialog loadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldweb);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();//加载动画
        //获取传过来的值
        title.setText("服务协议");
        link.setClickable(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingDialog.cancel();
                //set();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadingDialog.cancel();
            }
        });

        web.loadUrl("http://101.201.109.90:3335/H5Content/UserInfo1/UserText?type=2");
       // my_web.loadUrl("http://v.qq.com/iframe/player.html?vid=o0318tp1ddw&tiny=0&auto=0");
        //web.addJavascriptInterface(new JsObject(this), "android");


    }



}
