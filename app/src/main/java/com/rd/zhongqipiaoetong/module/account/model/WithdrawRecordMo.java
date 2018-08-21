package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.FinancialRecordVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/22 11:04
 * <p/>
 * Description: 提现记录信息({@link FinancialRecordVM})
 */
public class WithdrawRecordMo {
    /**
     * 提现时间
     */
    private String addTime;
    /**
     * 提现状态
     * 0 - 提现申请
     * 1 - 提现成功
     * 2 - 提现失败
     */
    private String status;
    /**
     * 提现银行卡
     */
    private String bankName;
    /**
     * 提现银行卡隐藏
     */
    private String hiddenBankNumber;
    /**
     * 状态 - 释义
     */
    private String statusStr;
    /**
     * 提现金额
     */
    private String cash;
    /**
     * 手续费
     */
    private String fee;
    /**
     * 账户余额
     */
    private String amount;
    private String verifyRemark;

    //--------------------------华丽分割线----------------------------------

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getHiddenBankNumber() {
        return hiddenBankNumber;
    }

    public void setHiddenBankNumber(String hiddenBankNumber) {
        this.hiddenBankNumber = hiddenBankNumber;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
    }
}