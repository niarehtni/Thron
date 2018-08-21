package com.rd.zhongqipiaoetong.module.account.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 */
public class TransferingMo implements Parcelable {
    /**
     * 债权ID
     */
    private String                  id;
    /**
     * 债权UUID
     */
    private String                  uuid;
    /**
     * 债权名称
     */
    private String                  name;
    /**
     * 借款标名称
     */
    private String                  borrowName;
    /**
     * 债权总额
     */
    private String                  amount;
    /**
     * 折让率
     */
    private String                  apr;
    /**
     * 折让金额
     */
    private String                  transfer;
    /**
     * 创建时间
     */
    private String                  addTime;
    /**
     * 截止时间
     */
    private String                  endTime;
    /**
     * 转让进度
     */
    private String                  progress;
    /**
     * 本期还款时间
     */
    private String                  nextRepayTime;
    /**
     * 转让手续费
     */
    private String                  manageFee;

    /**
     * 是否撤回
     */
    private boolean cancelSellingBondFlag = false;



    //--------------------------华丽分割线----------------------------------
    private UpDataListener<Boolean> upDataListener;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getNextRepayTime() {
        return nextRepayTime;
    }

    public void setNextRepayTime(String nextRepayTime) {
        this.nextRepayTime = nextRepayTime;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public UpDataListener<Boolean> getUpDataListener() {
        return upDataListener;
    }

    public void setUpDataListener(UpDataListener<Boolean> upDataListener) {
        this.upDataListener = upDataListener;
    }

    public boolean isCancelSellingBondFlag() {
        return cancelSellingBondFlag;
    }

    public void setCancelSellingBondFlag(boolean cancelSellingBondFlag) {
        this.cancelSellingBondFlag = cancelSellingBondFlag;
    }

    /**
     * 撤回点击事件
     */
    public void recallClick(View view) {
        // TODO 页面跳转
        if(cancelSellingBondFlag){
            UserLogic.transferRecall(id, upDataListener);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.uuid);
        dest.writeString(this.name);
        dest.writeString(this.borrowName);
        dest.writeString(this.amount);
        dest.writeString(this.apr);
        dest.writeString(this.transfer);
        dest.writeString(this.addTime);
        dest.writeString(this.endTime);
        dest.writeString(this.progress);
        dest.writeString(this.nextRepayTime);
        dest.writeString(this.manageFee);
        dest.writeByte(this.cancelSellingBondFlag ? (byte) 1 : (byte) 0);
    }

    public TransferingMo() {
    }

    protected TransferingMo(Parcel in) {
        this.id = in.readString();
        this.uuid = in.readString();
        this.name = in.readString();
        this.borrowName = in.readString();
        this.amount = in.readString();
        this.apr = in.readString();
        this.transfer = in.readString();
        this.addTime = in.readString();
        this.endTime = in.readString();
        this.progress = in.readString();
        this.nextRepayTime = in.readString();
        this.manageFee = in.readString();
        this.cancelSellingBondFlag = in.readByte() != 0;
    }

    public static final Creator<TransferingMo> CREATOR = new Creator<TransferingMo>() {
        @Override
        public TransferingMo createFromParcel(Parcel source) {
            return new TransferingMo(source);
        }

        @Override
        public TransferingMo[] newArray(int size) {
            return new TransferingMo[size];
        }
    };
}
