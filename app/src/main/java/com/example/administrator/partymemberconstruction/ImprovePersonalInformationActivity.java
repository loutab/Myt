package com.example.administrator.partymemberconstruction;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.administrator.partymemberconstruction.Adapter.Improve1Adapter;
import com.example.administrator.partymemberconstruction.Adapter.ImproveAdapter;
import com.example.administrator.partymemberconstruction.Bean.CompleteJson;
import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.Bean.PartJson;
import com.example.administrator.partymemberconstruction.Bean.PartMJson;
import com.example.administrator.partymemberconstruction.Bean.UserInfo;
import com.example.administrator.partymemberconstruction.utils.ComenUtils;
import com.example.administrator.partymemberconstruction.utils.OkhttpJsonUtil;
import com.example.administrator.partymemberconstruction.utils.Url;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImprovePersonalInformationActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.man)
    RadioButton man;
    @BindView(R.id.woman)
    RadioButton woman;
    @BindView(R.id.sex)
    RadioGroup sex;
    @BindView(R.id.group)
    TextView group;
    @BindView(R.id.part)
    TextView part;
    @BindView(R.id.controller)
    RadioButton controller;
    @BindView(R.id.member)
    RadioButton member;
    @BindView(R.id.organization)
    RadioGroup organization;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.stringaddress)
    TextView stringaddress;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.stringbrief)
    TextView stringbrief;
    @BindView(R.id.brief)
    EditText brief;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.firstGroup)
    LinearLayout firstGroup;
    @BindView(R.id.secondGroup)
    LinearLayout secondGroup;

    private TimePickerView pvTime;
    private List<GroupJson.TissueTreeBean> tissue_tree;
    private View groupDateView;
    private View partDateView;
    private PopupWindow popupWindowGroup;
    private List<PartJson.TissueTreeBean> tissue_tree1;
    private int sexDate;
    private int orgDate;
    private String userId;
    private int id1;
    private int id2;
    private List<PartMJson.DepartListBean> departList;
    private String standerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_personal_information);
        ButterKnife.bind(this);
        rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImprovePersonalInformationActivity.this, OldWebActivity.class);
                startActivity(intent);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获得UserId
        userId = getIntent().getStringExtra("userId");
        // userId=17+"";
        //获得时间
        getTimeK();

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupDateView = v;
                //获得组织与部门数据
                getGroupDate(true);
            }
        });
        part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partDateView = v;
                //获得组织与部门数据
                getPartDate(true);
            }
        });
        //默认为男
        sexDate = 0;
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.man:
                        sexDate = 0;
                        break;
                    case R.id.woman:
                        sexDate = 1;
                        break;
                }
            }
        });
        //默认管理者
        orgDate = 1;
        organization.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.controller:
                        orgDate = 1;
                        break;
                    case R.id.member:
                        orgDate = 0;
                        break;
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取所有信息
                String userName = name.getText() + "";
                String birthdayString = standerTime + "";
                String groupDate = group.getText() + "";
                String partDate = part.getText() + "";
                String emailDate = email.getText() + "";
                //非必填
                String addressDate = address.getText() + "";
                String briefDate = brief.getText() + "";
                //验证必填数据是否为空
