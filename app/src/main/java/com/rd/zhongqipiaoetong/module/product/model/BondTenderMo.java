package com.rd.zhongqipiaoetong.module.product.model;

import android.databinding.BaseObservable;

import com.rd.zhongqipiaoetong.module.account.model.TransferMo;

import java.io.Serializable;

/**
 * <p/>
 * Description: 债券投标信息()
 */
public class BondTenderMo extends BaseObservable implements Serializable{
    private double     totalInterest;
    private double     balanceAvailable;
    private TransferMo bondRes;

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(double balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public TransferMo getBondRes() {
        return bondRes;
    }

    public void setBondRes(TransferMo bondRes) {
        this.bondRes = bondRes;
    }
}