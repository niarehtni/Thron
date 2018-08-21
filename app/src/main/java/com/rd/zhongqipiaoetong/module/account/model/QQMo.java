package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Created by pzw on 2017/6/21.
 */
public class QQMo {
    private String name;
    private String qq;

    public QQMo(String name, String qq){
        this.name = name;
        this.qq = qq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
