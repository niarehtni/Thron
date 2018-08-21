package com.rd.zhongqipiaoetong.module.product.model;

import java.io.Serializable;

/**
 * Created by pzw on 2017/8/17.
 */
public class ContentMo implements Serializable{
    private String name;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
