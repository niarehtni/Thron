package com.rd.zhongqipiaoetong.module.product.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/29.
 */
public class FlowInitMo implements Serializable{
    /**
     * 用户是否已认证
     */
    private boolean authorized;

    /**
     * 用户认证类型
     */
    private String authorizeType;
    /**
     * 借款标总额
     */
    private double amount;
    /**
     * 剩余可投份数
     */
    private int remainCount;

    /**
     * 用户可用资金
     */
    private double useMoney;

    /**
     * 最低投资份数
     */
    private int minCount;

    /**
     * 最高可投份数
     */
    private int maxCount;
    /**
     * 开标倒计时（秒）
     */
    private long timeToStart;

    /**
     * 标的状态
     */
    private String status;

    /**
     * 当前投标进度
     */
    private double progress;
    /**
     * 投标会话ID
     */
    private String sessionId;
    /**
     * 借款时间类型
     */
    private String timeType;

    /**
     * 还款方式
     */
    private String repayType;

    /**
     * 借款期限
     */
    private int timeLimit;
    /**
     * 年化利率
     */
    private double apr;

    /**
     * 项目总收益
     */
    private double totalIncome;
    /**
     * 是否已绑卡
     */
    private boolean hasBindBankCard;
    /**
     * 是否已设置支付密码
     */
    private boolean hasSetPayPwd;

    /**
     * 标的收益
     */
    private double possibleEarnedAmount;

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    public double getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(double useMoney) {
        this.useMoney = useMoney;
    }

    public int getMinCount() {
        return minCount;
    }

    public void setMinCount(int minCount) {
        this.minCount = minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public long getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(long timeToStart) {
        this.timeToStart = timeToStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public boolean isHasBindBankCard() {
        return hasBindBankCard;
    }

    public void setHasBindBankCard(boolean hasBindBankCard) {
        this.hasBindBankCard = hasBindBankCard;
    }

    public boolean isHasSetPayPwd() {
        return hasSetPayPwd;
    }

    public void setHasSetPayPwd(boolean hasSetPayPwd) {
        this.hasSetPayPwd = hasSetPayPwd;
    }

    public double getPossibleEarnedAmount() {
        return possibleEarnedAmount;
    }

    public void setPossibleEarnedAmount(double possibleEarnedAmount) {
        this.possibleEarnedAmount = possibleEarnedAmount;
    }

    public String showCount(){
        if(minCount <= remainCount){
            return String.valueOf(minCount);
        } else {
            return String.valueOf(remainCount);
        }
    }
}
