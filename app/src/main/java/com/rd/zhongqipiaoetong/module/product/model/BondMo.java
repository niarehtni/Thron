package com.rd.zhongqipiaoetong.module.product.model;

import android.text.TextUtils;

import com.rd.zhongqipiaoetong.module.product.viewmodel.BondDetailVM;

/**
 * Description: 债券产品MO({@link BondDetailVM})
 */
public class BondMo extends ProjectInfo {
    /**
     * 进度
     */
    private double  sellingProgress;
    /**
     * 债权总额
     */
    private double  bondCapital;
    /**
     * 还款方式
     */
    private int  repayWay;
    /**
     * 折让率
     */
    private double  discountRate;
    /**
     * 投资笔数
     */
    private int countBondInvest;
    /**
     * 剩余债权
     */
    private double remainCapital;
    /**
     *
     */
    private String  borrowContent;
    /**
     * 标id
     */
    private String  borrowId;
    /**
     * 标名
     */
    private String  borrowName;
    private int     borrowTimeLimit;
    private String  borrowTimeType;
    private String  borrowType;
    private String  lastRepaymentTime;
    /**
     * 最小投资金额
     */
    private double  minBondMoney;
    private int     remainDays;
    /**
     * 还款方式：
     * 1 - 每月还息到期还本
     * 2 - 一次性还款
     * 3 - 按月分期还款
     */
    private String  borrowRepayType;
    /**
     * 还款方式 - 释义
     */
    private String  paybackTypeStr;
    ///////////////////////////////////////////////////////////////////////////
    // 业务逻辑属性
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 是否已售罄
     */
    private boolean isSoldOut;
    /**
     * 是否显示邮戳
     */
    private boolean isShowStamp;
    private double  interest;
    private int isOwn;

    /**
     * 标状态 1-待售中->显示倒计时时间，
     * 2-投资中->我要投资，
     * 3-满标待审->已售罄，
     * 4-还款中->已售罄，
     * 5-已还款->已售罄，
     * 6-流标->已售罄，
     * 7-截标->已售罄，
     * 8-撤回->不显示按钮
     */
    @Override
    protected void definitionStatus(String status) {
    }

    @Override
    protected void definitionType(String type) {
    }

    public double getSellingProgress() {
        return sellingProgress;
    }

    public void setSellingProgress(double sellingProgress) {
        this.sellingProgress = sellingProgress;
    }

    public double getBondCapital() {
        return bondCapital;
    }

    public void setBondCapital(double bondCapital) {
        this.bondCapital = bondCapital;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public int getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(int repayWay) {
        this.repayWay = repayWay;
    }

    public int getCountBondInvest() {
        return countBondInvest;
    }

    public void setCountBondInvest(int countBondInvest) {
        this.countBondInvest = countBondInvest;
    }

    public double getRemainCapital() {
        return remainCapital;
    }

    public void setRemainCapital(double remainCapital) {
        this.remainCapital = remainCapital;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowContent() {
        return borrowContent;
    }

    public void setBorrowContent(String borrowContent) {
        this.borrowContent = borrowContent;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public int getBorrowTimeLimit() {
        return borrowTimeLimit;
    }

    public void setBorrowTimeLimit(int borrowTimeLimit) {
        this.borrowTimeLimit = borrowTimeLimit;
    }

    public String getBorrowTimeType() {
        return borrowTimeType;
    }

    public void setBorrowTimeType(String borrowTimeType) {
        this.borrowTimeType = borrowTimeType;
    }

    public String getLastRepaymentTime() {
        return lastRepaymentTime;
    }

    public void setLastRepaymentTime(String lastRepaymentTime) {
        this.lastRepaymentTime = lastRepaymentTime;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public double getMinBondMoney() {
        return minBondMoney;
    }

    public void setMinBondMoney(double minBondMoney) {
        this.minBondMoney = minBondMoney;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public String getBorrowRepayType() {
        return borrowRepayType;
    }

    public void setBorrowRepayType(String borrowRepayType) {
        this.borrowRepayType = borrowRepayType;
    }

    public void setPaybackTypeStr(String paybackTypeStr) {
        this.paybackTypeStr = paybackTypeStr;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }

    public void setShowStamp(boolean showStamp) {
        isShowStamp = showStamp;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(int isOwn) {
        this.isOwn = isOwn;
    }

    public boolean isSoldOut() {
        if (TextUtils.isEmpty(status)) {
            status = "0";
        }
        int status = Integer.valueOf(this.status);
        return status >= 3 && status <= 7;
    }

    public boolean isShowStamp() {
        if (TextUtils.isEmpty(status)) {
            status = "0";
        }
        int status = Integer.valueOf(this.status);
        return !(status == 2 || status == 8);
    }

    public boolean isDay() {
        if (borrowTimeType.equals("1"))
            return true;
        return false;
    }

    public boolean canInvest(){
        if(TextUtils.isEmpty(status)){
            status = "0";
        }
        int s = Integer.valueOf(status);
        if(s == 1 ){
            if(isOwn == 1){
                return false;
            }
            return true;
        }  else {
            return false;
        }
    }

    public String buttonStr(){
        if(TextUtils.isEmpty(status)){
            status = "0";
        }
        int s = Integer.valueOf(status);
        if(s == 1){
            if(isOwn == 1){
                return "当前是您的债权转让信息";
            }
            return "立即受让";
        } else {
            return "转让完成";
        }
    }
}