package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24/024.
 */

public class NoticeItemJson {

    /**
     * Code : 成功
     * NoticeInfo : [{"EntityId":8,"User_id":1,"n_Type":0,"n_SendTime":"2018-03-13 16:45:07","n_Title":"通知8","n_Cover_imgUrl":"hdpi/calendar.png","n_Text":"hdpi/calendar.png","n_ArticleId":1,"n_IsNew":1,"n_Reply_Result":1,"userName":"小红帽"},{"EntityId":7,"User_id":1,"n_Type":0,"n_SendTime":"2018-03-06 16:44:32","n_Title":"通知7","n_Cover_imgUrl":"hdpi/calendar.png","n_Text":"hdpi/calendar.png","n_ArticleId":1,"n_IsNew":1,"n_Reply_Result":1,"userName":"小红帽"}]
     */

    private String Code;
    private List<NoticeInfoBean> NoticeInfo;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<NoticeInfoBean> getNoticeInfo() {
        return NoticeInfo;
    }

    public void setNoticeInfo(List<NoticeInfoBean> NoticeInfo) {
        this.NoticeInfo = NoticeInfo;
    }

    public static class NoticeInfoBean {
        /**
         * EntityId : 8
         * User_id : 1
         * n_Type : 0
         * n_SendTime : 2018-03-13 16:45:07
         * n_Title : 通知8
         * n_Cover_imgUrl : hdpi/calendar.png
         * n_Text : hdpi/calendar.png
         * n_ArticleId : 1
         * n_IsNew : 1
         * n_Reply_Result : 1
         * userName : 小红帽
         */

        private int EntityId;
        private int User_id;
        private int n_Type;
        private String n_SendTime;
        private String n_Title;
        private String n_Cover_imgUrl;
        private String n_Text;
        private int n_ArticleId;
        private int n_IsNew;
        private int n_Reply_Result;
        private String userName;

        public String getN_Link() {
            return n_Link;
        }

        public void setN_Link(String n_Link) {
            this.n_Link = n_Link;
        }

        private String n_Link;

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public int getUser_id() {
            return User_id;
        }

        public void setUser_id(int User_id) {
            this.User_id = User_id;
        }

        public int getN_Type() {
            return n_Type;
        }

        public void setN_Type(int n_Type) {
            this.n_Type = n_Type;
        }

        public String getN_SendTime() {
            return n_SendTime;
        }

        public void setN_SendTime(String n_SendTime) {
            this.n_SendTime = n_SendTime;
        }

        public String getN_Title() {
            return n_Title;
        }

        public void setN_Title(String n_Title) {
            this.n_Title = n_Title;
        }

        public String getN_Cover_imgUrl() {
            return n_Cover_imgUrl;
        }

        public void setN_Cover_imgUrl(String n_Cover_imgUrl) {
            this.n_Cover_imgUrl = n_Cover_imgUrl;
        }

        public String getN_Text() {
            return n_Text;
        }

        public void setN_Text(String n_Text) {
            this.n_Text = n_Text;
        }

        public int getN_ArticleId() {
            return n_ArticleId;
        }

        public void setN_ArticleId(int n_ArticleId) {
            this.n_ArticleId = n_ArticleId;
        }

        public int getN_IsNew() {
            return n_IsNew;
        }

        public void setN_IsNew(int n_IsNew) {
            this.n_IsNew = n_IsNew;
        }

        public int getN_Reply_Result() {
            return n_Reply_Result;
        }

        public void setN_Reply_Result(int n_Reply_Result) {
            this.n_Reply_Result = n_Reply_Result;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
