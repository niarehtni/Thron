package com.rd.zhongqipiaoetong.module.account.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

import java.text.DecimalFormat;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * String: 16/3/21 下午3:32
 * <p/>
 * Description: 债权转让MO
 */
public class TransferMo implements Parcelable {
    private int                     id;
    private int                     borrowInvestmentId;
    private int                     borrowId;
    private int                     remainDays;
    private double                  rateYear;
    private double                  bondCapital;
    private String                  borrowName;
    private double                  remainCapital;
    private int                     holdDays;
    private long                    addTime;
    private double                  discountRate;
    private double                  sellingPrice;
    private long                    repaymentTime;
    private String                  manageFee;
    private UpDataListener<Boolean> upDataListener;
    private String                  feeType;
    private String                  sellFee;

    //--------------------------华丽分割线----------------------------------

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getSellFee() {
        return sellFee;
    }

    public void setSellFee(String sellFee) {
        this.sellFee = sellFee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorrowInvestmentId() {
        return borrowInvestmentId;
    }

    public void setBorrowInvestmentId(int borrowInvestmentId) {
        this.borrowInvestmentId = borrowInvestmentId;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public double getRateYear() {
        return rateYear;
    }

    public void setRateYear(double rateYear) {
        this.rateYear = rateYear;
    }

    public double getBondCapital() {
        return bondCapital;
    }

    public void setBondCapital(double bondCapital) {
        this.bondCapital = bondCapital;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public double getRemainCapital() {
        return remainCapital;
    }

    public void setRemainCapital(double remainCapital) {
        this.remainCapital = remainCapital;
    }

    public int getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(int holdDays) {
        this.holdDays = holdDays;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(long repaymentTime) {
        this.repaymentTime = repaymentTime;
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

    public String getInvestAmount() {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(bondCapital);
    }

    /**
     * 撤回点击事件
     */
    public void recallClick(View view) {
        // TODO 页面跳转
        UserLogic.transferRecall(String.valueOf(id), upDataListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeInt(borrowInvestmentId);
        dest.writeInt(borrowId);
        dest.writeInt(remainDays);
        dest.writeDouble(rateYear);
        dest.writeDouble(bondCapital);
        dest.writeString(this.borrowName);
        dest.writeDouble(remainCapital);
        dest.writeInt(holdDays);
        dest.writeLong(addTime);
        dest.writeDouble(discountRate);
        dest.writeDouble(sellingPrice);
        dest.writeLong(repaymentTime);
        dest.writeString(manageFee);
    }

    public TransferMo() {
    }

    protected TransferMo(Parcel in) {
        this.id = in.readInt();
        this.borrowInvestmentId = in.readInt();
        this.borrowId = in.readInt();
        this.remainDays = in.readInt();
        this.rateYear = in.readDouble();
        this.bondCapital = in.readDouble();
        this.borrowName = in.readString();
        this.remainCapital = in.readDouble();
        this.holdDays = in.readInt();
        this.addTime = in.readLong();
        this.discountRate = in.readDouble();
        this.sellingPrice = in.readDouble();
        this.repaymentTime = in.readLong();
        this.manageFee = in.readString();
    }

    public static final Parcelable.Creator<TransferMo> CREATOR = new Parcelable.Creator<TransferMo>() {
        @Override
        public TransferMo createFromParcel(Parcel source) {
            return new TransferMo(source);
        }

        @Override
        public TransferMo[] newArray(int size) {
            return new TransferMo[size];
        }
    };
}