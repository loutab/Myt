package com.example.administrator.partymemberconstruction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;

import com.example.administrator.partymemberconstruction.utils.APIWebviewTBS;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.CodeJson;
import com.example.administrator.partymemberconstruction.Bean.SignJson;
import com.example.administrator.partymemberconstruction.CustomView.ActionSheetDialog;
import com.example.administrator.partymemberconstruction.CustomView.LoadingDialog;
import com.example.administrator.partymemberconstruction.utils.GetPathFromUri4kitkat;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.ScreenListener;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
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
    private ScreenListener screenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        APIWebviewTBS.getAPIWebview().initTBSActivity(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();//加载动画
        //获取传过来的值
        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");
        String titleTxt = intent.getStringExtra("title");
        user_id = MyApplication.user.getUser_ID() + "";
        ui_nickName = MyApplication.user.getUi_NickName();
        title.setText(titleTxt+"");
        //全部隐藏
        headtitle.setVisibility(View.GONE);

        link.setClickable(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //注册广播监听锁屏
        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                web.loadUrl("javascript:start()");
                // MyApplication.showToast("亮",0);
                Log.e("screen", "亮");
            }

            @Override
            public void onScreenOff() {
                web.loadUrl("javascript:pause()");
                // MyApplication.showToast("暗",0);
                Log.e("screen", "an");
            }

            @Override
            public void onUserPresent() {
                // MyApplication.showToast("解",0);
                Log.e("screen", "jie");
            }
        });

//        String headTitle = intent.getStringExtra("headTitle");
//        if(!TextUtils.isEmpty(headTitle)){
//            headtitle.setVisibility(View.GONE);
//        }

        //设置webview
        WebSettings settings = web.getSettings();

        settings.setUseWideViewPort(true); // 关键点
        settings.setLoadWithOverviewMode(true);

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
                isTheOne = false;
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
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                loadingDialog.cancel();
            }

        });
        //设置辅助Webview
        web.setWebChromeClient(new WebChromeClient() {
            //不同版本系统打开本地资源
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {//5.0+
                Log.e("wenjian","测试5");
                showDialog();
                mFilePathCallbacks = filePathCallback;
                return true;
            }

            //openFileChooser 方法是隐藏方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {// android 系统版本>4.1.1
                Log.e("wenjian","测试4");
                showDialog();
                mFilePathCallback = uploadMsg;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {//android 系统版本<3.0
                Log.e("wenjian","测试2");
                showDialog();
                mFilePathCallback = uploadMsg;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {//android 系统版本3.0+
                Log.e("wenjian","测试3");
                showDialog();
                mFilePathCallback = uploadMsg;

            }


////视频全屏
//            @Override
//            public View getVideoLoadingProgressView() {
//                Log.e("ss","getVideoLoadingProgressView");
//                FrameLayout frameLayout = new FrameLayout(WebActivity.this);
//                frameLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//                return frameLayout;
//            }
//
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback callback) {
//                showCustomView(view, callback);
//                Log.e("ss","onShowCustomView");
//            }
//
//            @Override
//            public void onHideCustomView() {
//                Log.e("ss","onHideCustomView");
//                hideCustomView();
//            }

        });
        web.loadUrl(url);
        //web.loadUrl("http://v.qq.com/iframe/player.html?vid=o0318tp1ddw&tiny=0&auto=0");
        web.addJavascriptInterface(new JsInteration(), "android");
    }
    /** 视频全屏参数 */
//    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//    private View customView;
//    private FrameLayout fullscreenContainer;
//    private WebChromeClient.CustomViewCallback customViewCallback;
//    /** 视频播放全屏 **/
//    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
//        // if a view already exists then immediately terminate the new one
//        if (customView != null) {
//            callback.onCustomViewHidden();
//            return;
//        }
//
//        WebActivity.this.getWindow().getDecorView();
//
//        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
//        fullscreenContainer = new FullscreenHolder(WebActivity.this);
//        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
//        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
//        customView = view;
//        setStatusBarVisibility(false);
//        customViewCallback = callback;
//    }
//
//    /** 隐藏视频全屏 */
//    private void hideCustomView() {
//        if (customView == null) {
//            return;
//        }
//
//        setStatusBarVisibility(true);
//        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
//        decor.removeView(fullscreenContainer);
//        fullscreenContainer = null;
//        customView = null;
//        customViewCallback.onCustomViewHidden();
//        web.setVisibility(View.VISIBLE);
//    }
//
//    /** 全屏容器界面 */
//    static class FullscreenHolder extends FrameLayout {
//
//        public FullscreenHolder(Context ctx) {
//            super(ctx);
//            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent evt) {
//            return true;
//        }
//    }
//
//    private void setStatusBarVisibility(boolean visible) {
//        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }



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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_PICK_PHOTO);

        }else{
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);}

    }
