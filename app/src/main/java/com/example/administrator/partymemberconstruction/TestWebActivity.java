package com.example.administrator.partymemberconstruction;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.FileProvider.getUriForFile;

public class TestWebActivity extends AppCompatActivity {

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
    private static final int REQUEST_LOAD_IMAGE_FROM_GALLERY = 1;
    private ValueCallback<Uri> mFilePathCallback;
    private ValueCallback<Uri[]> mFilePathCallbacks;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        WebSettings settings = web.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
       // settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        //设置可以访问文件
        settings.setAllowFileAccess(true);
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
        });
        web.setWebChromeClient(new OpenFileWebChromeClient(this));
//        web.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//                mFilePathCallbacks = filePathCallback;
//                openGallery();
//                return true;  // 一定要return true 防止下次回到 WebView页面重新调用，抛异常 duplicate result
//            }
//
//            // 5.0以下的文件上传监听方法
//            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//                mFilePathCallback = uploadMsg;
//                openGallery();
//            }
//        });
        web.loadUrl("file:///android_asset/test.html");
        web.addJavascriptInterface(new JsInteration(), "android");
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_LOAD_IMAGE_FROM_GALLERY);
    }

    public class JsInteration {
        @JavascriptInterface
        public String Id() {
            return user_id;
        }

        @JavascriptInterface
        public String Name() {
            //MyApplication.showToast(ui_nickName,0);
            return ui_nickName;
        }
        @JavascriptInterface
        public void ShowOrHide() {
            if(headtitle.getVisibility()==View.VISIBLE){
                headtitle.setVisibility(View.GONE);
            }else if(headtitle.getVisibility()==View.GONE){
                headtitle.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
int a=1;
        if (requestCode == REQUEST_LOAD_IMAGE_FROM_GALLERY) {
            if (mFilePathCallback == null && mFilePathCallbacks == null) {
                return;
            }
            Uri tempUri = null;
            if (data != null) {
                String url =getPath(this, data.getData());
                File temp = new File(url);
                tempUri = Uri.fromFile(temp);
            }

            if (mFilePathCallback != null) { // 5.0以下处理方式
                if (data != null) {
                    mFilePathCallback.onReceiveValue(tempUri);
                } else {
                    mFilePathCallback.onReceiveValue(imageUri);
                }
                mFilePathCallback = null;
            } else if (mFilePathCallbacks != null) { // 5.0版本以上
                Uri[] uris = null;
                if (data == null) {
                    uris = new Uri[] {imageUri};
                } else {
                    // 多选图片[图片一定要压缩，转化为base网页加载很慢]
                    String dataString = data.getDataString();
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        int size = clipData.getItemCount();
                        uris = new Uri[size];
                        for (int i = 0; i < size; i++) {
                            // 将所选图片的 url保存到 uris数组中
                            uris[i] = clipData.getItemAt(i).getUri();
                        }
                    }
                    if (!TextUtils.isEmpty(dataString)) {
                        uris = new Uri[] {Uri.parse(dataString)};
                    }
                }

                if (uris == null) {
                    mFilePathCallbacks.onReceiveValue(null);
                    mFilePathCallbacks = null;
                } else {
                    mFilePathCallbacks.onReceiveValue(uris);
                    mFilePathCallbacks = null;
                }
            }
        }
    }
    public static String getPath(Context mainActivity, Uri data) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(mainActivity, data, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public class OpenFileWebChromeClient extends WebChromeClient {
        public static final int REQUEST_FILE_PICKER = 1;
//        public ValueCallback<Uri> mFilePathCallback;
//        public ValueCallback<Uri[]> mFilePathCallbacks;
        Activity mContext;
        public OpenFileWebChromeClient(Activity mContext){
            super();
            this.mContext = mContext;
        }
        // Android < 3.0 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }
        // 3.0 + 调用这个方法
        public void openFileChooser(ValueCallback filePathCallback,
                                    String acceptType) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }
        //  / js上传文件的<input type="file" name="fileField" id="fileField" />事件捕获
        // Android > 4.1.1 调用这个方法
        public void openFileChooser(ValueCallback<Uri> filePathCallback,
                                    String acceptType, String capture) {
            mFilePathCallback = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            mFilePathCallbacks = filePathCallback;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            mContext.startActivityForResult(Intent.createChooser(intent, "File Chooser"),
                    REQUEST_FILE_PICKER);
            return true;
        }
    }
    /**
     * 调用相机
     */
    private File picturefile;

}
