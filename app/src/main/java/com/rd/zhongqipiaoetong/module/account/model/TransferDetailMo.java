package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 */
public class TransferDetailMo {
    /**
     * 债权ID
     */
    private String id;
    /**
     * 标的ID
     */
    private String borrowId;
    /**
     * 标的名称
     */
    private String borrowName;
    /**
     * 借款标利率
     */
    private String borrowApr;
    /**
     * 标的类型
     */
    private String borrowType;
    /**
     * 标的详情
     */
    private String borrowContent;
    /**
     * 标的借款期限类型
     */
    private String borrowTimeType;
    /**
     * 标的借款期限
     */
    private int    borrowTimeLimit;
    /**
     * 标的还款类型
     */
    private String borrowRepayType;
    /**
     * 债权名称
     */
    private String name;
    /**
     * 债权总额
     */
    private String amount;
    /**
     * 剩余可转金额
     */
    private String remainAmount;
    /**
     * 转让期限
     */
    private int    remainDays;
    /**
     * 折让率
     */
    private String apr;
    /**
     * 转让进度
     */
    private String progress;
    /**
     * 转让状态
     */
    private String status;
    /**
     * 创建日期
     */
    private String   addTime;
    /**
     * 最低转出金额
     */
    private String minBondMoney;
    /**
     * 到期日
     */
    private String   lastRepaymentTime;
    /**
     * 转出次数
     */
    private int    tenderTimes;

    //--------------------------华丽分割线----------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowContent() {
        return borrowContent;
    }

    public void setBorrowContent(String borrowContent) {
        this.borrowContent = borrowContent;
    }

    public String getBorrowTimeType() {
        return borrowTimeType;
    }

    public void setBorrowTimeType(String borrowTimeType) {
        this.borrowTimeType = borrowTimeType;
    }

    public int getBorrowTimeLimit() {
        return borrowTimeLimit;
    }

    public void setBorrowTimeLimit(int borrowTimeLimit) {
        this.borrowTimeLimit = borrowTimeLimit;
    }

    public String getBorrowRepayType() {
        return borrowRepayType;
    }

    public void setBorrowRepayType(String borrowRepayType) {
        this.borrowRepayType = borrowRepayType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMinBondMoney() {
        return minBondMoney;
    }

    public void setMinBondMoney(String minBondMoney) {
        this.minBondMoney = minBondMoney;
    }

    public String getLastRepaymentTime() {
        return lastRepaymentTime;
    }

    public void setLastRepaymentTime(String lastRepaymentTime) {
        this.lastRepaymentTime = lastRepaymentTime;
    }

    public int getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(int tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }
}
