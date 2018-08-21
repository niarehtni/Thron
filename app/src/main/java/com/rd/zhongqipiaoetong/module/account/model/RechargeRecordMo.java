package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.FinancialRecordVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/22 11:04
 * <p/>
 * Description: 充值记录信息({@link FinancialRecordVM})
 */
public class RechargeRecordMo {
    /**
     * 记录时间
     */
    private String addTime;

    /**
     * 交易号
     */
    private String tradeNumber;

    /**
     * 交易类型说明
     */
    private String typeStr;

    /**
     * 交易渠道
     */
    private String payment;

    /**
     * 金额
     */
    private String cash;

    /**
     * 状态 0:充值处理中 1:充值成功 2:充值失败
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核备注
     */
    private String verifyRemark;

    //--------------------------华丽分割线----------------------------------

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark;
    }
}