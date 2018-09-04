package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.util.List;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/28/16.
 */
public class InvestRecordDetailMo {
    /**
     * 标的ID
     */
    private String id;
    private String borrowId;
    /**
     * 标的名称
     */
    private String borrowName;
    /**
     * 标的金额
     */
    private String capital;
    /**
     * 投资时间
     */
    private String addTime;
    /**
     * 计息起始日
     */
    private String interestStartTime;
    /**
     * 到期日
     */
    private String paymentTime;
    /**
     * 投资状态: 0投标待中，1待回款，2已结算
     */
    private int status;
    /**
     * 产品利率
     */
    private String rateYear;
    /**
     * 还款方式: 1按月分期还款; 2一次性还款;3每月还息到期还本
     */
    private String repayWay;
    /**
     * 还款方式 - 释义
     */
    private String paybackTypeStr;
    /**
     * 优惠金额
     */
    private String discount;
    /**
     * 总收益
     */
    private String interest;
    /**
     * 已收收益
     */
    private String repaidInterest;
    /**
     * 待收收益
     */
    private String waitInterest;
    /**
     * 合同链接
     */
    private String protrcolUrl;
    /**
     * 投资状态（文本）
     */
    private String statusStr;
    private List<InvestRecordDetailCollectionItemMo> returnList;
    private int classify; //0:普通标 1:浮动标 2:即投即息标  3:活期理财 4:体验标
    private String rateYearMin; //最低年利率
    private int isRegionConfirm; //利率范围是否确定
    private String progressPercentage; //进度
    private int timeLimit; //期限
    private int timeType; //期限类型
    private String interestMin; //最低收益
    private String platformInterest; //奖励收益
    private String repaidCapital; //已收本金
    private String repaidAmount;

    //--------------------------华丽分割线----------------------------------


    public String getRepaidAmount() {
        return repaidAmount;
    }

    public void setRepaidAmount(String repaidAmount) {
        this.repaidAmount = repaidAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getInterestStartTime() {
        return interestStartTime;
    }

    public void setInterestStartTime(String interestStartTime) {
        this.interestStartTime = interestStartTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRateYear() {
        return rateYear;
    }

    public void setRateYear(String rateYear) {
        this.rateYear = rateYear;
    }

    public String getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(String repayWay) {
        this.repayWay = repayWay;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getRepaidInterest() {
        return repaidInterest;
    }

    public void setRepaidInterest(String repaidInterest) {
        this.repaidInterest = repaidInterest;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(String waitInterest) {
        this.waitInterest = waitInterest;
    }

    public String getProtrcolUrl() {
        return protrcolUrl;
    }

    public void setProtrcolUrl(String protrcolUrl) {
        this.protrcolUrl = protrcolUrl;
    }

    public List<InvestRecordDetailCollectionItemMo> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<InvestRecordDetailCollectionItemMo> returnList) {
        this.returnList = returnList;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public String getRateYearMin() {
        return rateYearMin;
    }

    public void setRateYearMin(String rateYearMin) {
        this.rateYearMin = rateYearMin;
    }

    public int getIsRegionConfirm() {
        return isRegionConfirm;
    }

    public void setIsRegionConfirm(int isRegionConfirm) {
        this.isRegionConfirm = isRegionConfirm;
    }

    public String getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(String progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public String getInterestMin() {
        return interestMin;
    }

    public void setInterestMin(String interestMin) {
        this.interestMin = interestMin;
    }

    public String getPlatformInterest() {
        return platformInterest;
    }

    public void setPlatformInterest(String platformInterest) {
        this.platformInterest = platformInterest;
    }

    public String getRepaidCapital() {
        return repaidCapital;
    }

    public void setRepaidCapital(String repaidCapital) {
        this.repaidCapital = repaidCapital;
    }

    public long getNextPayTime() {
        long time = 0;
        for (InvestRecordDetailCollectionItemMo item : returnList) {
            if (item.getStatus() == 0) {
                long itemTimeLimit = item.getRepaymentTime();
                time = (time == 0 || time > itemTimeLimit) ? itemTimeLimit : time;
            }
        }
        return time;
    }

    public int getNoPayPeriod() {
        int num = 0;
        for (InvestRecordDetailCollectionItemMo item : returnList) {
            if (item.getStatus() == 0) {
                num++;
            }
        }
        return num;
    }

    //利率显示
    public String showRate() {
        if (classify == 1 && isRegionConfirm == 0) {
            return DisplayFormat.doubleFormat(rateYearMin) + "~" + DisplayFormat.doubleFormat(rateYear) + "%";
        }
        if (classify != 1 || isRegionConfirm == 1) {
            return DisplayFormat.doubleFormat(rateYear) + "%";
        }
        return DisplayFormat.doubleFormat(rateYear) + "%";
    }

    //期限显示
    public String showTimeLimit() {
        return timeLimit + (timeType == 1 ? "天" : "个月");
    }

    //显示预估待收收益
    public String showExpectInterest() {
        if (classify == 1 && isRegionConfirm == 0) {
            return DisplayFormat.doubleMoney(Double.parseDouble(interestMin)+Double.parseDouble(platformInterest)) + "~" + DisplayFormat.doubleMoney(Double.parseDouble(waitInterest)+Double.parseDouble(platformInterest));
        }
        if (classify != 1 || isRegionConfirm == 1) {
            return DisplayFormat.doubleMoney(Double.parseDouble(waitInterest));
        }
        return DisplayFormat.doubleMoney(Double.parseDouble(waitInterest));
    }

    public String showFailMark() {
        if (status == 3) {
            return "复审不通过";
        }
        if (status == 4) {
            return "投资撤回";
        }
        return "";
    }

    public String showCapital() {
        if (classify == 4) {
            return "体验金";
        } else {
            return "投资金额";
        }
    }
}
