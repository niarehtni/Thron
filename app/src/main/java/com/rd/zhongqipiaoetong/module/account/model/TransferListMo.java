package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.network.entity.PageMo;

import java.util.List;

/**
 * Created by pzw on 2017/6/5.
 */
public class TransferListMo   extends PageMo {

    private double           discountRateMin;
    private double           discountRateMax;
    private double           sellFee;
    private String feeType;
    private List<TransferMo> assigmentList;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public double getDiscountRateMin() {
        return discountRateMin;
    }

    public void setDiscountRateMin(double discountRateMin) {
        this.discountRateMin = discountRateMin;
    }

    public double getDiscountRateMax() {
        return discountRateMax;
    }

    public void setDiscountRateMax(double discountRateMax) {
        this.discountRateMax = discountRateMax;
    }

    public double getSellFee() {
        return sellFee;
    }

    public void setSellFee(double sellFee) {
        this.sellFee = sellFee;
    }

    public List<TransferMo> getAssigmentList() {
        return assigmentList;
    }

    public void setAssigmentList(List<TransferMo> assigmentList) {
        this.assigmentList = assigmentList;
    }
}
