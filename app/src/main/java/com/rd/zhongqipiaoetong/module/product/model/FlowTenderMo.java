package com.rd.zhongqipiaoetong.module.product.model;

/**
 * Created by Administrator on 2016/9/29.
 */
public class FlowTenderMo {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 购买份数
     */
    private int buyCopies;
    /**
     * 购买金额
     */
    private double buyMoney;
    /**
     * 状态:1:成功，2:失败，3:结束（还款完成），0:初始化
     */
    private byte status;
    /**
     * 投资时间
     */
    private long addTime;
    /**
     * 年化利率
     */
    private double apr;
    /**
     * 理财项目名
     */
    private String projectName;
    /**
     * 借款项目uuid
     */
    private String uuid;
    /**
     * 预计收益
     */
    private double waitYields;
    /**
     * 实际收益
     */
    private double yesYields;
    /**
     * 收回本金
     */
    private double yesMoney;
    /**
     * 预计回款时间
     */
    private long waitTime;
    /**
     * 实际还款时间
     */
    private long yesTime;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getBuyCopies() {
        return buyCopies;
    }
    public void setBuyCopies(int buyCopies) {
        this.buyCopies = buyCopies;
    }
    public double getBuyMoney() {
        return buyMoney;
    }
    public void setBuyMoney(double buyMoney) {
        this.buyMoney = buyMoney;
    }
    public byte getStatus() {
        return status;
    }
    public void setStatus(byte status) {
        this.status = status;
    }
    public long getAddTime() {
        return addTime;
    }
    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }
    public double getApr() {
        return apr;
    }
    public void setApr(double apr) {
        this.apr = apr;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public double getWaitYields() {
        return waitYields;
    }
    public void setWaitYields(double waitYields) {
        this.waitYields = waitYields;
    }
    public double getYesYields() {
        return yesYields;
    }
    public void setYesYields(double yesYields) {
        this.yesYields = yesYields;
    }
    public double getYesMoney() {
        return yesMoney;
    }
    public void setYesMoney(double yesMoney) {
        this.yesMoney = yesMoney;
    }
    public long getWaitTime() {
        return waitTime;
    }
    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
    public long getYesTime() {
        return yesTime;
    }
    public void setYesTime(long yesTime) {
        this.yesTime = yesTime;
    }
}
