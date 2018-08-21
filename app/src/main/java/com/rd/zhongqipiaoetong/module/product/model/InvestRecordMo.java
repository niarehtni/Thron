package com.rd.zhongqipiaoetong.module.product.model;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/2/24 11:39
 * <p/>
 * Description:投资记录
 */
public class InvestRecordMo {
    /**
     * 投资者
     */
    private String username;
    /**
     * 投资时间
     */
    private String addTime;
    /**
     * 投资金额
     */
    private String capital;
    /**
     * 是否使用红包投资
     */
    private double usedRedMoney;
    /**
     * 是否使用体验券投资
     */
    private double usedExperienceMoney;
    /**
     * 是否使用加息券投资
     */
    private double usedUpRate;

    /**
     * 是否显示投资使用情况
     * @return
     */
    private boolean isshow;

    public boolean isshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }

    public double getUsedUpRate() {
        return usedUpRate;
    }

    public void setUsedUpRate(double usedUpRate) {
        this.usedUpRate = usedUpRate;
    }

    public double getUsedExperienceMoney() {

        return usedExperienceMoney;
    }

    public void setUsedExperienceMoney(double usedExperienceMoney) {
        this.usedExperienceMoney = usedExperienceMoney;
    }

    public double getUsedRedMoney() {

        return usedRedMoney;
    }

    public void setUsedRedMoney(double usedRedMoney) {
        this.usedRedMoney = usedRedMoney;
    }

    public InvestRecordMo() {
    }

    public InvestRecordMo(String name, String time, String amount) {
        this.username = name;
        this.addTime = time;
        this.capital = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public boolean isShowIcon(){

        if(usedRedMoney == 0 && usedExperienceMoney == 0 && usedUpRate == 0){
            return false;
        }
        return true;
    }
}