package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class ContactsJson {

    /**
     * Code : 成功
     * user_InfoList : [{"Name":"小红","UserID":1,"PhoneNum":"13261011499","HeadImg":"/Upload/20180321_556.jpg","Mail":""}]
     * userCount : 1
     * Error : 数据库获取数据为空
     */

    private String Code;
    private int userCount;
    private String Error;
    private List<UserInfoListBean> user_InfoList;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }

    public List<UserInfoListBean> getUser_InfoList() {
        return user_InfoList;
    }

    public void setUser_InfoList(List<UserInfoListBean> user_InfoList) {
        this.user_InfoList = user_InfoList;
    }

    public static class UserInfoListBean {
        /**
         * Name : 小红
         * UserID : 1
         * PhoneNum : 13261011499
         * HeadImg : /Upload/20180321_556.jpg
         * Mail :
         */

        private String Name;
        private int UserID;
        private String PhoneNum;
        private String HeadImg;
        private String Mail;

        public UserInfoListBean(String name) {
            Name = name;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getPhoneNum() {
            return PhoneNum;
        }

        public void setPhoneNum(String PhoneNum) {
            this.PhoneNum = PhoneNum;
        }

        public String getHeadImg() {
            return HeadImg;
        }

        public void setHeadImg(String HeadImg) {
            this.HeadImg = HeadImg;
        }

        public String getMail() {
            return Mail;
        }

        public void setMail(String Mail) {
            this.Mail = Mail;
        }
    }
}