//                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(birthdayString) || TextUtils.isEmpty(groupDate) || TextUtils.isEmpty(partDate) || TextUtils.isEmpty(emailDate)) {
//                    MyApplication.showToast("必填数据不能为空", 0);
                if (TextUtils.isEmpty(userName)) {
                    MyApplication.showToast("名字不能为空", 0);
                } else if (TextUtils.isEmpty(groupDate)) {
                    MyApplication.showToast("所属组织不能为空", 0);
                } else if (TextUtils.isEmpty(partDate)) {
                    MyApplication.showToast("部门不能为空", 0);
                } else {
                    //调用完善接口
                    if (!TextUtils.isEmpty(emailDate)) {
                        if (!ComenUtils.isEmail(emailDate))
                            MyApplication.showToast("请输入正确邮箱", 0);
                        else
                            completeDate(userName, birthdayString, groupDate, partDate, emailDate, addressDate, briefDate);
                    } else
                        completeDate(userName, birthdayString, groupDate, partDate, emailDate, addressDate, briefDate);
                }
            }
        });
        String isRestart = getIntent().getStringExtra("isRestart");

        //重新申请页面
        if (!TextUtils.isEmpty(isRestart)) {
            getOldDate();
        }

    }

    int oldOrg;
    int oldPart;

    private void getOldDate() {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.GetOldDateUrl, params, UserInfo.class,
                new OkhttpJsonUtil.TextCallBack<UserInfo>() {
                    @Override
                    public void getResult(UserInfo result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                if (result.getUserInfo() != null) {
                                    name.setText(result.getUserInfo().getUi_NickName() == null ? "" : result.getUserInfo().getUi_NickName());
                                    if (!TextUtils.isEmpty(result.getUserInfo().getUi_Birthday())) {
                                        String[] split = result.getUserInfo().getUi_Birthday().split(" ");
                                        String[] birth = split[0].split("-");
                                        if (birth.length >= 2) {
                                            birthday.setText(birth[0] + "      " + birth[1] + "      " + birth[2]);
                                            standerTime = birth[0] + "-" + birth[1] + "-" + birth[2];
                                        }
                                    }
                                    if (result.getUserInfo().getSex() == 0)
                                        sex.check(R.id.man);
                                    else
                                        sex.check(R.id.woman);

                                    oldOrg = result.getUserInfo().getUi_Organization();
                                    oldPart = result.getUserInfo().getUi_Department();
                                    getGroupDate(false);
                                    getPartDate(false);
                                    if (result.getUserInfo().getUi_Position() == 1)
                                        organization.check(R.id.controller);
                                    else
                                        organization.check(R.id.member);
                                    if (result.getUserInfo().getUi_Mail() != null)
                                        email.setText(result.getUserInfo().getUi_Mail());
                                    if (result.getUserInfo().getUi_Address() != null)
                                        address.setText(result.getUserInfo().getUi_Address());
                                    if (result.getUserInfo().getUi_Introduction() != null)
                                        brief.setText(result.getUserInfo().getUi_Introduction());
                                }
                            } else {
                                MyApplication.showToast(result.getError() == null ? "" : result.getError(), 0);
                            }
                        } else {
                            MyApplication.showToast("服务器连接失败", 0);
                        }

                    }
                });
    }

    private void completeDate(String userName, String birthdayString, String groupDate, String partDate, String emailDate, String addressDate, String briefDate) {
        HashMap<String, String> params = new HashMap<>();
        params.put("User_ID", "" + userId);
        params.put("ui_Organization", "" + id1);
        params.put("ui_Department", "" + id2);
        params.put("NickName", "" + userName);
        params.put("Sex", "" + sexDate);
        params.put("ui_Introduction", "" + (TextUtils.isEmpty(briefDate)?"":briefDate));
        params.put("Mail", "" +(TextUtils.isEmpty(emailDate)?"":emailDate) );
        params.put("Address", "" + (TextUtils.isEmpty(addressDate)?"":addressDate));
        params.put("Birthday", "" + birthdayString);
        params.put("ui_Headimg", " ");
        params.put("ui_Position", "" + orgDate);
        for(String s:params.keySet()){
            Log.e("params",s+"   "+params.get(s));
        }
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.CompleteDateUrl, params, CompleteJson.class,
                new OkhttpJsonUtil.TextCallBack<CompleteJson>() {
                    @Override
                    public void getResult(CompleteJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                //跳转页面
                                Intent intent = new Intent(ImprovePersonalInformationActivity.this, ExamineActivity.class);
                                startActivity(intent);
                                finish();
                                MyApplication.showToast("成功", 0);
                            } else {
//                                Intent intent = new Intent(ImprovePersonalInformationActivity.this, ExamineActivity.class);
//                                startActivity(intent);
                                //finish();
                                MyApplication.showToast(result.getException(), 0);
                            }
                        } else {
                            MyApplication.showToast("服务器处理失败", 0);
                        }

                    }
                });
    }

    private void getPartDate(boolean isShow) {
        HashMap<String, String> params = new HashMap<>();
        isRealShowTwo = isShow;
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.PartDateUrl, params, PartMJson.class,
                new OkhttpJsonUtil.TextCallBack<PartMJson>() {
                    @Override
                    public void getResult(PartMJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                departList = result.getDepartList();
                                if (isRealShowTwo)
                                    showPopupWindowPart(secondGroup);
                                else {
                                    if (departList != null) {
                                        if (departList.size() > 0) {
                                            for (PartMJson.DepartListBean bean : departList) {
                                                if (bean.getId() == oldPart) {
                                                    part.setText(bean.getDepartName());
                                                    id2=oldPart;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                });
    }

    boolean isRealShowOne;
    boolean isRealShowTwo;

    private void getGroupDate(boolean isShow) {
        HashMap<String, String> params = new HashMap<>();
        isRealShowOne = isShow;
        OkhttpJsonUtil.getInstance().postByEnqueue(this, Url.GroupDateUrl, params, GroupJson.class,
                new OkhttpJsonUtil.TextCallBack<GroupJson>() {
                    @Override
                    public void getResult(GroupJson result) {
                        // MyApplication.showToast(result.getCode()+"",0);
                        if (result != null) {
                            if (result.getCode().equals("成功")) {
                                tissue_tree = result.getTissue_tree();
                                if (isRealShowOne)
                                    showPopupWindowGroup(firstGroup);
                                else {
                                    if (tissue_tree != null) {
                                        for (GroupJson.TissueTreeBean bean : tissue_tree) {
                                            if (bean.getId() == oldOrg) {
                                                group.setText(bean.getOrganizationName());
                                                id1=oldOrg;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                });
    }

    private void getTimeK() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        int year = curDate.getYear() + 1900;
        int month = curDate.getMonth() + 1;
        birthday.setText(year + "      " + month + "      " + curDate.getDate());
        standerTime = year + "-" + month + "-" + curDate.getDate();
        timeCustom();
        //生日选择
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }

    public void showPopupWindowGroup(View v) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popuwindow_layout, null);
        ListView listView = contentView.findViewById(R.id.list);
        ImproveAdapter group = new ImproveAdapter(this, tissue_tree);
        listView.setAdapter(group);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupJson.TissueTreeBean tissueTreeBean = tissue_tree.get(position);
                String organizationName = tissueTreeBean.getOrganizationName();
                ImprovePersonalInformationActivity.this.group.setText(organizationName);
                id1 = tissueTreeBean.getId();
                popupWindowGroup.dismiss();
            }
        });
        popupWindowGroup = new PopupWindow(contentView,
                this.group.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindowGroup.setTouchable(true);

//        popupWindowGroup.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                Log.i("mengdd", "onTouch : ");
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        ColorDrawable cd = new ColorDrawable(0x00ffffff);// 背景颜色全透明
        popupWindowGroup.setBackgroundDrawable(cd);
        // 设置好参数之后再show
        popupWindowGroup.showAsDropDown(v);
    }

    public void showPopupWindowPart(View v) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popuwindow_layout, null);
        ListView listView = contentView.findViewById(R.id.list);
        Improve1Adapter group = new Improve1Adapter(this, departList);
        listView.setAdapter(group);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartMJson.DepartListBean departListBean = departList.get(position);
                String organizationName = departListBean.getDepartName();
                ImprovePersonalInformationActivity.this.part.setText(organizationName);
                id2 = departListBean.getId();
                popupWindowGroup.dismiss();
            }
        });
        popupWindowGroup = new PopupWindow(contentView,
                this.part.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindowGroup.setTouchable(true);

//        popupWindowGroup.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                Log.i("mengdd", "onTouch : ");
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        ColorDrawable cd = new ColorDrawable(0x00ffffff);// 背景颜色全透明
        popupWindowGroup.setBackgroundDrawable(cd);
        // 设置好参数之后再show
        popupWindowGroup.showAsDropDown(v);
    }

    private String str;

    private void timeCustom() {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarStart.set(1800, calendarEnd.get(Calendar.MONTH) + 1, calendarEnd.get(Calendar.DAY_OF_MONTH));
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                int year = date.getYear() + 1900;
                int monthOfYear = date.getMonth();
                int day = date.getDate();
                int dayOfMonth = 20;
                //  birthday.setText(date.getYear()+1900+"-"+(date.getMonth()+1)+"-"+ day);
                str = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                standerTime = year + "-" + (monthOfYear + 1) + "-" + day;
                birthday.setText(year + "      " + (monthOfYear + 1) + "      " + day);

            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                .setRangDate(calendarStart, calendarEnd)
                .isCyclic(false)
                .build();
        pvTime.setDate(Calendar.getInstance());
    }

}
