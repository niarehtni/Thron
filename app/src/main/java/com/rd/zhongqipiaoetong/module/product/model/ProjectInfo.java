package com.rd.zhongqipiaoetong.module.product.model;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/8 10:21
 * <p/>
 * Description: 标的基础信息类(理财产品标、债权转让标)
 */
public abstract class ProjectInfo extends ProductInfo {
    /**
     * 借款、转让期限
     */
    protected String  timeLimit;
    /**
     * 期限类型：0-天，1-月
     */
    protected String  timeType;
    /**
     * 项目总额
     */
    protected String  amount;
    /**
     * 创建时间
     */
    private   String  addTime;
    /**
     * 结束日期
     */
    protected String  endDate;
    /**
     * 剩余金额
     */
    protected String  remainAmount;
    /**
     * 投资/借款记录数
     */
    protected String  investedCount;
    /**
     * 是否是定向标
     */
    protected boolean directional;
    /**
     * 是否要求登录后可见
     */
    protected boolean logonRequired;
    /**
     * 是否是推荐标
     */
    protected boolean isRecommended;

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getInvestedCount() {
        return investedCount;
    }

    public void setInvestedCount(String investedCount) {
        this.investedCount = investedCount;
    }

    public boolean isDirectional() {
        return directional;
    }

    public void setDirectional(boolean directional) {
        this.directional = directional;
    }

    public boolean isLogonRequired() {
        return logonRequired;
    }

    public void setLogonRequired(boolean logonRequired) {
        this.logonRequired = logonRequired;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }
}