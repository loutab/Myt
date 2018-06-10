package com.example.administrator.partymemberconstruction.Bean;

/**
 * Created by Administrator on 2018/3/23.
 */

public class UserJson {

    /**
     * Code : 成功
     * Exception : sdasd
     * Success : 成功
     * status : 1
     * userInfo : {"EntityId":1,"User_ID":1,"ui_NickName":"小红","ui_Introduction":"无简介","ui_Level":1,"ui_Integral":0,"ui_Headimg":"/Upload/20180321_556.jpg","ui_Organization":1,"ui_Department":1,"ui_Position":1,"Sex":0,"ui_Mail":"","ui_Address":"","ui_Birthday":"0001-01-01 00:00:00","ui_FirstLetter":"X"}
     */


    private String Code;
    private String Exception;
    private String Success;
    private int status;
    private UserInfoBean userInfo;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    private String Error;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    private int User_id;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String Exception) {
        this.Exception = Exception;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * EntityId : 1
         * User_ID : 1
         * ui_NickName : 小红
         * ui_Introduction : 无简介
         * ui_Level : 1
         * ui_Integral : 0
         * ui_Headimg : /Upload/20180321_556.jpg
         * ui_Organization : 1
         * ui_Department : 1
         * ui_Position : 1
         * Sex : 0
         * ui_Mail :
         * ui_Address :
         * ui_Birthday : 0001-01-01 00:00:00
         * ui_FirstLetter : X
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
    }
}
