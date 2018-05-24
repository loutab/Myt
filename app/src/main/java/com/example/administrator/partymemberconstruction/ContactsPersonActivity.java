package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.ContactsAdapter;
import com.example.administrator.partymemberconstruction.Adapter.ContactsPersonAdapter;
import com.example.administrator.partymemberconstruction.Bean.ContactsPersonBean;
import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.CustomView.CircleImageView;
import com.example.administrator.partymemberconstruction.fragment.MineFragment;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;
import com.squareup.picasso.Picasso;

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
    private String userId;
    private List<ContactsPersonBean.UserListBean> userList;
    private ContactsPersonAdapter contactsPersonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_person);
        ButterKnife.bind(this);
        userId = getIntent().getStringExtra("userId");
        if (TextUtils.isEmpty(userId)) {
            MyApplication.showToast("用户ID不存在", 0);
        } else {
            userList = new ArrayList<>();
            getDate();
            contactsPersonAdapter = new ContactsPersonAdapter(userList, this);
            list.setAdapter(contactsPersonAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String ai_jumpLink = userList.get(i).getAi_JumpLink();
                    Intent intent=new Intent(ContactsPersonActivity.this, WebActivity.class);
                    intent.putExtra("Url",ai_jumpLink+"");
                    startActivity(intent);
                }
            });
            headImg.setClickable(true);
            headImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ContactsPersonActivity.this,PersonDateActivity.class);
                    startActivity(intent);
                }
            });
        }
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
                                    MyApplication.otherBean=result.getUserinfo();
                                    String nickName = result.getUserinfo().getNickName();
                                    name.setText(nickName == null ? "" : nickName);
                                    int sexInt = result.getUserinfo().getSex();
                                    if (sexInt == 0) {
                                        sex.setImageResource(R.mipmap.personman);
                                    } else {
                                        sex.setImageResource(R.mipmap.personwoman);
                                    }
                                    Picasso.with(ContactsPersonActivity.this).load(result.getUserinfo().getUi_Headimg() == null ? "" :
                                            result.getUserinfo().getUi_Headimg()).error(R.mipmap.default_head).into(headImg);
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
}
