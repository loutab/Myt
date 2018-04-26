package com.example.administrator.partymemberconstruction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Adapter.ContactsAdapter;
import com.example.administrator.partymemberconstruction.Bean.ContactsJson;
import com.example.administrator.partymemberconstruction.utils.ChineseToEnglish;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.list)
    ListView list;
    private List<targe> newList;
    private List<ContactsJson.UserInfoListBean> user_infoList;
    private ContactsAdapter contactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getContacts();
        newList=new ArrayList<>();
        user_infoList=new ArrayList<>();
        contactsAdapter = new ContactsAdapter(user_infoList, newList, this);
        list.setAdapter(contactsAdapter);
        //testPin();
    }

    private void testPin() {
        List<test> list = new ArrayList();
        list.add(new test("刘军", 1));
        list.add(new test("刘先生", 2));
        list.add(new test("刘额", 2));
        list.add(new test("刘他", 2));
        list.add(new test("刘军", 2));
        list.add(new test("胡啊", 2));
        list.add(new test("胡是", 2));
        list.add(new test("胡了", 2));
        list.add(new test("胡人", 2));
        list.add(new test("湖说", 2));
        list.add(new test("雨是", 2));
        list.add(new test("与他", 2));
        List<targe> newList = new ArrayList<>();
        Collections.sort(list, new Comparator<test>() {
            @Override
            public int compare(test test, test t1) {
                return ChineseToEnglish.getPinYinHeadChar(test.getName()).compareTo(ChineseToEnglish.getPinYinHeadChar(t1.getName()));
            }
        });
        String letters = "" + ChineseToEnglish.getPinYinHeadChar(list.get(0).getName()).charAt(0);
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();

            Log.e("1", name + "名字" + ChineseToEnglish.getCnASCII(name));
            String pinYinHeadChar = ChineseToEnglish.getPinYinHeadChar(name);
            if (!letters.equals(pinYinHeadChar.charAt(0) + "")) {
                newList.add(new targe(i, pinYinHeadChar.charAt(0) + ""));
                Log.e("2", i + "位置" + pinYinHeadChar.charAt(0) + "");
            }
            letters = pinYinHeadChar.charAt(0) + "";
        }
    }

    public class targe {
        int position;
        String letters;

        public int getPosition() {
            return position;
        }

        public String getLetters() {
            return letters;
        }

        public targe(int position, String letters) {
            this.position = position;
            this.letters = letters;
        }
    }

    class test {
        private String name;
        private int i;

        public test(String name, int i) {
            this.name = name;
            this.i = i;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    private void getContacts() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", MyApplication.user.getUser_ID() + "");
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.GetContact, params, ContactsJson.class,
                new OkhttpJsonUtil.TextCallBack<ContactsJson>() {
                    @Override
                    public void getResult(ContactsJson result) {
                        if (result.getCode().equals("成功")) {
                            user_infoList = result.getUser_InfoList();
                            //为集合排序
                            Collections.sort(user_infoList, new Comparator<ContactsJson.UserInfoListBean>() {
                                @Override
                                public int compare(ContactsJson.UserInfoListBean t, ContactsJson.UserInfoListBean t1) {
                                    return ChineseToEnglish.getPinYinHeadChar(t.getName()).compareTo(ChineseToEnglish.getPinYinHeadChar(t1.getName()));
                                }
                            });
                            newList = new ArrayList<>();
                            String letters = "" + ChineseToEnglish.getPinYinHeadChar(user_infoList.get(0).getName()).charAt(0);
                            for (int i = 0; i < user_infoList.size(); i++) {
                                String name = user_infoList.get(i).getName();

                                Log.e("1", name + "名字" + ChineseToEnglish.getCnASCII(name));
                                String pinYinHeadChar = ChineseToEnglish.getPinYinHeadChar(name);
                                if (!letters.equals(pinYinHeadChar.charAt(0) + "")) {
                                    newList.add(new targe(i, pinYinHeadChar.charAt(0) + ""));
                                    Log.e("2", i + "位置" + pinYinHeadChar.charAt(0) + "");
                                }
                                letters = pinYinHeadChar.charAt(0) + "";
                            }
                            contactsAdapter = new ContactsAdapter(user_infoList, newList, ContactsActivity.this);
                            list.setAdapter(contactsAdapter);
                        }else {
                            MyApplication.showToast("暂无联系人",0);

                        }
                    }
                });
    }

}
