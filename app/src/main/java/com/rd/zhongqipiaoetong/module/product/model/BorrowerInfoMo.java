package com.rd.zhongqipiaoetong.module.product.model;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/25 17:45
 * <p/>
 * Description:借款资料
 */
public class BorrowerInfoMo {
    private String imageURL;
    private String description;

    public BorrowerInfoMo(String imageURL, String description) {
        this.imageURL = imageURL;
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}