package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/31/16.
 */
public class DoAddBondMo {
    private double bondCapital;
    private int    borrowInvestmentId;
    private double discountRate;
    private boolean agree;

    public double getBondCapital() {
        return bondCapital;
    }

    public void setBondCapital(double bondCapital) {
        this.bondCapital = bondCapital;
    }

    public int getBorrowInvestmentId() {
        return borrowInvestmentId;
    }

    public void setBorrowInvestmentId(int borrowInvestmentId) {
        this.borrowInvestmentId = borrowInvestmentId;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
