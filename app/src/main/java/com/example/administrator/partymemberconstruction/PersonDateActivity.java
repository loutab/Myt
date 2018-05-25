package com.example.administrator.partymemberconstruction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.partymemberconstruction.Bean.ContactsPersonBean;
import com.example.administrator.partymemberconstruction.Bean.GroupJson;
import com.example.administrator.partymemberconstruction.Bean.PartMJson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonDateActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.link)
    ImageView link;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.brief)
    TextView brief;
    @BindView(R.id.org)
    TextView org;
    @BindView(R.id.part)
    TextView part;
    @BindView(R.id.work)
    TextView work;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.address)
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_date_layout);
        ButterKnife.bind(this);
        link.setClickable(true);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ContactsPersonBean.UserinfoBean b = MyApplication.otherBean;
        name.setText(b.getNickName()+"");
        sex.setText(b.getSex()==0?"男":"女");
        date.setText((b.getBirthday()+"").split(" ")[0]);
        brief.setText(b.getUi_Introduction()+"");
        org.setText("");
        for(GroupJson.TissueTreeBean bean:MyApplication.GroupDate){
            if(bean.getId()==b.getUi_Organization());
                  org.setText(bean.getOrganizationName());
        }
        part.setText("");
        for (PartMJson.DepartListBean bean:MyApplication.PartDate){
            if (b.getUi_Department()==bean.getId())
                part.setText(""+bean.getDepartName());
        }
        //part.setText(""+b.getUi_Department());
        email.setText(""+b.getMail());
        phone.setText(""+b.getPhoneNum());
        address.setText(""+b.getAddress());
        work.setText(b.getUi_Position()==0?"普通党员":"管理员");

    }
}
