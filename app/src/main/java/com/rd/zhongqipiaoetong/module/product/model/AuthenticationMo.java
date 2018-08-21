package com.rd.zhongqipiaoetong.module.product.model;

import com.rd.zhongqipiaoetong.module.product.viewmodel.AuthenticationVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/2 10:56
 * <p/>
 * Description:资料审核({@link AuthenticationVM})
 */
public class AuthenticationMo {
    /**
     * 审核项目
     */
    private String title;
    /**
     * 审核状态
     */
    private String status;
    /**
     * 审核时间
     */
    private String date;

    public AuthenticationMo(String title, String status, String date) {
        this.title = title;
        this.status = status;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}