package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 */
public class ToAddBondMo {
    private double  money;
    private double  discountApr;
    private double  sellFee;
    private String feeType;
    private double  minRate;

    //--------------------------华丽分割线----------------------------------

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getDiscountApr() {
        return discountApr;
    }

    public void setDiscountApr(double discountApr) {
        this.discountApr = discountApr;
    }

    public double getSellFee() {
        return sellFee;
    }

    public void setSellFee(double sellFee) {
        this.sellFee = sellFee;
    }

    public double getMinRate() {
        return minRate;
    }

    public void setMinRate(double minRate) {
        this.minRate = minRate;
    }

    public double discount() {
        return money * (1 - discountApr / 100);
    }
}
