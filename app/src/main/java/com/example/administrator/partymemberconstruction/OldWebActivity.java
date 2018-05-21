package com.example.administrator.partymemberconstruction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.CustomView.ActionSheetDialog;
import com.example.administrator.partymemberconstruction.CustomView.LoadingDialog;
import com.example.administrator.partymemberconstruction.utils.BrowserJsInject;
import com.example.administrator.partymemberconstruction.utils.GetPathFromUri4kitkat;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.FileProvider.getUriForFile;

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
//        Intent intent = getIntent();
//        String url = intent.getStringExtra("Url");
//        String titleTxt = intent.getStringExtra("title");
//        user_id = MyApplication.user.getUser_ID() + "";
//        ui_nickName = MyApplication.user.getUi_NickName();
        title.setText("服务协议");
        link.setClickable(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initWebView();

//        web.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loadingDialog.cancel();
//                //set();
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                loadingDialog.cancel();
//            }
//        });

        web.loadUrl("file:///android_asset/fuwu.html");
       // my_web.loadUrl("http://v.qq.com/iframe/player.html?vid=o0318tp1ddw&tiny=0&auto=0");
        //web.addJavascriptInterface(new JsObject(this), "android");


    }
    xWebChromeClient xwebchromeclient;
    private void initWebView() {
        WebSettings ws = web.getSettings();
/**
 * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
 * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
 * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
 * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
 * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
 * setSupportZoom 设置是否支持变焦
 * */
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setPluginState(WebSettings.PluginState.ON);
// settings.setPluginsEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setLoadWithOverviewMode(true);
        ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
        ws.setSavePassword(true);
        ws.setSaveFormData(true);// 保存表单数据
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        web.setSaveEnabled(false);
        ws.setSaveFormData(false);
// 下面的一句话是必须的，必须要打开javaScript不然所做一切都是徒劳的
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        xwebchromeclient = new xWebChromeClient();
//setWebChromeClient主要处理解析，渲染网页等浏览器做的事情
//这个方法必须有，就算类中没有函数也可以，不然视频播放不了
        web.setWebChromeClient(xwebchromeclient);
//WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
        web.setWebViewClient(new xWebViewClientent());
    }
    /**
     * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
     * @author
     */
    public class xWebChromeClient extends WebChromeClient {
    }


    public class xWebViewClientent extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {



            view.loadUrl(BrowserJsInject.fullScreenByJs(url));
//            System.out.println("url1:"+BrowserJsInject.fullScreenByJs(url));
//            System.out.println("url2"+url);

//view.loadData("", "text/html", "UTF-8");
            // my_web.loadUrl("http://v.qq.com/iframe/player.html?vid=o0318tp1ddw&tiny=0&auto=0");
//view.loadUrl("javascript:alert('123')");
            //view.loadUrl(BrowserJsInject.fullScreenByJs(url));

/*//点击链接时：
view.setWebViewClient(new WebViewClient(){
@Override
public boolean shouldOverrideUrlLoading(WebView view, String url) {

view.loadUrl(url); //在当前的webview中跳转到新的url
System.out.println("链接--》"+url);
return true;
}
});
启动手机浏览器来打开新的url

webView.setWebViewClient(new WebViewClient(){
 @Override
 public boolean shouldOverrideUrlLoading(WebView view, String url) {

 Intent i = new Intent(Intent.ACTION_VIEW);
 i.setData(Uri.parse(url));
 startActivity(i);
 return true;
 }
 });
*/

/*
view.addJavascriptInterface(new Object(){

@JavascriptInterface
public void playing(){
System.out.println("返回结果");
setFullScreen();
}

}, "local_obj");
view.loadUrl(BrowserJsInject.fullScreenByJs(url));
System.out.println("url1:"+BrowserJsInject.fullScreenByJs(url));
System.out.println("url2"+url);*/
//myJavaScriptInterface = new JavaScriptInterface(this);



        }
        /**
         * 设置全屏
         */
        private void setFullScreen() {
            Log.i("视频全屏-->", "竖屏切换到横屏");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
// 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }


    }



}
