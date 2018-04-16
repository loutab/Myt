package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class PartJson {

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    /**
     * Code : 成功
     * Tissue_tree : [{"EntityId":1,"dt_ParentId":0,"dt_Department_Name":"行政部"},{"EntityId":2,"dt_ParentId":1,"dt_Department_Name":"秘书部"}]
     */

    private String Error;
    private String Code;
    private List<TissueTreeBean> Tissue_tree;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<TissueTreeBean> getTissue_tree() {
        return Tissue_tree;
    }

    public void setTissue_tree(List<TissueTreeBean> Tissue_tree) {
        this.Tissue_tree = Tissue_tree;
    }

    public static class TissueTreeBean {
        /**
         * EntityId : 1
         * dt_ParentId : 0
         * dt_Department_Name : 行政部
         */

        private int EntityId;
        private int dt_ParentId;
        private String dt_Department_Name;

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public int getDt_ParentId() {
            return dt_ParentId;
        }

        public void setDt_ParentId(int dt_ParentId) {
            this.dt_ParentId = dt_ParentId;
        }

        public String getDt_Department_Name() {
            return dt_Department_Name;
        }

        public void setDt_Department_Name(String dt_Department_Name) {
            this.dt_Department_Name = dt_Department_Name;
        }
    }
}
