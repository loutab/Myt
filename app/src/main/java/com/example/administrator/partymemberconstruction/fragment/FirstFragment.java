package com.example.administrator.partymemberconstruction.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.FirstBigList1Adapter;
import com.example.administrator.partymemberconstruction.Adapter.FirstBigListAdapter;
import com.example.administrator.partymemberconstruction.Bean.FirstJson;
import com.example.administrator.partymemberconstruction.Bean.GlideImageLoader;
import com.example.administrator.partymemberconstruction.Bean.ImgJson;
import com.example.administrator.partymemberconstruction.Bean.UploadJson;
import com.example.administrator.partymemberconstruction.CustomView.CustomerGridView;
import com.example.administrator.partymemberconstruction.CustomView.LoadingDialog;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.WebActivity;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

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

public class FirstFragment extends Fragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.banner)
    Banner banner;
    Unbinder unbinder;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.noticeImg)
    ImageView noticeImg;
    @BindView(R.id.noticeTxt)
    TextView noticeTxt;
    @BindView(R.id.noticeGo)
    ImageView noticeGo;
    @BindView(R.id.firstBigImg)
    ImageView firstBigImg;
    @BindView(R.id.firstBigTxt)
    TextView firstBigTxt;
    @BindView(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @BindView(R.id.secondImg)
    ImageView secondImg;
    @BindView(R.id.secondTxt)
    TextView secondTxt;
    @BindView(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @BindView(R.id.thirdImg)
    ImageView thirdImg;
    @BindView(R.id.thirdTxt)
    TextView thirdTxt;
    @BindView(R.id.firstBigList)
    CustomerGridView firstBigList;
    @BindView(R.id.firstBigList1)
    CustomerGridView firstBigList1;
    @BindView(R.id.firstBigList2)
    CustomerGridView firstBigList2;
    @BindView(R.id.firstBigList3)
    CustomerGridView firstBigList3;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.four)
    TextView four;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.notice_click)
    RelativeLayout noticeClick;

    private List<String> images;
    private List<String[]> listTemp;
    private List<String[]> listTemp1;
    private List<String[]> listTemp2;
    private List<String[]> listTemp3;
    private FirstJson.MenuListBean notice_menu;
    private Context currentContext;
    private FirstBigListAdapter firstBigListAdapter;
    private FirstBigList1Adapter firstBigList1Adapter1;
    private FirstBigList1Adapter firstBigList1Adapter2;
    private FirstBigList1Adapter firstBigList1Adapter3;
    private List<List<FirstJson.MenuListBean>> allDateList;
    private FirstJson.MenuListBean notice_menu1;
    private List<TextView> textViewList;
    private String testUrl;
    private AlertDialog.Builder sureUpload;//确认下载弹框
    //private UploadDialog uploadDialog;
    private LoadingDialog loadingDialog;
    private int typeScreen;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentContext = this.getContext();
        initDate();
        loadingDialog.show();
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        firstBigListAdapter = new FirstBigListAdapter(listTemp, view.getContext());
        firstBigList.setAdapter(firstBigListAdapter);
        firstBigListAdapter.notifyDataSetChanged();
        firstBigList1Adapter1 = new FirstBigList1Adapter(listTemp1, view.getContext());
        firstBigList1Adapter2 = new FirstBigList1Adapter(listTemp2, view.getContext());
        firstBigList1Adapter3 = new FirstBigList1Adapter(listTemp3, view.getContext());
        firstBigList1.setAdapter(firstBigList1Adapter1);
        firstBigList1Adapter1.notifyDataSetChanged();
        firstBigList2.setAdapter(firstBigList1Adapter2);
        firstBigListAdapter.notifyDataSetChanged();
        firstBigList3.setAdapter(firstBigList1Adapter3);
        firstBigListAdapter.notifyDataSetChanged();
        getScreenDensity_ByWindowManager();
        connect();
        connect2();
        setFirstBigClick();
        setHeadClick();
        initDialog();
    }

    private void setHeadClick() {
//        link.setClickable(true);
//        link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isAppInstalled(currentContext,"com.dangjian"))
//                gotoApp();
//                else
//                sureUpload.show();
////Intent intent=new Intent(currentContext, TestActivity.class);
////startActivity(intent);
//            }
//        });
        link.setVisibility(View.INVISIBLE);
    }

    private void gotoApp() {
        PackageManager packageManager = getActivity().getPackageManager();
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

    void initDialog() {
        if (sureUpload == null) {
            sureUpload = new AlertDialog.Builder(currentContext);
            sureUpload.setCancelable(false);
            sureUpload.setTitle("是否确认下载？");
            sureUpload.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getUploadUrl();
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

    //下载
    private void upload() {
        MyTask myTask = new MyTask();
        myTask.execute(testUrl);
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
            int i = 0;
            if (values.length > 1) {
                Log.d("1", "设置进度为" + values[1]);
                bar.setProgress(values[1]);
                //textView.setText(values[1]+"/"+values[0]);
                textView.setText("100");
            } else {
                Log.d("1", "设置最大尺度为" + values[0]);
                bar.setMax(values[0]);
            }

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

    private void initDate() {
        images = new ArrayList<>();

        listTemp = new ArrayList<>();
        listTemp1 = new ArrayList<>();
        listTemp2 = new ArrayList<>();
        listTemp3 = new ArrayList<>();

        textViewList = new ArrayList<>();
        textViewList.add(one);
        textViewList.add(two);
        textViewList.add(three);
        textViewList.add(four);

        //uploadDialog = new UploadDialog(currentContext);
        loadingDialog = new LoadingDialog(currentContext);
        loadingDialog.setCancelable(false);
    }

    //获得屏幕分辨率
    public void getScreenDensity_ByWindowManager() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        float density = mDisplayMetrics.density;
        int densityDpi = mDisplayMetrics.densityDpi;
        Log.d("p", "" + width + "  " + height);
        if (width <= 480) {
            typeScreen = 1;
        } else if (width <= 720) {
            typeScreen = 2;
        } else {
            typeScreen = 3;
        }
    }

    //获得首页主要数据
    private void connect() {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID", MyApplication.user.getUser_ID() + "");
        params.put("Resol_Type", typeScreen + "");
        params.put("type", 0 + "");
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.FirstUrl, params, FirstJson.class,
                new OkhttpJsonUtil.TextCallBack<FirstJson>() {
                    @Override
                    public void getResult(FirstJson result) {
                        loadingDialog.cancel();
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                List<FirstJson.MenuListBean> menu_list = result.getMenu_List();
                                if (menu_list.size() > 0) {
                                    String menu_nameTemp = "";
                                    setNotice(menu_list.get(0));//设置通告
                                    allDateList = new ArrayList<>();
                                    List<FirstJson.MenuListBean> listTemp = new ArrayList<>();
                                    int textTemp = 0;//textList设置次数
                                    for (int i = 1; i < menu_list.size(); i++) {
                                        if (i == 1) {
                                            menu_nameTemp = menu_list.get(i).getMenu_Region();
                                            textViewList.get(textTemp).setText(menu_nameTemp);
                                            textTemp++;
                                        }
                                        if (menu_list.get(i).getMenu_Region().equals(menu_nameTemp)) {
                                            listTemp.add(menu_list.get(i));
                                        } else {
                                            allDateList.add(listTemp);
                                            listTemp = new ArrayList<>();
                                            listTemp.add(menu_list.get(i));
                                            menu_nameTemp = menu_list.get(i).getMenu_Region();
                                            textViewList.get(textTemp).setText(menu_nameTemp);
                                            textTemp++;
                                        }

                                    }
                                    allDateList.add(listTemp);
                                    changeDate(allDateList);//更新listview数据
                                }


                            } else {
                                MyApplication.showToast("内容请求失败", 0);
                            }
                        } else {
                            MyApplication.showToast("网络请求失败", 0);
                        }


                    }
                });
    }

    //获得轮转图地址
    private void connect2() {
        HashMap<String, String> params = new HashMap<>();
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.FirstUrl2, params, ImgJson.class,
                new OkhttpJsonUtil.TextCallBack<ImgJson>() {
                    @Override
                    public void getResult(ImgJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                List<ImgJson.ProductBean> product = result.getProduct();
                                List<String> images = new ArrayList<>();
                                for (int i = 0; i < product.size(); i++) {
                                    images.add(product.get(i).getPicture_Url());
                                }
                                banner.update(images);
                            }
                        }

                    }
                });
    }

    //获得链接下载地址
    private void getUploadUrl() {
        HashMap<String, String> params = new HashMap<>();
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.UploadUrl, params, UploadJson.class,
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

    private void changeDate(List<List<FirstJson.MenuListBean>> allDateList) {
        List<List<String[]>> lists = new ArrayList<>();
        listTemp.clear();
        listTemp1.clear();
        listTemp2.clear();
        listTemp3.clear();
        lists.add(listTemp);
        lists.add(listTemp1);
        lists.add(listTemp2);
        lists.add(listTemp3);
        if (allDateList.size() >= lists.size()) {
            for (int i = 0; i < lists.size(); i++) {
                if (i == 0) {
                    for (int j = 0; j < allDateList.get(i).size(); j++) {
                        if (j >= 3) {
                            lists.get(i).add(new String[]{allDateList.get(i).get(j).getMenu_Logo_Url(), allDateList.get(i).get(j).getMenu_Name()});
                        } else {
                            if (j == 0) {
                                String menu_logo_url = allDateList.get(i).get(j).getMenu_Logo_Url();
                                Picasso.with(currentContext).load(allDateList.get(i).get(j).getMenu_Logo_Url()).into(firstBigImg);
                                firstBigTxt.setText(allDateList.get(i).get(j).getMenu_Name());
                            }
                            if (j == 1) {
                                String menu_logo_url = allDateList.get(i).get(j).getMenu_Logo_Url();
                                Picasso.with(currentContext).load(allDateList.get(i).get(j).getMenu_Logo_Url()).into(secondImg);
                                secondTxt.setText(allDateList.get(i).get(j).getMenu_Name());
                            }
                            if (j == 2) {
                                String menu_logo_url = allDateList.get(i).get(j).getMenu_Logo_Url();
                                Picasso.with(currentContext).load(allDateList.get(i).get(j).getMenu_Logo_Url()).into(thirdImg);
                                thirdTxt.setText(allDateList.get(i).get(j).getMenu_Name());
                            }

                        }
                    }
                } else {
                    for (int j = 0; j < allDateList.get(i).size(); j++) {
                        lists.get(i).add(new String[]{allDateList.get(i).get(j).getMenu_Logo_Url(), allDateList.get(i).get(j).getMenu_Name()});
                    }
                }
            }
        } else {
            for (int i = 0; i < allDateList.size(); i++) {
                if (i == 0) {
                    for (int j = 3; j < allDateList.get(i).size(); j++) {
                        lists.get(i).add(new String[]{allDateList.get(i).get(j).getMenu_Logo_Url(), allDateList.get(i).get(j).getMenu_Name()});
                    }
                } else {
                    for (int j = 0; j < allDateList.get(i).size(); j++) {
                        lists.get(i).add(new String[]{allDateList.get(i).get(j).getMenu_Logo_Url(), allDateList.get(i).get(j).getMenu_Name()});
                    }
                }

            }
        }
        firstBigListAdapter.notifyDataSetChanged();
        firstBigList1Adapter1.notifyDataSetChanged();
        firstBigList1Adapter2.notifyDataSetChanged();
        firstBigList1Adapter3.notifyDataSetChanged();
    }

    private void setNotice(final FirstJson.MenuListBean notice_menu) {
        if (notice_menu.getMenu_Name().length() >= 12) {
            String s = notice_menu.getMenu_Name().substring(0, 13) + "...";
            noticeTxt.setText(s);
        } else
            noticeTxt.setText(notice_menu.getMenu_Name());
        noticeClick.setClickable(true);
        notice_menu1 = notice_menu;
        noticeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoWebView(notice_menu1.getMenu_Url(), "通知公告");
            }
        });
    }

    private void setFirstBigClick() {

        relativeLayout1.setClickable(true);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstJson.MenuListBean menuListBean = allDateList.get(0).get(0);
                gotoWebView(menuListBean.getMenu_Url(), menuListBean.getMenu_Name());
            }
        });

        relativeLayout2.setClickable(true);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstJson.MenuListBean menuListBean = allDateList.get(0).get(2);
                gotoWebView(menuListBean.getMenu_Url(), menuListBean.getMenu_Name());
            }
        });

        relativeLayout3.setClickable(true);
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstJson.MenuListBean menuListBean = allDateList.get(0).get(1);
                gotoWebView(menuListBean.getMenu_Url(), menuListBean.getMenu_Name());
            }
        });


        firstBigList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FirstJson.MenuListBean> menuListBeans1 = allDateList.get(0);
                String menu_url = menuListBeans1.get(position + 3).getMenu_Url();
                gotoWebView(menu_url, menuListBeans1.get(position + 3).getMenu_Name());
            }
        });
        firstBigList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FirstJson.MenuListBean> menuListBeans1 = allDateList.get(1);
                String menu_url = menuListBeans1.get(position).getMenu_Url();
                gotoWebView(menu_url, menuListBeans1.get(position).getMenu_Name());
            }
        });
        firstBigList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FirstJson.MenuListBean> menuListBeans2 = allDateList.get(2);
                String menu_url = menuListBeans2.get(position).getMenu_Url();
                gotoWebView(menu_url, menuListBeans2.get(position).getMenu_Name());
            }
        });

        firstBigList3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FirstJson.MenuListBean> menuListBeans3 = allDateList.get(3);
                String menu_url = menuListBeans3.get(position).getMenu_Url();
                gotoWebView(menu_url, menuListBeans3.get(position).getMenu_Name());
            }
        });
    }

    private void gotoWebView(String menu_url, String title) {
        Intent intent = new Intent(currentContext, WebActivity.class);
        intent.putExtra("Url", menu_url);
        intent.putExtra("title", title);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private ProgressBar bar;
    private TextView textView;

    public class UploadDialog extends Dialog {


        public View getCustomView() {
            return customView;
        }

        public void setCustomView(View customView) {
            this.customView = customView;
        }

        public View customView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.upload_layout);
            setTitle("下载中...");
            setCanceledOnTouchOutside(false);
            bar = customView.findViewById(R.id.jd);
            textView = customView.findViewById(R.id.text);
        }

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        protected void onStop() {
            super.onStop();
        }

//        protected UploadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
//            super(context, cancelable, cancelListener);
//        }

        public UploadDialog(Context context) {
            super(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            customView = inflater.inflate(R.layout.upload_layout, null);
            bar = customView.findViewById(R.id.jd);
            textView = customView.findViewById(R.id.text);
        }
    }
}
