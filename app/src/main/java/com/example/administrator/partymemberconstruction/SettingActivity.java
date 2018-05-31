package com.example.administrator.partymemberconstruction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.CustomView.Switch;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.change_psw)
    RelativeLayout changePsw;
    @BindView(R.id.bind_account)
    RelativeLayout bindAccount;
    @BindView(R.id.voice)
    Switch voice;
    @BindView(R.id.shock)
    Switch shock;
    @BindView(R.id.delete)
    RelativeLayout delete;
    @BindView(R.id.about)
    RelativeLayout about;
    @BindView(R.id.out)
    TextView out;
    @BindView(R.id.cach)
    TextView cach;
    private String about1;
    private AlertDialog.Builder builder;
    private AlertDialog.Builder builder1;
    private File pFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        about1 = intent.getStringExtra("about");
        link.setOnClickListener(this);
        out.setOnClickListener(this);
        changePsw.setOnClickListener(this);
        bindAccount.setOnClickListener(this);
        about.setOnClickListener(this);
        delete.setClickable(true);
        delete.setOnClickListener(this);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("是否退出登录？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SignOut();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("是否清除数据？");
        builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFolderFile(pFile.getAbsolutePath(),true);
                deleteFolderFile(SettingActivity.this.getCacheDir().getAbsolutePath(),true);
                getCach();
                dialog.dismiss();
            }
        });
        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

//获得图片缓存
        pFile = new File(Environment.getExternalStorageDirectory(), "MyPictures");
        if (!pFile.exists()) {
            pFile.mkdirs();
        }

        getCach();
    }

    private void getCach() {
        File cacheDir = this.getCacheDir();
        long one = getAutoFileOrFilesSize(cacheDir.getAbsolutePath());
        long two = getAutoFileOrFilesSize(pFile.getAbsolutePath());
        cach.setText("删除本地缓存（"+FormetFileSize(one+two)+"）");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.link:
                finish();
                break;
            case R.id.out:
                builder.create().show();
                break;
            case R.id.change_psw:
                Intent intent1 = new Intent(this, ChangeMyPwsActivity.class);
                startActivity(intent1);
                break;
            case R.id.bind_account:
                Intent intent2 = new Intent(this, ChangePhoneActivity.class);
                startActivity(intent2);
                break;
            case R.id.about:
                Intent intent3 = new Intent(this, WebActivity.class);
                intent3.putExtra("Url", about1);
                intent3.putExtra("title", "关于党建");
                startActivity(intent3);
                break;
            case R.id.delete:
                //MyApplication.showToast("删除",0);
builder1.show();
                break;
        }
    }
    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private void SignOut() {
        finish();
        Intent intent = new Intent(this, FirstActivity.class);
        intent.putExtra("exit", true);
        startActivity(intent);
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    public static long getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // LogUtil.E(TAG,"获取文件大小失败!");
        }
        //return FormetFileSize(blockSize);
        return blockSize;
    }

    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
}
