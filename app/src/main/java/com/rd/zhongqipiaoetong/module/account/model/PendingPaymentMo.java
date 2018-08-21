package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.activity.RepaymentDetailAct;
import com.rd.zhongqipiaoetong.module.account.viewmodel.PendingPaymentVM;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 14:25
 * <p/>
 * Description: 回款记录({@link PendingPaymentVM})信息
 */
public class PendingPaymentMo {
    private String investmentId;
    private String id;
    private String uuid;
    /**
     * 标的名称
     */
    private String borrowName;
    /**
     * 年利率
     */
    private double apr;
    /**
     * 本息金额
     */
    private String repaymentAmount;
    private String repaidAmount;
    /**
     * 计息起息日
     */
    private long   startDate;
    /**
     * 还款日
     */
    private long   repaymentTime;
    /**
     * 本金
     */
    private double capital;
    /**
     * 利息
     */
    private double interest;
    /**
     * 第几期
     */
    private String period;
    /**
     * 总期数
     */
    private String totalPeriod;

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

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public String getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(String repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getRepaidAmount() {
        return repaidAmount;
    }

    public void setRepaidAmount(String repaidAmount) {
        this.repaidAmount = repaidAmount;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(long repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    /**
     * 点击事件
     */
    public void onItemClick(View view) {
        // TODO 页面跳转
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.INVESTMENT_ID, investmentId);
        ActivityUtils.push(RepaymentDetailAct.class, intent);
    }
}