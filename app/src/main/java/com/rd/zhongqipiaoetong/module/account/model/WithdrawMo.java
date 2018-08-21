package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.RechargeVM;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/21 11:09
 * <p/>
 * Description: 充值初始化信息({@link RechargeVM})
 */
public class WithdrawMo {
    /**
     * 提现会话ID
     */
    private String                sessionId;
    /**
     * 可用余额
     */
    private String                balance;
    /**
     * 手续费
     */
    private String                fee;
    /**
     * 是否授权
     */
    private boolean               authorizated;
    /**
     * 用户认证类型
     */
    private String                authorizeType;
    private String                minInvest;
    /**
     * 银行卡信息
     */
    private ArrayList<BankCardMo> accountBankList;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public ArrayList<BankCardMo> getAccountBankList() {
        return accountBankList;
    }

    public void setAccountBankList(ArrayList<BankCardMo> accountBankList) {
        this.accountBankList = accountBankList;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public boolean isAuthorizated() {
        return authorizated;
    }

    public void setAuthorizated(boolean authorizated) {
        this.authorizated = authorizated;
    }

    public String getMinInvest() {
        return minInvest;
    }

    public void setMinInvest(String minInvest) {
        this.minInvest = minInvest;
    }
}