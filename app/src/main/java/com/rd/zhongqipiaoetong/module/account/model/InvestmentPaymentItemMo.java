package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/7 17:02
 * <p/>
 * Description:回款列表记录({@link InvestmentReceivedMo})
 */
public class InvestmentPaymentItemMo {
    /**
     * 回款时间
     */
    private String paidTime;
    /**
     * 回款金额
     */
    private String paidMoney;
    /**
     * 期数
     */
    private String period;

    public InvestmentPaymentItemMo() {
    }

    public InvestmentPaymentItemMo(String paidTime, String paidMoney, String period) {
        this.paidTime = paidTime;
        this.paidMoney = paidMoney;
        this.period = period;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public String getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(String paidMoney) {
        this.paidMoney = paidMoney;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}