package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/23/023.
 */

public class MessageListJson {
    /**
     * Code : 成功
     * MessageList : [{"EntityId":3,"m_To_User_Id":1,"m_Date":"2018-03-02 14:22:04","m_Form_User_Id":3,"m_Text":"消息3","m_IsRead":0},{"EntityId":1,"m_To_User_Id":1,"m_Date":"2018-03-05 14:21:20","m_Form_User_Id":2,"m_Text":"消息1","m_IsRead":1}]
     */

    private String Code;
    private List<MessageListBean> MessageList;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<MessageListBean> getMessageList() {
        return MessageList;
    }

    public void setMessageList(List<MessageListBean> MessageList) {
        this.MessageList = MessageList;
    }

    public static class MessageListBean {
        /**
         * EntityId : 3
         * m_To_User_Id : 1
         * m_Date : 2018-03-02 14:22:04
         * m_Form_User_Id : 3
         * m_Text : 消息3
         * m_IsRead : 0
         */

        private int EntityId;
        private int m_To_User_Id;
        private String m_Date;
        private int m_Form_User_Id;
        private String m_Text;
        private int m_IsRead;

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public int getM_To_User_Id() {
            return m_To_User_Id;
        }

        public void setM_To_User_Id(int m_To_User_Id) {
            this.m_To_User_Id = m_To_User_Id;
        }

        public String getM_Date() {
            return m_Date;
        }

        public void setM_Date(String m_Date) {
            this.m_Date = m_Date;
        }

        public int getM_Form_User_Id() {
            return m_Form_User_Id;
        }

        public void setM_Form_User_Id(int m_Form_User_Id) {
            this.m_Form_User_Id = m_Form_User_Id;
        }

        public String getM_Text() {
            return m_Text;
        }

        public void setM_Text(String m_Text) {
            this.m_Text = m_Text;
        }

        public int getM_IsRead() {
            return m_IsRead;
        }

        public void setM_IsRead(int m_IsRead) {
            this.m_IsRead = m_IsRead;
        }
    }
}
