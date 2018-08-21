package com.rd.zhongqipiaoetong.module.product.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
public class ProductInfoMo implements Serializable{
    private String      qiniu_domain;
    private ProductInfo borrowVO;

    public String getQiniu_domain() {
        return qiniu_domain;
    }

    public void setQiniu_domain(String qiniu_domain) {
        this.qiniu_domain = qiniu_domain;
    }

    public ProductInfo getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(ProductInfo borrowVO) {
        this.borrowVO = borrowVO;
    }

    public class ProductInfo implements Serializable{
        private String          content;
        private List<ContentMo> borrowContentList;
        private List<ImageList> borrowImageList;

        public List<ContentMo> getBorrowContentList() {
            return borrowContentList;
        }

        public void setBorrowContentList(List<ContentMo> borrowContentList) {
            this.borrowContentList = borrowContentList;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<ImageList> getBorrowImageList() {
            return borrowImageList;
        }

        public void setBorrowImageList(List<ImageList> borrowImageList) {
            this.borrowImageList = borrowImageList;
        }
    }

    public class ImageList implements Serializable{
        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
