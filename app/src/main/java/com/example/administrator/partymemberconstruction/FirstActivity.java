package com.example.administrator.partymemberconstruction;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.partymemberconstruction.Bean.GetHeadPage;
import com.example.administrator.partymemberconstruction.Bean.Tab;
import com.example.administrator.partymemberconstruction.Bean.UploadJson;
import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.CustomView.LoadingDialog;
import com.example.administrator.partymemberconstruction.CustomView.MTabHost;
import com.example.administrator.partymemberconstruction.CustomView.MyTabHost;
import com.example.administrator.partymemberconstruction.fragment.FirstFragment;
import com.example.administrator.partymemberconstruction.fragment.MineFragment;
import com.example.administrator.partymemberconstruction.fragment.NoticeFragment;
import com.example.administrator.partymemberconstruction.fragment.PartyConstructionFragment;
import com.example.administrator.partymemberconstruction.utils.MobileInfoUtil;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @BindView(android.R.id.tabhost)
    MTabHost th;
    @BindView(R.id.down)
    ImageView down;
    private long mExitTime;
    public static int YES = 0;
    private AlertDialog.Builder sureUpload;
    private Context mContext = this;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList<>();
    private String testUrl;
    private LoadingDialog loadingDialog;
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Unbinder bind;
    private int typeScreen;
    private String menu_logo_url1;
    private String loadurl;
    private boolean isUpload = false;
    private String packName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        bind = ButterKnife.bind(this);
        //initTab();
        if (getIntent() != null) {
            boolean isExit = getIntent().getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                this.finish();
                Intent intent1 = new Intent(this, LoadingActivity.class);
                startActivity(intent1);
            }
        }
        try {
            load();
            getUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initNewTab();
//        int w = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0,
//                View.MeasureSpec.UNSPECIFIED);
//        th.measure(w, h);
//        WindowManager manager = this.getWindowManager();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        manager.getDefaultDisplay().getMetrics(outMetrics);
//        int wi = outMetrics.widthPixels;
//        int hei = outMetrics.heightPixels;
//        int height = th.getMeasuredHeight();
//        int width = th.getMeasuredWidth();
//        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) realtabcontent.getLayoutParams(); //取控件textView当前的布局参数
//        linearParams.height = hei-height;// 控件的高强制设成20
//        realtabcontent.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        initState();
        initDialog();
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        //判断app是否下载与安装
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpload) {
                    if (isAppInstalled(FirstActivity.this, packName + ""))
                        gotoApp();
                    else
                        sureUpload.show();
                } else {
                    Intent intent = new Intent(FirstActivity.this, WebActivity.class);
                    intent.putExtra("Url", loadurl);
                    intent.putExtra("title", "");
                    startActivity(intent);
                }
            }
        });
    }

    private void initNewTab() {
        Log.d("aaa", th.getChildCount() + "数量");
        //设置文字的位置,在addTab方法之前调用,否则无效
        th.setTextPosition(MTabHost.BOTTOM_TEXTPOSITION);
        th.addTab(R.mipmap.study2, R.mipmap.study1, "学习", 3, 1, 1);
        th.addTab(R.mipmap.partyconstruction2, R.mipmap.partyconstruction1, "党建", 3, 1, 2);
        th.addTab(R.mipmap.partyconstruction2, R.mipmap.partyconstruction1, "党建", 3, 0, 3);
        th.addTab(R.mipmap.notice2, R.mipmap.notice1, "通知", 3, 1, 4);
        th.addTab(R.mipmap.mine2, R.mipmap.mine1, "个人中心", 3, 1, 5);
        Log.d("aaa", th.getChildCount() + "数量");
        FirstFragment fragment1 = new FirstFragment();

        NoticeFragment fragment3 = new NoticeFragment();

        PartyConstructionFragment fragment2 = new PartyConstructionFragment();

        MineFragment fragment4 = new MineFragment();
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.realtabcontent, fragment1);
        fragmentTransaction.add(R.id.realtabcontent, fragment2);
        fragmentTransaction.add(R.id.realtabcontent, fragment3);
        fragmentTransaction.add(R.id.realtabcontent, fragment4);
        fragmentTransaction.commit();
        th.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId > 5) {
                    checkedId = checkedId - 5;
                }
                if (checkedId != 3) {
                    if (checkedId > 3) {
                        checkedId = checkedId - 1;
                    }
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.realtabcontent, fragments.get(checkedId - 1));
                    fragmentTransaction.commit();
                }
            }
        });
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.realtabcontent, fragment1);
        fragmentTransaction.commit();
    }

    //获得链接下载地址
    private void getUploadUrl() {
        HashMap<String, String> params = new HashMap<>();
        OkhttpJsonUtil.getInstance().postByEnqueue(this, loadurl, params, UploadJson.class,
                new OkhttpJsonUtil.TextCallBack<UploadJson>() {
                    @Override
                    public void getResult(UploadJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            testUrl = result.getAndroid();//设置地址
                            upload();//下载
                        }

                    }
                });
    }

    //下载
    private void upload() {
        MyTask myTask = new MyTask();
        myTask.execute(testUrl);
    }

    void initDialog() {
        if (sureUpload == null) {
            sureUpload = new AlertDialog.Builder(this);
            sureUpload.setCancelable(false);
            sureUpload.setTitle("是否确认下载？");
            sureUpload.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //getUploadUrl();
                    testUrl = loadurl;
                    upload();
                }
            });
            sureUpload.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void gotoApp() {
        PackageManager packageManager = getPackageManager();
        Intent intent = new Intent();
        intent = packageManager.getLaunchIntentForPackage("com.dangjian");
        startActivity(intent);
    }

    //检测是否安装app
    public boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回键重写
    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(FirstActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


//    private void initTab() {
//        mInflater = LayoutInflater.from(this);
//
//        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//
//        Tab tab_nome = new Tab(R.string.study, R.drawable.selector_pic_study, FirstFragment.class);
//        Tab tab_address = new Tab(R.string.party, R.drawable.selector_pic_party, PartyConstructionFragment.class);
//        Tab tab_find_friend = new Tab(R.string.notice, R.drawable.selector_pic_notice, NoticeFragment.class);
//        Tab tab_setting = new Tab(R.string.mine, R.drawable.selector__pic_mine, MineFragment.class);
//
//        mTabs.add(tab_nome);
//        mTabs.add(tab_address);
//        mTabs.add(tab_find_friend);
//        mTabs.add(tab_setting);
//
//        for (int i = 0; i < mTabs.size(); i++) {
//            View view = mInflater.inflate(R.layout.tab_indicator, null);
//            ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
//            TextView tex = (TextView) view.findViewById(R.id.txt_indicator);
//
//
//            img.setImageResource(mTabs.get(i).getIcon());
//            tex.setText(mTabs.get(i).getTitle());
//
//            tabhost.addTab(tabhost.newTabSpec(getString(mTabs.get(i).getTitle())).setIndicator(view),
//                    mTabs.get(i).getFragment(), null);
//        }
//
//        tabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
//        tabhost.setCurrentTab(0);
//    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public class MyTask extends AsyncTask<String, Integer, String> {
        public MyTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("1", "开始下载");
            loadingDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loadingDialog.cancel();
//            update(fileName);
            File file = new File(Environment.getExternalStorageDirectory(), "党建.apk");
            if (file.exists()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(file),
                        "application/vnd.android.package-archive");
                startActivity(intent);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
            URL url = null;
            HttpURLConnection urlConn = null;
            InputStream is = null;
            try {
                url = new URL(params[0]);
                Log.d("1", "开始" + url);
                urlConn = (HttpURLConnection) url.openConnection();
                int length = urlConn.getContentLength();
                Log.d("1", "文件大小为：" + length);
                is = urlConn.getInputStream();
                Log.d("1", "开始zhong" + is);
                int read = is.available();
                Log.d("1", "文件长度为：" + read);
                OutputStream os = null;
                File file = null;
                try {
                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "党建.apk");
                    file.createNewFile();
                    os = new FileOutputStream(file);
                    byte buffer[] = new byte[1024 * 4];
                    int temp = 0;
                    int jd = 0;
                    // publishProgress(length);
                    while ((temp = is.read(buffer)) != -1) {
                        os.write(buffer, 0, temp);
                        //发送消息告知主线程当前进度
                        Log.d("1", "4");
                        jd += temp;
                        Integer[] a = new Integer[2];
                        a[0] = length;
                        a[1] = jd;
                        //publishProgress(a);
                        //textView.setText(a[0]);
                    }
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    loadingDialog.cancel();
                    Log.d("1", "获取网络资源失败，请重试！" + e.toString());
                } finally {
                    try {
                        os.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    private static final String TAG_EXIT = "exit";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            if (isExit) {
                getSharedPreferences("load", Context.MODE_PRIVATE).edit().remove("yes").commit();
                this.finish();
                Intent intent1 = new Intent(this, LoadingActivity.class);
                startActivity(intent1);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (FirstFragment.F == 2) {
            th.getRadioButton(3).performClick();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.realtabcontent, fragments.get(2));
            fragmentTransaction.commit();
            FirstFragment.F = 0;
        }
    }

    private void load() {
        HashMap<String, String> params = new HashMap<>();
        params.put("UserName", MyApplication.phone);
        params.put("Password", MyApplication.psw);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.LoadingUrl, params, UserJson.class,
                new OkhttpJsonUtil.TextCallBack<UserJson>() {
                    @Override
                    public void getResult(UserJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            Log.e("p", result.getCode());
                            if (result.getCode().equals("成功")) {
                                int u_isDelete = result.getUser().getU_IsDelete();
                                if (u_isDelete == 1) {
                                    out();
                                    MyApplication.showToast(result.getException(), 0);
                                    getSharedPreferences("load", Context.MODE_PRIVATE).edit().remove("yes").commit();
                                    finish();
                                    Intent intent1 = new Intent(FirstActivity.this, LoadingActivity.class);
                                    startActivity(intent1);
                                }
                                //根据状态选择进入的页面
                                //finish();
                                int status = result.getStatus();
                                switch (status) {
                                    case 0:
                                        Intent intent = new Intent(FirstActivity.this, ExamineActivity.class);
                                        intent.putExtra("state", "" + result.getStatus());
                                        intent.putExtra("userId", result.getUserId() + "");
                                        startActivity(intent);
                                        break;
                                    //跳转到首页
                                    case 1:
                                        //全局化用户信息
                                        MyApplication.outUserId=result.getUserInfo().getUser_ID()+"";
                                        MyApplication.user = result.getUserInfo() == null ? new UserJson.UserInfoBean() : result.getUserInfo();
                                        break;
                                    case 2:
                                        Intent intent1 = new Intent(FirstActivity.this, ExamineActivity.class);
                                        intent1.putExtra("state", "" + result.getStatus());
                                        intent1.putExtra("userId", result.getUser_id());
                                        intent1.putExtra("Error", result.getError());
                                        startActivity(intent1);
                                        break;
                                    case 3:
                                        //跳转到完善信息页面
                                        Intent intent3 = new Intent(FirstActivity.this, ImprovePersonalInformationActivity.class);
                                        intent3.putExtra("userId", result.getUserInfo().getEntityId() + "");
                                        startActivity(intent3);
                                        break;
                                }
                                //登录成功进入首页
                            } else {
                                out();
                                MyApplication.showToast(result.getException(), 0);
                                getSharedPreferences("load", Context.MODE_PRIVATE).edit().remove("yes").commit();
                                finish();
                                Intent intent1 = new Intent(FirstActivity.this, LoadingActivity.class);
                                startActivity(intent1);
                            }
                        } else {
                            MyApplication.showToast("连接服务器失败", 0);
                        }

                    }
                });
    }

    private void getUpdate() throws Exception {
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.GetHeadPageAndroid, new HashMap<String, String>(), GetHeadPage.class,
                new OkhttpJsonUtil.TextCallBack<GetHeadPage>() {
                    @Override
                    public void getResult(GetHeadPage result) {
                        if (result != null) {
                            boolean b = result.getCode().equals("成功");
                            if (b) {
                                getScreenDensity_ByWindowManager();
                                switch (typeScreen) {
                                    case 1:
                                        menu_logo_url1 = result.getMenuInfo().getMenu_Logo_Url1();
                                        break;
                                    case 2:
                                        menu_logo_url1 = result.getMenuInfo().getMenu_Logo_Url2();
                                        break;
                                    case 3:
                                        menu_logo_url1 = result.getMenuInfo().getMenu_Logo_Url3();
                                        break;
                                }
                                Picasso.with(FirstActivity.this).load(menu_logo_url1).centerCrop().fit().into(down);
                                loadurl = result.getMenuInfo().getMenu_Url();
                                int menuCategory = result.getMenuInfo().getMenu_Category();
                                if (menuCategory == 4) {
                                    isUpload = true;
                                    packName = result.getApk_name();
                                } else {
                                    isUpload = false;
                                }
                            } else {
                                MyApplication.showToast(result.getError(), 0);
                            }
                        } else {
                            MyApplication.showToast("连接服务器失败", 0);
                        }
                    }
                });
    }

    //获得屏幕分辨率
    public void getScreenDensity_ByWindowManager() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.e("p", "" + width + "  " + height);
        if (width <= 480) {
            typeScreen = 1;
        } else if (width <= 720) {
            typeScreen = 2;
        } else {
            typeScreen = 3;
        }
    }

    private void out() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", MyApplication.outUserId);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.SignOut, params, UserJson.class,
                new OkhttpJsonUtil.TextCallBack<UserJson>() {
                    @Override
                    public void getResult(UserJson result) {
                        if (result != null) {
                            // MyApplication.showToast("" + result.getSuccess() == null ? result.getException(): result.getSuccess(), 0);
                        }
                    }
                });
    }

    boolean isFirst = true;

    @Override
    protected void onStart() {
        super.onStart();
        if (!isFirst) {
            HashMap<String, String> params = new HashMap<>();
            params.put("UserName", MyApplication.phone);
            params.put("Password", MyApplication.psw);
            OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.LoadingUrl, params, UserJson.class,
                    new OkhttpJsonUtil.TextCallBack<UserJson>() {
                        @Override
                        public void getResult(UserJson result) {
                            // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                            if (result != null) {
                                Log.e("p", result.getCode());
                                if (result.getCode().equals("成功")) {
                                    int u_isDelete = result.getUser().getU_IsDelete();
                                    if (u_isDelete == 1) {
                                        out();
                                        MyApplication.showToast(result.getException(), 0);
                                        getSharedPreferences("load", Context.MODE_PRIVATE).edit().remove("yes").commit();
                                        finish();
                                        Intent intent1 = new Intent(FirstActivity.this, LoadingActivity.class);
                                        startActivity(intent1);
                                    }
                                }else{
                                    out();
                                    MyApplication.showToast(result.getException(), 0);
                                    getSharedPreferences("load", Context.MODE_PRIVATE).edit().remove("yes").commit();
                                    finish();
                                    Intent intent1 = new Intent(FirstActivity.this, LoadingActivity.class);
                                    startActivity(intent1);
                                }
                            }
                        }
                    });
        }
        isFirst = false;
    }
}
