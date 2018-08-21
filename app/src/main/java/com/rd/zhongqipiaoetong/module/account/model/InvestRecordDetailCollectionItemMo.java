package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/28/16.
 */
public class InvestRecordDetailCollectionItemMo {
    private long repaymentTime;
    /**
     * 金额
     */
    private double repaymentAmount;
    /**
     * 状态: 还款状态 0未收款 1已收款
     */
    private int status;
    /**
     * 期数
     */
    private int period;
    private int totalPeriod;
    private double repaidAmount;

    //--------------------------华丽分割线----------------------------------


    public double getRepaidAmount() {
        return repaidAmount;
    }

    public void setRepaidAmount(double repaidAmount) {
        this.repaidAmount = repaidAmount;
    }

    public long getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(long repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public double getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(double repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        switch (status) {
            case -1:
                return "复审未通过";
            case 0:
                return "未收款";
            case 1:
                return "已收款";
            default:
                return "";
        }
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
