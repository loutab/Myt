package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class GroupJson {

    /**
     * Code : 成功Error
     * Tissue_tree : [{"Id":1,"OrganizationName":"第五支部"},{"Id":2,"OrganizationName":"第六支部"}]
     */

    private String Code;

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    private String Error;
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
         * Id : 1
         * OrganizationName : 第五支部
         */

        private int Id;
        private String OrganizationName;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getOrganizationName() {
            return OrganizationName;
        }

        public void setOrganizationName(String OrganizationName) {
            this.OrganizationName = OrganizationName;
        }
    }
}
