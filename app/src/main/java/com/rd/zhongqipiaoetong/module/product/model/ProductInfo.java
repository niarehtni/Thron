package com.rd.zhongqipiaoetong.module.product.model;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import com.rd.zhongqipiaoetong.utils.Converter;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.io.Serializable;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/7 14:33
 * <p/>
 * Description: 产品基础信息类
 */
public abstract class ProductInfo implements Serializable {
    /**
     * 标ID
     */
    protected String id;
    /**
     * 标的UUID
     */
    protected String uuid;
    /**
     * 标名称
     */
    protected String name;
    /**
     * 预期年化
     */
    protected String rateYear;
    protected String platformRateYear;
    /**
     * 项目规模
     */
    protected String amountBorrow;
    /**
     * 状态
     */
    protected String status;
    /**
     * 状态 - 释义
     */
    protected String statusStr;
    /**
     * 标的类型
     */
    protected String type;
    /**
     * 标类型icon
     */
    protected String borrowImage;
    /**
     * 标的类型 - 释义
     */
    protected String typeStr;
    /**
     * 标的进度
     */
    protected String progressPercentage;

    /**
     * 状态 - 释义
     */
    protected abstract void definitionStatus(String status);

    /**
     * 类型 - 释义
     */
    protected abstract void definitionType(String type);

    public String getAmountBorrow() {
        return amountBorrow;
    }

    public void setAmountBorrow(String amountBorrow) {
        this.amountBorrow = amountBorrow;
    }

    public String getPlatformRateYear() {
        return platformRateYear;
    }

    public void setPlatformRateYear(String platformRateYear) {
        this.platformRateYear = platformRateYear;
    }

    public String getBorrowImage() {
        return borrowImage;
    }

    public void setBorrowImage(String borrowImage) {
        this.borrowImage = borrowImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRateYear() {
        return rateYear;
    }

    public void setRateYear(String rateYear) {
        this.rateYear = rateYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        definitionStatus(status);
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        definitionType(type);
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public String getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(String progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public Spannable rate() {

        String str = "";
        if (Converter.getDouble(platformRateYear) != 0) {
            str = DisplayFormat.doubleFormat(rateYear) + "%" + "+" + DisplayFormat.doubleFormat(platformRateYear) + "%";
        } else {
            str = DisplayFormat.doubleFormat(rateYear) + "%";
        }
        Spannable span  = new SpannableString(str);
        int       index = str.indexOf(".");
        span.setSpan(new AbsoluteSizeSpan(14, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return span;
    }
}