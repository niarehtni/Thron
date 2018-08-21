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
public class RechargeMo {
    /**
     * 充值会话ID
     */
    private String                sessionId;
    /**
     * 可用余额
     */
    private String                balance;
    /**
     * 是否授权
     */
    private boolean               authorizated;
    /**
     * 用户认证类型
     */
    private String                authorizeType;
    private String                host;
    /**
     * 银行卡信息
     */
    private ArrayList<BankCardMo> bankCardList;
    /**
     * 最小充值额
     * minInvest
     *
     * @return
     */
    private double                minInvest;

    public double getMinInvest() {
        return minInvest;
    }

    public void setMinInvest(double minInvest) {
        this.minInvest = minInvest;
    }

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

    public ArrayList<BankCardMo> getBankCardList() {
        return bankCardList;
    }

    public void setBankCardList(ArrayList<BankCardMo> bankCardList) {
        this.bankCardList = bankCardList;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}