package com.example.administrator.partymemberconstruction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.utils.APIWebviewTBS;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldWebActivity extends AppCompatActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.headtitle)
    RelativeLayout headtitle;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldweb);
        ButterKnife.bind(this);
        APIWebviewTBS.getAPIWebview().initTBSActivity(this);   //api借口注册二次封装
        loadWebvieUrl("http://101.201.109.90:3335/H5Content/ThepartyBBS/Index?type=28");
    }
    private void loadWebvieUrl(String url) {
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl(url);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {

            }
        });
        //进度条
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
