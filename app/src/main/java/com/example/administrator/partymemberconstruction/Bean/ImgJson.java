package com.example.administrator.partymemberconstruction.Bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19/019.
 */

public class ImgJson {

    /**
     * Code : 成功
     * Product : [{"EntityId":1,"Picture_Url":"http://101.201.109.90:3333/Upload/LBT/1.png"},{"EntityId":2,"Picture_Url":"http://101.201.109.90:3333/Upload/LBT/2.png"},{"EntityId":3,"Picture_Url":"http://101.201.109.90:3333/Upload/LBT/3.png"}]
     */

    private String Code;
    private List<ProductBean> Product;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public List<ProductBean> getProduct() {
        return Product;
    }

    public void setProduct(List<ProductBean> Product) {
        this.Product = Product;
    }

    public static class ProductBean {
        /**
         * EntityId : 1
         * Picture_Url : http://101.201.109.90:3333/Upload/LBT/1.png
         */

        private int EntityId;
        private String Picture_Url;

        public int getEntityId() {
            return EntityId;
        }

        public void setEntityId(int EntityId) {
            this.EntityId = EntityId;
        }

        public String getPicture_Url() {
            return Picture_Url;
        }

        public void setPicture_Url(String Picture_Url) {
            this.Picture_Url = Picture_Url;
        }
    }
}
