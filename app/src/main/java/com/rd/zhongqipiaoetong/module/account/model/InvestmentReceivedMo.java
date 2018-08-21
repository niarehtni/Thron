package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.fragment.InvestmentReceivedFrag;

import java.util.List;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/7 15:34
 * <p/>
 * Description: 回款信息({@link InvestmentReceivedFrag})
 */
public class InvestmentReceivedMo {
    /**
     * 回款总额
     */
    private String                        totalPayment;
    /**
     * 下期还款时间
     */
    private String                        nextPaymentDate;
    /**
     * 总期数
     */
    private String                        totalPeriod;
    /**
     * 已回款
     */
    private String                        paidPeriod;
    /**
     * 回款记录
     */
    private List<InvestmentPaymentItemMo> paymentItem;

    public InvestmentReceivedMo() {
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(String nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public String getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public String getPaidPeriod() {
        return paidPeriod;
    }

    public void setPaidPeriod(String paidPeriod) {
        this.paidPeriod = paidPeriod;
    }

    public List<InvestmentPaymentItemMo> getPaymentItem() {
        return paymentItem;
    }

    public void setPaymentItem(List<InvestmentPaymentItemMo> paymentItem) {
        this.paymentItem = paymentItem;
    }
}