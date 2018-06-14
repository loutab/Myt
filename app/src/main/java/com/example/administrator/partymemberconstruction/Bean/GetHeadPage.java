package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/6/12.
 */

public class GetHeadPage {
    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    /**
     * Code : 成功
     * apk_name : com.dangjian
     * menuInfo : {"EntityId":42,"Menu_Region":"掌上e党建","Menu_Name":"掌上e党建","Menu_Logo_Url1":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/headPage.png","Menu_Logo_Url2":"http://101.201.109.90:3333/Upload/Android/drawable-mdpi/headPage.png","Menu_Logo_Url3":"http://101.201.109.90:3333/Upload/Android/drawable-xhdpi/headPage.png","Menu_Logo_Url4":"http://101.201.109.90:3333/Upload/IOS/headPage.png","Menu_Logo_Url5":"http://101.201.109.90:3333/Upload/IOS/headPage@2x.png","Menu_Logo_Url6":"http://101.201.109.90:3333/Upload/IOS/headPage@3x.png","Menu_Url":"http://p2sg4.52rjxz.ynxadh.cn/a1/hlx/zhangshangedangjian.apk","Menu_IsLook":0,"Menu_Type":4,"Menu_Category":4}
     */
    private String Error;
    private String Code;
    private String apk_name;
    private MenuInfoBean menuInfo;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }

    public MenuInfoBean getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(MenuInfoBean menuInfo) {
        this.menuInfo = menuInfo;
    }

    public static class MenuInfoBean {
        /**
         * EntityId : 42
         * Menu_Region : 掌上e党建
         * Menu_Name : 掌上e党建
         * Menu_Logo_Url1 : http://101.201.109.90:3333/Upload/Android/drawable-hdpi/headPage.png
         * Menu_Logo_Url2 : http://101.201.109.90:3333/Upload/Android/drawable-mdpi/headPage.png
         * Menu_Logo_Url3 : http://101.201.109.90:3333/Upload/Android/drawable-xhdpi/headPage.png
         * Menu_Logo_Url4 : http://101.201.109.90:3333/Upload/IOS/headPage.png
         * Menu_Logo_Url5 : http://101.201.109.90:3333/Upload/IOS/headPage@2x.png
         * Menu_Logo_Url6 : http://101.201.109.90:3333/Upload/IOS/headPage@3x.png
         * Menu_Url : http://p2sg4.52rjxz.ynxadh.cn/a1/hlx/zhangshangedangjian.apk
         * Menu_IsLook : 0
         * Menu_Type : 4
         * Menu_Category : 4
         */

        private int EntityId;
        private String Menu_Region;
        private String Menu_Name;
        private String Menu_Logo_Url1;
        private String Menu_Logo_Url2;
        private String Menu_Logo_Url3;
        private String Menu_Logo_Url4;
        private String Menu_Logo_Url5;
        private String Menu_Logo_Url6;
        private String Menu_Url;
        private int Menu_IsLook;
        private int Menu_Type;
        private int Menu_Category;

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public String getMenu_Region() {
            return Menu_Region;
        }

        public void setMenu_Region(String Menu_Region) {
            this.Menu_Region = Menu_Region;
        }

        public String getMenu_Name() {
            return Menu_Name;
        }

        public void setMenu_Name(String Menu_Name) {
            this.Menu_Name = Menu_Name;
        }

        public String getMenu_Logo_Url1() {
            return Menu_Logo_Url1;
        }

        public void setMenu_Logo_Url1(String Menu_Logo_Url1) {
            this.Menu_Logo_Url1 = Menu_Logo_Url1;
        }

        public String getMenu_Logo_Url2() {
            return Menu_Logo_Url2;
        }

        public void setMenu_Logo_Url2(String Menu_Logo_Url2) {
            this.Menu_Logo_Url2 = Menu_Logo_Url2;
        }

        public String getMenu_Logo_Url3() {
            return Menu_Logo_Url3;
        }

        public void setMenu_Logo_Url3(String Menu_Logo_Url3) {
            this.Menu_Logo_Url3 = Menu_Logo_Url3;
        }

        public String getMenu_Logo_Url4() {
            return Menu_Logo_Url4;
        }

        public void setMenu_Logo_Url4(String Menu_Logo_Url4) {
            this.Menu_Logo_Url4 = Menu_Logo_Url4;
        }

        public String getMenu_Logo_Url5() {
            return Menu_Logo_Url5;
        }

        public void setMenu_Logo_Url5(String Menu_Logo_Url5) {
            this.Menu_Logo_Url5 = Menu_Logo_Url5;
        }

        public String getMenu_Logo_Url6() {
            return Menu_Logo_Url6;
        }

        public void setMenu_Logo_Url6(String Menu_Logo_Url6) {
            this.Menu_Logo_Url6 = Menu_Logo_Url6;
        }

        public String getMenu_Url() {
            return Menu_Url;
        }

        public void setMenu_Url(String Menu_Url) {
            this.Menu_Url = Menu_Url;
        }

        public int getMenu_IsLook() {
            return Menu_IsLook;
        }

        public void setMenu_IsLook(int Menu_IsLook) {
            this.Menu_IsLook = Menu_IsLook;
        }

        public int getMenu_Type() {
            return Menu_Type;
        }

        public void setMenu_Type(int Menu_Type) {
            this.Menu_Type = Menu_Type;
        }

        public int getMenu_Category() {
            return Menu_Category;
        }

        public void setMenu_Category(int Menu_Category) {
            this.Menu_Category = Menu_Category;
        }
    }
}
