package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/4/26/026.
 */

public class ContactsSort {
    ContactsJson.UserInfoListBean bean;
    boolean show;

    public ContactsJson.UserInfoListBean getBean() {
        return bean;
    }

    public void setBean(ContactsJson.UserInfoListBean bean) {
        this.bean = bean;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public ContactsSort(ContactsJson.UserInfoListBean bean, boolean show) {
        this.bean = bean;
        this.show = show;
    }
}
