package com.example.administrator.partymemberconstruction.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.MineListAdapter;
import com.example.administrator.partymemberconstruction.Bean.ChangeJson;
import com.example.administrator.partymemberconstruction.Bean.PostImgJson;
import com.example.administrator.partymemberconstruction.Bean.SelfInfo;
import com.example.administrator.partymemberconstruction.Bean.SignJson;
import com.example.administrator.partymemberconstruction.Bean.UserJson;
import com.example.administrator.partymemberconstruction.ContactsActivity;
import com.example.administrator.partymemberconstruction.CustomView.ChangeHeadImgDialog;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.LoadingActivity;
import com.example.administrator.partymemberconstruction.MyApplication;
import com.example.administrator.partymemberconstruction.R;
import com.example.administrator.partymemberconstruction.SettingActivity;
import com.example.administrator.partymemberconstruction.WebActivity;
import com.example.administrator.partymemberconstruction.utils.GetPathFromUri4kitkat;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment with a Google +1 button.
 */
public class MineFragment extends Fragment {


    @BindView(R.id.headImg)
    CircleImageView headImg;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.level)
    TextView level;
    @BindView(R.id.introduction)
    TextView introduction;
    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.right)
    TextView right;
    @BindView(R.id.contacts)
    LinearLayout contacts;
    @BindView(R.id.group)
    LinearLayout group;
    @BindView(R.id.personalData)
    LinearLayout personalData;
    @BindView(R.id.integral)
    LinearLayout integral;
    @BindView(R.id.list)
    ListView list;
    Unbinder unbinder;
    private List<String[]> lists;
    private SelfInfo.MenuListBean menuListBean;
    private SelfInfo.MenuListBean collect;
    private SelfInfo.MenuListBean inter;
    private SelfInfo.MenuListBean selfInfo;
    private ChangeHeadImgDialog changeHeadImgDialog;
    private File picturefile;
    private String aboutPart;
    private String tempUrl;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        MineListAdapter mineListAdapter=new MineListAdapter(getContext(),lists);
        list.setAdapter(mineListAdapter);
        //设置联系人点击事件
        contacts.setClickable(true);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gotoActivity(ContactsActivity.class);
            }
        });
        //设置设置点击事件
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SettingActivity.class);
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    if (collect!=null)
                    gotoNewActivity(collect.getMenu_Url(),collect.getMenu_Name());
                }
            }
        });
        integral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inter!=null)
                gotoNewActivity(inter.getMenu_Url(),inter.getMenu_Name());
            }
        });
        personalData.setClickable(true);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selfInfo!=null)
                gotoNewActivity(MineFragment.this.selfInfo.getMenu_Url(), MineFragment.this.selfInfo.getMenu_Name());
            }
        });
        getSelf();
        //设置头像点击事件
        changeHeadImgDialog = new ChangeHeadImgDialog(this.getContext());
        headImg.setClickable(true);
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeadImgDialog.show();
            }
        });
        changeHeadImgDialog.getPhone().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //相册
                takePhoto();
            }
        });
        changeHeadImgDialog.getTake().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //照相
                pickPhoto();
            }
        });
    }
    private static final int REQUEST_CODE_TAKE_PICETURE = 11;
    private static final int REQUEST_CODE_PICK_PHOTO = 12;
    private void takePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

    private void pickPhoto() {
        File pFile = new File(Environment.getExternalStorageDirectory(), "MyPictures");//图片位置
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        //拍照所存路径
        picturefile = new File(pFile + File.separator + "IvMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
       String testPath = new String(picturefile.getAbsolutePath());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picturefile));
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICETURE);
    }
    private void takePhotoResult(int resultCode, Intent data) {
        Uri newResult = data == null? null : data.getData();
        if (newResult != null) {
            String path = GetPathFromUri4kitkat.getPath(this.getContext(), newResult);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            //headImg.setImageBitmap(bitmap);
            String s = Bitmap2StrByBase64(bitmap);
            postImg("data:image/jpeg;base64,"+s);
        }
        else{
            MyApplication.showToast("获取照片失败,图片未更改",0);
        }}

    private void postImg(String s) {
        HashMap<String, String> params = new HashMap<>();
        params.put("headImg",s);
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.PostImg, params, PostImgJson.class,
                new OkhttpJsonUtil.TextCallBack<PostImgJson>() {
                    @Override
                    public void getResult(PostImgJson result) {
                        if (result != null) {
                            if(result.getCode().equals("成功")){
                                tempUrl = result.getSuccess();

                                changeImg(tempUrl);
                            }else{
                                MyApplication.showToast(result.getError(),0);
                            }
    }else{
                            MyApplication.showToast("上传照片失败",0);
                        }
                    }
        });
    }

    private void changeImg(String url) {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID",MyApplication.user.getUser_ID()+"");
        params.put("ui_Headimg",url);
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.ChangeImg, params, ChangeJson.class,
                new OkhttpJsonUtil.TextCallBack<ChangeJson>() {
                    @Override
                    public void getResult(ChangeJson result) {
                        if (result != null) {
                            if(result.getCode().equals("成功")){
                                MyApplication.user.setUi_Headimg(tempUrl);
                                Picasso.with(MineFragment.this.getContext()).load(tempUrl).error(R.mipmap.default_head).into(headImg);
                            }else{
                                MyApplication.showToast(result.getException(),0);
                            }
                        }else{
                            MyApplication.showToast("更改头像失败",0);
                        }
                    }
                });
    }

    private void takePictureResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            String path = GetPathFromUri4kitkat.getPath(this.getContext(),Uri.fromFile(picturefile));
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            //headImg.setImageBitmap(bitmap);
            String s = Bitmap2StrByBase64(bitmap);
            postImg("data:image/jpeg;base64,"+s);
        }else{
            MyApplication.showToast("获取照片失败,图片未更改",0);
        }}
    public String Bitmap2StrByBase64(Bitmap bit){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 40, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PICETURE:
                takePictureResult(resultCode);
                changeHeadImgDialog.cancel();
                break;

            case REQUEST_CODE_PICK_PHOTO:
                takePhotoResult(resultCode, data);
                changeHeadImgDialog.cancel();
                break;
        }
    }


    private void getSelf() {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID", MyApplication.user.getUser_ID() + "");
        params.put("Resol_Type", "1");
        params.put("type", "3");
        OkhttpJsonUtil.getInstance().postByEnqueue(this.getActivity(), Url.FirstUrl, params, SelfInfo.class,
                new OkhttpJsonUtil.TextCallBack<SelfInfo>() {
                    @Override
                    public void getResult(SelfInfo result) {
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                final List<SelfInfo.MenuListBean> menu_list = result.getMenu_List();
                                for (SelfInfo.MenuListBean bean:menu_list) {
                                    menuListBean = bean;
                                    switch (menuListBean.getMenu_Name()){
                                        case "个人资料":
                                            selfInfo = MineFragment.this.menuListBean;

                                            break;
                                        case  "积分":
                                            inter = MineFragment.this.menuListBean;

                                            break;
                                        case "我的收藏":
                                            collect = MineFragment.this.menuListBean;
                                            break;
                                        case "关于党建":
                                            aboutPart = MineFragment.this.menuListBean.getMenu_Url();
                                            break;

                                    }
                                }

                            }
                        }
                            else
                                MyApplication.showToast("获取个人信息失败",0);
                    }
        }
                    );
    }

    private void gotoNewActivity(String currentmenu_url,String title) {
        Intent intent=new Intent(getContext(), WebActivity.class);
        intent.putExtra("Url",currentmenu_url);
        intent.putExtra("title",title);
        intent.putExtra("headTitle","s");
        startActivity(intent);
    }

    private void gotoActivity(Class c) {
        Intent intent=new Intent(getContext(),c);
        intent.putExtra("about",aboutPart);
        startActivity(intent);
    }

    private void initDate() {
        lists = new ArrayList<>();
        //lists.add(new String[]{"我发的消息",R.mipmap.mymessage+""});
        lists.add(new String[]{"我的收藏",R.mipmap.mycollect+""});
        String userName=MyApplication.user.getUi_NickName()==null?"":MyApplication.user.getUi_NickName();
        name.setText(userName);

        String introduce=MyApplication.user.getUi_Introduction()==null?"":MyApplication.user.getUi_Introduction();
        introduction.setText(introduce);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }


    @Override
    public void onStart() {
        super.onStart();
        HashMap<String, String> params = new HashMap<>();
        params.put("UserName", MyApplication.phone);
        params.put("Password", MyApplication.psw);
        OkhttpJsonUtil.getInstance().postByEnqueue(MineFragment.this.getActivity(), Url.LoadingUrl, params, UserJson.class,
                new OkhttpJsonUtil.TextCallBack<UserJson>() {
                    @Override
                    public void getResult(UserJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);/PhoneNum=13764929873
                        if (result != null) {
                            MyApplication.user = result.getUserInfo()==null?new UserJson.UserInfoBean():result.getUserInfo();
                            String userName=MyApplication.user.getUi_NickName()==null?"":MyApplication.user.getUi_NickName();
                            name.setText(userName);
                            String url=MyApplication.user.getUi_Headimg()==null|MyApplication.user.getUi_Headimg()==""?"wwww":MyApplication.user.getUi_Headimg();
                            url=url.replace(" ","");
                            if(TextUtils.isEmpty(url)){
                                url="www";
                            }
                            Picasso.with(MineFragment.this.getContext()).load(url).into(headImg, new Callback() {
                                @Override
                                public void onSuccess() {

                                }
                                @Override
                                public void onError() {
                                    headImg.setImageResource(R.mipmap.default_head);
//MyApplication.showToast("头像加载失败",0);
                                }
                            });
                        }
                    }
        }
        );
    }
}
