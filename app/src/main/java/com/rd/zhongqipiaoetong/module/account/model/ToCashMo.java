package com.rd.zhongqipiaoetong.module.account.model;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/25/16.
 */
public class ToCashMo {
    /**
     * 银行卡号
     */
    private String bankNO;
    /**
     * 银行id
     */
    private String bankId;
    /**
     * 支付密码
     */
    private String paypwd;
    /**
     * 提现金额
     */
    private String cash;
    /**
     * 银行logo
     */
    private String logoPicUrl;
    /**
     * 银行名称
     */
    private String bank;



    public String getBankNO() {
        return bankNO;
    }

    public void setBankNO(String bankNO) {
        this.bankNO = bankNO;
    }

    public String getPaypwd() {
        return paypwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getLogoPicUrl() {
        return logoPicUrl;
    }

    public void setLogoPicUrl(String logoPicUrl) {
        this.logoPicUrl = logoPicUrl;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
