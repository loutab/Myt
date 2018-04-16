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

import com.example.administrator.partymemberconstruction.CustomView.ActionSheetDialog;
import com.example.administrator.partymemberconstruction.utils.GetPathFromUri4kitkat;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.FileProvider.getUriForFile;

public class TestWeb2Activity extends AppCompatActivity {

    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.headtitle)
    RelativeLayout headtitle;
    private static final String TAG = TestWeb2Activity.class.getSimpleName();
    private static final int REQUEST_CODE_TAKE_PICETURE = 11;
    private static final int REQUEST_CODE_PICK_PHOTO = 12;
    private static final int REQUEST_CODE_PERMISSION = 13;
    public static ValueCallback<Uri> mFilePathCallback;
    public static ValueCallback<Uri[]> mFilePathCallbacks;
    private File picturefile;
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
        web.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {//5.0+
                Log.e(TAG, "--------调用onShowFileChooser1");
                showDialog();
                mFilePathCallbacks = filePathCallback;
                return true;
            }

            //openFileChooser 方法是隐藏方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {// android 系统版本>4.1.1
                Log.e(TAG, "--------调用openFileChooser2");
                showDialog();
                mFilePathCallback = uploadMsg;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {//android 系统版本<3.0
                showDialog();
                mFilePathCallback = uploadMsg;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {//android 系统版本3.0+
                showDialog();
                mFilePathCallback = uploadMsg;
            }

        });
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

String user_id=MyApplication.user.getUser_ID()+"";
    String ui_nickName=MyApplication.user.getUi_NickName();
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
    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(TestWeb2Activity.this).builder().addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                takeForPicture();
            }
        }).addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                takeForPhoto();
            }
        }).setCancelable(false).setCanceledOnTouchOutside(false);

        dialog.show();
        //设置点击“取消”按钮监听，目的取消mFilePathCallback回调，可以重复调起弹窗
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelFilePathCallback();
            }
        });
    }

    /**
     * 调用相册
     */
    private void takeForPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);

    }

    /**
     * 调用相机
     */
    private void takeForPicture() {
//       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//手机图片的公共目录
        File pFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyPictures");//图片位置
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        //拍照所存路径
        picturefile = new File(pFile + File.separator + "IvMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        Log.e(TAG, "拍照所存路径: ===" + picturefile.getAbsolutePath());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (Build.VERSION.SDK_INT > 23) {//7.0及以上
//            Uri contentUri = getUriForFile(TestWeb2Activity.this, getResources().getString(R.string.filepath), picturefile);
//            grantUriPermission(getPackageName(),contentUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//        } else {//7.0以下
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picturefile));
       // }
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICETURE);

    }

    private void cancelFilePathCallback() {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PICETURE:
                takePictureResult(resultCode);
                break;

            case REQUEST_CODE_PICK_PHOTO:
                takePhotoResult(resultCode, data);

                break;
        }
    }

    private void takePhotoResult(int resultCode, Intent data) {
        if (mFilePathCallback != null){
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                if (Build.VERSION.SDK_INT > 22) {
                    mFilePathCallbacks.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }

            }else {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        }
    }

    private void takePictureResult(int resultCode) {
        if (mFilePathCallback != null) {
            if (resultCode == RESULT_OK) {
                Uri uri = Uri.fromFile(picturefile);
                if (Build.VERSION.SDK_INT > 22) {
                    mFilePathCallbacks.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }
            } else {
                //点击了file按钮，必须有一个返回值，否则会卡死
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        }

    }


}
