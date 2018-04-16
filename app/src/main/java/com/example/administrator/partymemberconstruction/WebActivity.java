package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.CustomView.ActionSheetDialog;
import com.example.administrator.partymemberconstruction.CustomView.LoadingDialog;
import com.example.administrator.partymemberconstruction.utils.GetPathFromUri4kitkat;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.FileProvider.getUriForFile;

public class WebActivity extends AppCompatActivity {

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
    private static final int REQUEST_CODE_TAKE_PICETURE = 11;
    private static final int REQUEST_CODE_PICK_PHOTO = 12;
    private static final int REQUEST_CODE_PERMISSION = 13;
    public static ValueCallback<Uri> mFilePathCallback;
    public static ValueCallback<Uri[]> mFilePathCallbacks;
    private File picturefile;
    private LoadingDialog loadingDialog;
    private String testPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();//加载动画
        //获取传过来的值
        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");
        String titleTxt = intent.getStringExtra("title");
        user_id = MyApplication.user.getUser_ID() + "";
        ui_nickName = MyApplication.user.getUi_NickName();
        title.setText(titleTxt);
        link.setClickable(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //设置webview
        WebSettings settings = web.getSettings();
        //设置自适应网页
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setUseWideViewPort(true);
        // 设置支持javascript脚本
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        //设置app内网页跳转以及页面加载完取消动画
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
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadingDialog.cancel();
            }
        });
        //设置辅助Webview
        web.setWebChromeClient(new WebChromeClient() {
            //不同版本系统打开本地资源
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {//5.0+
                showDialog();
                mFilePathCallbacks = filePathCallback;
                return true;
            }

            //openFileChooser 方法是隐藏方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {// android 系统版本>4.1.1
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
        web.loadUrl(url);
        web.loadUrl("file:///android_asset/test.html");
        web.addJavascriptInterface(new JsInteration(), "android");
    }

    private void showDialog() {
        ActionSheetDialog dialog = new ActionSheetDialog(WebActivity.this).builder().addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                //打开相册
                takeForPicture();
            }
        }).addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                //拍照
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
        //String fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        //picture = new File(Environment.getExternalStorageDirectory()
        File pFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyPictures");//图片位置
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        //拍照所存路径
        picturefile = new File(pFile + File.separator + "IvMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

        testPath = new String(picturefile.getAbsolutePath());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (Build.VERSION.SDK_INT > 23) {//7.0及以上
//            Uri contentUri = getUriForFile(WebActivity.this, getResources().getString(R.string.filepath), picturefile);
//            grantUriPermission(getPackageName(), contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
//        } else {//7.0以下
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picturefile));
        //}
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
            case 101:
                if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String result = bundle.getString("result");
                //MyApplication.showToast(result, 0);
                    Log.d("1",""+result);
            }

                break;
        }
//        if (isQR) {
//            if (resultCode == RESULT_OK) {
//                Bundle bundle = data.getExtras();
//                String result = bundle.getString("result");
//                MyApplication.showToast(result, 0);
//                isQR = false;
//            }
//        }
    }

    private void takePhotoResult(int resultCode, Intent data) {
        if (mFilePathCallback != null||mFilePathCallbacks!=null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Uri uri = Uri.fromFile(new File(path));
                if (Build.VERSION.SDK_INT > 19) {
                    mFilePathCallbacks.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }

            } else {
                mFilePathCallback.onReceiveValue(null);
                mFilePathCallback = null;
            }
        }
    }

    private void takePictureResult(int resultCode) {
        if (mFilePathCallback != null||mFilePathCallbacks!=null) {
            if (resultCode == RESULT_OK) {
               Uri uri = Uri.fromFile(picturefile);
                if (Build.VERSION.SDK_INT > 19) {
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
        //picturefile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ File.separator + "IvMG_" + "s"+ ".jpg");
//        boolean exists = picturefile.exists();
//        String s = exists + "";
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
            set();
        }
        @JavascriptInterface
        public void GetQR() {
            startActivityForResult(new Intent(WebActivity.this, CaptureActivity.class), 101);
        }
    }

    boolean isQR;

    public void set() {
        headtitle.post(new Runnable() {
            @Override
            public void run() {
                if (headtitle.getVisibility() == View.VISIBLE) {
                    headtitle.setVisibility(View.GONE);
                } else if (headtitle.getVisibility() == View.GONE) {
                    headtitle.setVisibility(View.VISIBLE);
                }
            }
        });
        isQR = true;
        //startActivityForResult(new Intent(WebActivity.this, CaptureActivity.class), 0);
    }

}
