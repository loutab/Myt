package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.ContactsPersonAdapter;
import com.example.administrator.partymemberconstruction.Bean.ContactsPersonBean;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsPersonActivity extends AppCompatActivity {

    @BindView(R.id.headImg)
    CircleImageView headImg;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    ImageView sex;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.goRight)
    Button goRight;
    private String userId;
    private List<ContactsPersonBean.UserListBean> userList;
    private ContactsPersonAdapter contactsPersonAdapter;
    private String userName;
    private String headUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_person);
        ButterKnife.bind(this);
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        float density = getResources().getDisplayMetrics().density;
        int i = (int) (34 * density + 0.5f);
        i=(int)(width-i);

        userId = getIntent().getStringExtra("userId");
        userName = getIntent().getStringExtra("name")+"";
        String absolutePath = getDCIMFile().getAbsolutePath();
        headUrl = getIntent().getStringExtra("url")+"";
        if(!TextUtils.isEmpty(headUrl)){
            //t.start();
            headUrl=headUrl.replace(" ","");
            if(headUrl.isEmpty()){
                headUrl="www";
            }
            Picasso.with(ContactsPersonActivity.this).load(headUrl).error(R.mipmap.default_head).into(headImg);
            download();
        }
        if (TextUtils.isEmpty(userId)) {
            MyApplication.showToast("用户ID不存在", 0);
        } else {
            userList = new ArrayList<>();
            getDate();
            contactsPersonAdapter = new ContactsPersonAdapter(userList, this,i,absolutePath);
            list.setAdapter(contactsPersonAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String ai_jumpLink = userList.get(i).getAi_JumpLink();
                    Intent intent = new Intent(ContactsPersonActivity.this, WebActivity.class);
                    intent.putExtra("Url", ai_jumpLink + "");
                    startActivity(intent);
                }
            });
            goRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ContactsPersonActivity.this, PersonDateActivity.class);
                    startActivity(intent);
                }
            });
        }

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.showToast("功能待开通，敬请期待",0);
            }
        });
    }
    //下载头像到本地
    private void download() {
        //获得图片的地址
        String url = headUrl;
        //Target
        Target target = new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                File dcimFile = getDCIMFile();
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(dcimFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 70, ostream);
                    ostream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        //Picasso下载
        Picasso.with(this).load(url).into(target);

    }
    //为了下载图片资源，开辟一个新的子线程
    Thread t=new Thread(){
        public void run() {
            //下载图片的路径
            String iPath=headUrl;
            try {
                //对资源链接
                URL url=new URL(iPath);
                //打开输入流
                InputStream inputStream=url.openStream();
                //对网上资源进行下载转换位图图片
                BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                //再一次打开
                inputStream=url.openStream();
                FileOutputStream fileOutputStream=new FileOutputStream(getDCIMFile());
                int hasRead=0;
                while((hasRead=inputStream.read())!=-1){
                    fileOutputStream.write(hasRead);
                }
                fileOutputStream.close();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    };
    //保存路径
    public File getDCIMFile() {
        File pFile = new File(Environment.getExternalStorageDirectory() + "/MyHead");//图片位置
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        //拍照所存路径
       File picturefile = new File(pFile + File.separator + "IvMG_" + userName+ ".jpg");
        return picturefile;
    }


    private void getDate() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        // params.put("userId",1+"");
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.GetContactsDetail, params, ContactsPersonBean.class,
                new OkhttpJsonUtil.TextCallBack<ContactsPersonBean>() {
                    @Override
                    public void getResult(ContactsPersonBean result) {
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                if (result.getUserinfo() != null) {
                                    MyApplication.otherBean = result.getUserinfo();
                                    String nickName = result.getUserinfo().getNickName();
                                    name.setText(nickName == null ? "" : nickName);
                                    int sexInt = result.getUserinfo().getSex();
                                    if (sexInt == 0) {
                                        sex.setImageResource(R.mipmap.personman);
                                    } else {
                                        sex.setImageResource(R.mipmap.personwoman);
                                    }
                                    String s = result.getUserinfo().getUi_Headimg() == null ? "" : result.getUserinfo().getUi_Headimg();
                                    if (result.getUserinfo().getUi_Headimg() != null)
                                        MyApplication.otherHead = result.getUserinfo().getUi_Headimg();
                                    if (result.getUserList() != null) {
                                        if (result.getUserList().size() > 0) {
                                            userList.clear();
                                            userList.addAll(result.getUserList());
                                            contactsPersonAdapter.notifyDataSetChanged();

                                        }
                                    }
                                }
                            } else if (result.getCode().equals("失败")) {
                                if (result.getUserinfo() != null) {
                                    MyApplication.otherBean = result.getUserinfo();
                                    String nickName = result.getUserinfo().getNickName();
                                    name.setText(nickName == null ? "" : nickName);
                                    int sexInt = result.getUserinfo().getSex();
                                    if (sexInt == 0) {
                                        sex.setImageResource(R.mipmap.personman);
                                    } else {
                                        sex.setImageResource(R.mipmap.personwoman);
                                    }
                                    String ui_headimg = result.getUserinfo().getUi_Headimg();
                                    if (ui_headimg != null) {
                                        ui_headimg= ui_headimg.replace(" ", "");
                                    } else {
                                        ui_headimg = "www";
                                    }
                                    Picasso.with(ContactsPersonActivity.this).load(TextUtils.isEmpty(ui_headimg) ? "www" :
                                            ui_headimg).error(R.mipmap.default_head).into(headImg);
                                }
                            } else {
                                MyApplication.showToast("暂无数据", 0);
                                finish();
                            }
                        } else {
                            MyApplication.showToast("连接服务器失败", 0);
                            finish();
                        }
                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        File dir =new File(Environment.getExternalStorageDirectory() + "/MyHead");
        deleteDirWihtFile(dir);
    }
    public  void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}