//权限处理

    /**
     * 调用相机
     */
    private void takeForPicture() {
//       Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//手机图片的公共目录
        //String fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        //picture = new File(Environment.getExternalStorageDirectory()
        File pFile = new File(Environment.getExternalStorageDirectory(), "MyPictures");//图片位置
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

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_TAKE_PICETURE);

        }else{
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picturefile));
        //}
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICETURE);}

    }

    private void cancelFilePathCallback() {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }else if(mFilePathCallbacks!=null){
            mFilePathCallbacks.onReceiveValue(null);
            mFilePathCallback=null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == REQUEST_CODE_TAKE_PICETURE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picturefile));
                //}
                startActivityForResult(intent, REQUEST_CODE_TAKE_PICETURE);
            } else
            {
                // Permission Denied
              //  Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == REQUEST_CODE_PICK_PHOTO)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
            } else
            {
                // Permission Denied
                //Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    Log.d("1", "" + result);
                    if (!TextUtils.isEmpty(result))
                        completeSign(result);
                    else
                        MyApplication.showToast("扫码失败", 0);
                }

                break;
            case Activity.RESULT_CANCELED:
                Intent intent = new Intent(this, WebActivity.class);
//startActivity(intent);
                finish();
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


    private void completeSign(String s) {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID", MyApplication.user.getUser_ID() + "");
        params.put("MeetingID", meetID + "");
        params.put("MD5", s);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.Sign, params, SignJson.class,
                new OkhttpJsonUtil.TextCallBack<SignJson>() {
                    @Override
                    public void getResult(SignJson result) {
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                web.loadUrl(result.getUrl());
                            }
                            MyApplication.showToast(result.getError(), 0);
                        } else {
                            MyApplication.showToast("签到失败", 0);
                        }
                    }
                });
    }

    private void takePhotoResult(int resultCode, Intent data) {
        Uri newResult = data == null ? null : data.getData();
        if (newResult != null) {
            String path = GetPathFromUri4kitkat.getPath(this, newResult);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            String s = Bitmap2StrByBase64(bitmap);
            Log.e("sssss", s);
            web.loadUrl("javascript:getimg('" + s + "')");
        } else {
            MyApplication.showToast("获取照片失败", 0);
        }
        if (mFilePathCallback != null || mFilePathCallbacks != null) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result != null) {
                String path = GetPathFromUri4kitkat.getPath(this, result);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                String s = Bitmap2StrByBase64(bitmap);
                Log.e("sssss", s);
                web.loadUrl("javascript:getimg('" + s + "')");
                Uri uri = Uri.fromFile(new File(path));
                if (Build.VERSION.SDK_INT > 18) {
                    mFilePathCallbacks.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }

            } else {
                mFilePathCallbacks.onReceiveValue(null);
                mFilePathCallbacks = null;
            }
        }

    }

    public String Bitmap2StrByBase64(Bitmap bit) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private void takePictureResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            String path = GetPathFromUri4kitkat.getPath(this, Uri.fromFile(picturefile));
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            String s = Bitmap2StrByBase64(bitmap);
            Log.e("sssss", s);
            web.loadUrl("javascript:getimg('" + s + "')");
        } else {
            MyApplication.showToast("获取照片失败", 0);
        }


        if (mFilePathCallback != null || mFilePathCallbacks != null) {
            if (resultCode == RESULT_OK) {
                Uri uri = Uri.fromFile(picturefile);
                if (Build.VERSION.SDK_INT > 18) {
                    mFilePathCallbacks.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallback.onReceiveValue(uri);
                }
            } else {
                //点击了file按钮，必须有一个返回值，否则会卡死
                mFilePathCallbacks.onReceiveValue(null);
                mFilePathCallbacks = null;
            }
        }
        //picturefile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+ File.separator + "IvMG_" + "s"+ ".jpg");
//        boolean exists = picturefile.exists();
//        String s = exists + "";
    }

    boolean isTheOne;

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

//        @JavascriptInterface
//        public void Show() {
//            headtitle.post(new Runnable() {
//                @Override
//                public void run() {
//                    headtitle.setVisibility(View.VISIBLE);
//                }
//            });
//        }

        @JavascriptInterface
        public void isFirst() {
            isTheOne = true;
        }

        @JavascriptInterface
        public void backToHome() {
            finish();
        }
//        @JavascriptInterface
//        public void Hide() {
//            headtitle.post(new Runnable() {
//                @Override
//                public void run() {
//                    headtitle.setVisibility(View.GONE);
//                }
//            });
//        }

        @JavascriptInterface
        public void GetPic() {
            showDialog();
        }

        @JavascriptInterface
        public void GetQR(int meetingID) {
            meetID = meetingID;
            startActivityForResult(new Intent(WebActivity.this, CaptureActivity.class), 101);
        }
    }

    int meetID;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack() && !isTheOne) {
                web.goBack();//返回上一页面
                Log.e("back", "返回");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenListener.unregisterListener();
    }
}
