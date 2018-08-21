package com.rd.zhongqipiaoetong.module.account.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 */
public class TransferedMo implements Parcelable {
    /**
     * 债权ID
     */
    private String id;
    /**
     * 债权UUID
     */
    private String uuid;
    /**
     * 借款标名称
     */
    private String borrowName;
    /**
     * 债券名称
     */
    private String name;
    /**
     * 转出总额
     */
    private String amount;
    /**
     * 这让金额
     */
    private String transfer;
    /**
     * 折让率
     */
    private String apr;
    /**
     * 已转出本金
     */
    private String soldCapital;
    /**
     * 提前付息
     */
    private String payInterest;
    /**
     * 转让手续费
     */
    private String manageFee;
    /**
     * 创建时间
     */
    private String addTime;
    /**
     * 协议
     */
    private String protrcolUrl;
    /**
     * 字面意思是收兑，实际上看不懂
     */
    private String redemption;

    //--------------------------华丽分割线----------------------------------

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

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getSoldCapital() {
        return soldCapital;
    }

    public void setSoldCapital(String soldCapital) {
        this.soldCapital = soldCapital;
    }

    public String getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(String payInterest) {
        this.payInterest = payInterest;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getRedemption() {
        return redemption;
    }

    public void setRedemption(String redemption) {
        this.redemption = redemption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtrcolUrl() {
        return protrcolUrl;
    }

    public void setProtrcolUrl(String protrcolUrl) {
        this.protrcolUrl = protrcolUrl;
    }

    public TransferedMo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.borrowName);
        dest.writeString(this.name);
        dest.writeString(this.amount);
        dest.writeString(this.transfer);
        dest.writeString(this.apr);
        dest.writeString(this.soldCapital);
        dest.writeString(this.payInterest);
        dest.writeString(this.manageFee);
        dest.writeString(this.addTime);
        dest.writeString(this.protrcolUrl);
        dest.writeString(this.redemption);
    }

    protected TransferedMo(Parcel in) {
        this.id = in.readString();
        this.uuid = in.readString();
        this.borrowName = in.readString();
        this.name = in.readString();
        this.amount = in.readString();
        this.transfer = in.readString();
        this.apr = in.readString();
        this.soldCapital = in.readString();
        this.payInterest = in.readString();
        this.manageFee = in.readString();
        this.addTime = in.readString();
        this.protrcolUrl = in.readString();
        this.redemption = in.readString();
    }

    public static final Creator<TransferedMo> CREATOR = new Creator<TransferedMo>() {
        @Override
        public TransferedMo createFromParcel(Parcel source) {
            return new TransferedMo(source);
        }

        @Override
        public TransferedMo[] newArray(int size) {
            return new TransferedMo[size];
        }
    };
}
