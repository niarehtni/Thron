package com.rd.zhongqipiaoetong.module.product.model;

import com.rd.zhongqipiaoetong.common.Constant;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/29.
 */
public class FlowDetailMo implements Serializable {
    /**
     * 用户id
     */
    private long   id;
    /**
     * 项目uuid
     */
    private String uuid;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目金额
     */
    private double account;
    /**
     * 利率
     */
    private double apr;
    /**
     * 状态:0发布成功，1:初审通过，2：不通过，3:投满，6:结束
     */
    private byte   status;
    /**
     * 每份金额
     */
    private double eachMoney;
    /**
     * 起投份数
     */
    private int    startCopies;
    /**
     * 总份数
     */
    private int    totalCopies;
    /**
     * 已购份数
     */
    private int    yesCopies;
    /**
     * 完成比例
     */
    private double scales;
    /**
     * 期限
     */
    private int    timeLimit;
    /**
     * 还款方式
     */
    private int    repayStyle;
    /**
     * 是否按天
     */
    private byte   isDay;
    /**
     * 添加时间
     */
    private long   addTime;
    /**
     * 审核时间
     */
    private long   verifyTime;
    private String tendertimes;

    public String getTenderTimes() {
        return tendertimes;
    }

    public void setTenderTimes(String tenderTimes) {
        this.tendertimes = tenderTimes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public double getApr() {
        return apr;
    }

    public void setApr(double apr) {
        this.apr = apr;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public double getEachMoney() {
        return eachMoney;
    }

    public void setEachMoney(double eachMoney) {
        this.eachMoney = eachMoney;
    }

    public int getStartCopies() {
        return startCopies;
    }

    public void setStartCopies(int startCopies) {
        this.startCopies = startCopies;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getYesCopies() {
        return yesCopies;
    }

    public void setYesCopies(int yesCopies) {
        this.yesCopies = yesCopies;
    }

    public double getScales() {
        return scales;
    }

    public void setScales(double scales) {
        this.scales = scales;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(int repayStyle) {
        this.repayStyle = repayStyle;
    }

    public byte getIsDay() {
        return isDay;
    }

    public void setIsDay(byte isDay) {
        this.isDay = isDay;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public long getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
    }

    public boolean isDay() {
        if (this.isDay == 1) {
            return true;
        }
        return false;
    }

    public String getTimeType() {
        return isDay() ? "项目期限(天)" : "项目期限(月)";
    }

    private String paybackTypeStr;

    public String getPaybackTypeStr() {
        switch (repayStyle + "") {
            case Constant.STATUS_1:
                paybackTypeStr = Constant.TYPE_FINANCING_FQHK;
                break;
            case Constant.STATUS_2:
                paybackTypeStr = Constant.TYPE_FINANCING_YCXHK;
                break;
            case Constant.STATUS_3:
                paybackTypeStr = Constant.TYPE_FINANCING_DQHB;
                break;
            case Constant.STATUS_4:
                paybackTypeStr = Constant.TYPE_FINANCING_TQFX;
                break;
            case Constant.STATUS_5:
                paybackTypeStr = Constant.TYPE_FINANCING_TQFXDQHB;
                break;
        }
        return paybackTypeStr;
    }

    public String flowStatusDesc() {

        if (scales >= 1) {
            return "投资已结束";
        } else {
            return "立即投资";
        }
    }

    public boolean buttonEnable() {
        return !(scales >= 1);
    }
}
