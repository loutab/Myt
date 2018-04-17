package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class PartMJson {


    /**
     * Code : 成功
     * DepartList : [{"Id":1,"DepartName":"行政部"},{"Id":2,"DepartName":"秘书部1"}]
     */

    private String Code;
    private List<DepartListBean> DepartList;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<DepartListBean> getDepartList() {
        return DepartList;
    }

    public void setDepartList(List<DepartListBean> DepartList) {
        this.DepartList = DepartList;
    }

    public static class DepartListBean {
        /**
         * Id : 1
         * DepartName : 行政部
         */

        private int Id;
        private String DepartName;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getDepartName() {
            return DepartName;
        }

        public void setDepartName(String DepartName) {
            this.DepartName = DepartName;
        }
    }
}
