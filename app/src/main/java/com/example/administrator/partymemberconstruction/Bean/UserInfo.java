package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/5/23.
 */

public class UserInfo {

    /**
     * code : 成功
     * Error : 该用户未完善信息
     * userInfo : {"EntityId":3,"User_ID":1,"ui_NickName":"运维管理员","ui_Introduction":"我就想看看能输入多少我就想看看能输入多少我就想看看能k输入少我就想看看能输入多少我就想看看能输入多少我就想看看能输入多少我就想看看能输入多少我就想看看能输入多少我就想看看能输入jfffffffffff","ui_Level":1,"ui_Integral":27,"ui_Headimg":"http://101.201.109.90:3333/Upload/Head/20180518_677246.jpg","ui_Organization":12,"ui_Department":1,"ui_Position":1,"Sex":1,"ui_Mail":"1058855858@qq.com","ui_Address":"hzhdjfjcjcjfjfjfjfjfj   ","ui_Birthday":"0001-01-01 00:00:00","ui_FirstLetter":"Y","ui_Nation":"汉","ui_BirthdayPlace":"北京","ui_DegreeofEducation":7,"ui_ReadTime":8,"ui_ReadCount":0,"ui_categoryId":7}
     */

    private String code;
    private String Error;
    private UserInfoBean userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * EntityId : 3
         * User_ID : 1
         * ui_NickName : 运维管理员
         * ui_Introduction : 我就想看看能输入多少我就想看看能输入多少我就想看看能k输入少我就想看看能输入多少我就想看看能输入多少我就想看看能输入多少我就想看看能输入多少我就想看看能输入多少我就想看看能输入jfffffffffff
         * ui_Level : 1
         * ui_Integral : 27
         * ui_Headimg : http://101.201.109.90:3333/Upload/Head/20180518_677246.jpg
         * ui_Organization : 12
         * ui_Department : 1
         * ui_Position : 1
         * Sex : 1
         * ui_Mail : 1058855858@qq.com
         * ui_Address : hzhdjfjcjcjfjfjfjfjfj
         * ui_Birthday : 0001-01-01 00:00:00
         * ui_FirstLetter : Y
         * ui_Nation : 汉
         * ui_BirthdayPlace : 北京
         * ui_DegreeofEducation : 7
         * ui_ReadTime : 8
         * ui_ReadCount : 0
         * ui_categoryId : 7
         */

        private int EntityId;
        private int User_ID;
        private String ui_NickName;
        private String ui_Introduction;
        private int ui_Level;
        private int ui_Integral;
        private String ui_Headimg;
        private int ui_Organization;
        private int ui_Department;
        private int ui_Position;
        private int Sex;
        private String ui_Mail;
        private String ui_Address;
        private String ui_Birthday;
        private String ui_FirstLetter;
        private String ui_Nation;
        private String ui_BirthdayPlace;
        private int ui_DegreeofEducation;
        private int ui_ReadTime;
        private int ui_ReadCount;
        private int ui_categoryId;

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public int getUser_ID() {
            return User_ID;
        }

        public void setUser_ID(int User_ID) {
            this.User_ID = User_ID;
        }

        public String getUi_NickName() {
            return ui_NickName;
        }

        public void setUi_NickName(String ui_NickName) {
            this.ui_NickName = ui_NickName;
        }

        public String getUi_Introduction() {
            return ui_Introduction;
        }

        public void setUi_Introduction(String ui_Introduction) {
            this.ui_Introduction = ui_Introduction;
        }

        public int getUi_Level() {
            return ui_Level;
        }

        public void setUi_Level(int ui_Level) {
            this.ui_Level = ui_Level;
        }

        public int getUi_Integral() {
            return ui_Integral;
        }

        public void setUi_Integral(int ui_Integral) {
            this.ui_Integral = ui_Integral;
        }

        public String getUi_Headimg() {
            return ui_Headimg;
        }

        public void setUi_Headimg(String ui_Headimg) {
            this.ui_Headimg = ui_Headimg;
        }

        public int getUi_Organization() {
            return ui_Organization;
        }

        public void setUi_Organization(int ui_Organization) {
            this.ui_Organization = ui_Organization;
        }

        public int getUi_Department() {
            return ui_Department;
        }

        public void setUi_Department(int ui_Department) {
            this.ui_Department = ui_Department;
        }

        public int getUi_Position() {
            return ui_Position;
        }

        public void setUi_Position(int ui_Position) {
            this.ui_Position = ui_Position;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public String getUi_Mail() {
            return ui_Mail;
        }

        public void setUi_Mail(String ui_Mail) {
            this.ui_Mail = ui_Mail;
        }

        public String getUi_Address() {
            return ui_Address;
        }

        public void setUi_Address(String ui_Address) {
            this.ui_Address = ui_Address;
        }

        public String getUi_Birthday() {
            return ui_Birthday;
        }

        public void setUi_Birthday(String ui_Birthday) {
            this.ui_Birthday = ui_Birthday;
        }

        public String getUi_FirstLetter() {
            return ui_FirstLetter;
        }

        public void setUi_FirstLetter(String ui_FirstLetter) {
            this.ui_FirstLetter = ui_FirstLetter;
        }

        public String getUi_Nation() {
            return ui_Nation;
        }

        public void setUi_Nation(String ui_Nation) {
            this.ui_Nation = ui_Nation;
        }

        public String getUi_BirthdayPlace() {
            return ui_BirthdayPlace;
        }

        public void setUi_BirthdayPlace(String ui_BirthdayPlace) {
            this.ui_BirthdayPlace = ui_BirthdayPlace;
        }

        public int getUi_DegreeofEducation() {
            return ui_DegreeofEducation;
        }

        public void setUi_DegreeofEducation(int ui_DegreeofEducation) {
            this.ui_DegreeofEducation = ui_DegreeofEducation;
        }

        public int getUi_ReadTime() {
            return ui_ReadTime;
        }

        public void setUi_ReadTime(int ui_ReadTime) {
            this.ui_ReadTime = ui_ReadTime;
        }

        public int getUi_ReadCount() {
            return ui_ReadCount;
        }

        public void setUi_ReadCount(int ui_ReadCount) {
            this.ui_ReadCount = ui_ReadCount;
        }

        public int getUi_categoryId() {
            return ui_categoryId;
        }

        public void setUi_categoryId(int ui_categoryId) {
            this.ui_categoryId = ui_categoryId;
        }
    }
}
