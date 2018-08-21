package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.module.account.viewmodel.MyAssetsVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/14 15:51
 * <p/>
 * Description: 账户总览({@link MyAssetsVM})信息
 */
public class AssetsMo {
    /**
     * 冻结金额
     */
    private String noUseMoney;
    /**
     * 可用余额
     */
    private String useMoney;

    /**
     * 待收收益
     */
    private String waitInterest;
    /**
     * 在投金额
     */
    private String investMoney;


    /**
     * 债权转让
     */
    private String transferMoney;
    /**
     * 债权投资待收收益
     */
    private String  toBondToCollectEarnMoney;

    /**
     * 债权投资金额
     */
    private String  bondInvestingAmount;


    /**
     * 总金额
     */
    private String total;

    public String getToBondToCollectEarnMoney() {
        return toBondToCollectEarnMoney;
    }

    public void setToBondToCollectEarnMoney(String toBondToCollectEarnMoney) {
        this.toBondToCollectEarnMoney = toBondToCollectEarnMoney;
    }

    public String getBondInvestingAmount() {
        return bondInvestingAmount;
    }

    public void setBondInvestingAmount(String bondInvestingAmount) {
        this.bondInvestingAmount = bondInvestingAmount;
    }

    public String getNoUseMoney() {
        return noUseMoney;
    }

    public void setNoUseMoney(String noUseMoney) {
        this.noUseMoney = noUseMoney;
    }

    public String getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(String waitInterest) {
        this.waitInterest = waitInterest;
    }

    public String getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(String investMoney) {
        this.investMoney = investMoney;
    }

    public String getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(String useMoney) {
        this.useMoney = useMoney;
    }

    public String getTransferMoney() {
        return transferMoney;
    }

    public void setTransferMoney(String transferMoney) {
        this.transferMoney = transferMoney;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}