package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class SelfInfo {

    /**
     * Code : 成功
     * Menu_List : [{"Menu_Logo_Url":null,"Menu_Name":"我的收藏","Menu_Url":"http://101.201.109.90:3335/H5Content/MyCenter/Index","Menu_Region":"个人中心"},{"Menu_Logo_Url":null,"Menu_Name":"个人资料","Menu_Url":"http://101.201.109.90:3335/H5Content/UserInfo1/Index_Inser","Menu_Region":"个人中心"},{"Menu_Logo_Url":null,"Menu_Name":"积分","Menu_Url":"http://101.201.109.90:3335/H5Content/UserInfo1/Integralinser","Menu_Region":"个人中心"}]
     */

    private String Code;
    private List<MenuListBean> Menu_List;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<MenuListBean> getMenu_List() {
        return Menu_List;
    }

    public void setMenu_List(List<MenuListBean> Menu_List) {
        this.Menu_List = Menu_List;
    }

    public static class MenuListBean {
        /**
         * Menu_Logo_Url : null
         * Menu_Name : 我的收藏
         * Menu_Url : http://101.201.109.90:3335/H5Content/MyCenter/Index
         * Menu_Region : 个人中心
         */

        private Object Menu_Logo_Url;
        private String Menu_Name;
        private String Menu_Url;
        private String Menu_Region;

        public Object getMenu_Logo_Url() {
            return Menu_Logo_Url;
        }

        public void setMenu_Logo_Url(Object Menu_Logo_Url) {
            this.Menu_Logo_Url = Menu_Logo_Url;
        }

        public String getMenu_Name() {
            return Menu_Name;
        }

        public void setMenu_Name(String Menu_Name) {
            this.Menu_Name = Menu_Name;
        }

        public String getMenu_Url() {
            return Menu_Url;
        }

        public void setMenu_Url(String Menu_Url) {
            this.Menu_Url = Menu_Url;
        }

        public String getMenu_Region() {
            return Menu_Region;
        }

        public void setMenu_Region(String Menu_Region) {
            this.Menu_Region = Menu_Region;
        }
    }
}
