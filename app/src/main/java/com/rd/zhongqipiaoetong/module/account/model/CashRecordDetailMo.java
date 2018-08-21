package com.rd.zhongqipiaoetong.module.account.model;

import java.util.List;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/6/16.
 */
public class CashRecordDetailMo {

    private String borrowName;
    private int status;
    private double investMoney;
    private String discountRate;
    private double discount;
    private double boughtPrice;
    private long addTime;
    private double rateYear;
    private double receivedInterest;
    private double waitInterest;
    private double repaidAmount;
    private int repayWay;
    private long nextRepayTime;
    private int period;
    private int totalPeriod;
    private String bondId;
    private List<InvestRecordDetailCollectionItemMo> returnList;
    private String bondInvestmentId;

    //----------------------------------------------

    public String getBondInvestmentId() {
        return bondInvestmentId;
    }

    public void setBondInvestmentId(String bondInvestmentId) {
        this.bondInvestmentId = bondInvestmentId;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(double investMoney) {
        this.investMoney = investMoney;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(double boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public double getRateYear() {
        return rateYear;
    }

    public void setRateYear(double rateYear) {
        this.rateYear = rateYear;
    }

    public double getReceivedInterest() {
        return receivedInterest;
    }

    public void setReceivedInterest(double receivedInterest) {
        this.receivedInterest = receivedInterest;
    }

    public double getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(double waitInterest) {
        this.waitInterest = waitInterest;
    }

    public double getRepaidAmount() {
        return repaidAmount;
    }

    public void setRepaidAmount(double repaidAmount) {
        this.repaidAmount = repaidAmount;
    }

    public int getRepayWay() {
        return repayWay;
    }

    public void setRepayWay(int repayWay) {
        this.repayWay = repayWay;
    }

    public long getNextRepayTime() {
        return nextRepayTime;
    }

    public void setNextRepayTime(long nextRepayTime) {
        this.nextRepayTime = nextRepayTime;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getTotalPeriod() {
        return totalPeriod;
    }

    public void setTotalPeriod(int totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public List<InvestRecordDetailCollectionItemMo> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<InvestRecordDetailCollectionItemMo> returnList) {
        this.returnList = returnList;
    }

    public String getBondId() {
        return bondId;
    }

    public void setBondId(String bondId) {
        this.bondId = bondId;
    }

    public String getStatusStr(){
        if(status == 1){
            return "已还款";
        }
        return "未还款";
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

}
