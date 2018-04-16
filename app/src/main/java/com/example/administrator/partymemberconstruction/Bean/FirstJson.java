package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 */

public class FirstJson {
    /**
     * Code : 成功
     * Menu_List : [{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/通知公告.png","Menu_Name":"首都国企思想政治工作创新调研报告...","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"通知公告"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/DL-1.png","Menu_Name":"党章党规","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"两学一做"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/businesscard.png","Menu_Name":"系列讲话","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"两学一做"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/attendance.png","Menu_Name":"学习考试","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"两学一做"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/bar-chart.png","Menu_Name":"学习排名","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"两学一做"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/box.png","Menu_Name":"主题教育","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"两学一做"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/duihua.png","Menu_Name":"学习交流","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"两学一做"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/calendar.png","Menu_Name":"三会成果","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"三会一课"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/business.png","Menu_Name":"上党课","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"三会一课"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/import.png","Menu_Name":"组织生活会","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"三会一课"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/bill.png","Menu_Name":"专题会议","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"三会一课"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/矩形1副本12.png","Menu_Name":"典型引领","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"组织建设"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/group.png","Menu_Name":"党员活动日","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"组织建设"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/light","Menu_Name":"时代先锋","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"组织建设"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/image-text.png","Menu_Name":"模范党员","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"组织建设"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/图层6.png","Menu_Name":"人民网","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"党建资讯"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/information.png","Menu_Name":"时政要闻","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"党建资讯"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/electronics.png","Menu_Name":"集团新闻","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"党建资讯"},{"Menu_Logo_Url":"http://101.201.109.90:3333/Upload/Android/drawable-hdpi/international.png","Menu_Name":"特色要闻","Menu_Url":"http://jd.bjartin.com/","Menu_Region":"党建资讯"}]
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
         * Menu_Logo_Url : http://101.201.109.90:3333/Upload/Android/drawable-hdpi/通知公告.png
         * Menu_Name : 首都国企思想政治工作创新调研报告...
         * Menu_Url : http://jd.bjartin.com/
         * Menu_Region : 通知公告
         */

        private String Menu_Logo_Url;
        private String Menu_Name;
        private String Menu_Url;
        private String Menu_Region;

        public String getMenu_Logo_Url() {
            return Menu_Logo_Url;
        }

        public void setMenu_Logo_Url(String Menu_Logo_Url) {
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
