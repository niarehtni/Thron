package com.rd.zhongqipiaoetong.module.product.model;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/7 17:44
 * <p/>
 * Description:债券产品MO
 */
public class CreditorMo extends ProjectInfo {
    /**
     * 剩余期限
     */
    private int    remainDays;
    /**
     * 债权原价
     */
    private String oldPrice;
    /**
     * 转让价格
     */
    private String nowPrice;
    /**
     * 折让率
     */
    private String borrowApr;
    /**
     * 进度
     */
    private double sellingProgress;

    private String borrowName;

    private String discountRate;

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    /**
     * 状态
     * 1-转让中->立即购买
     * 2-已转让->已售罄 3-已撤回 -----不用显示
     */


    @Override
    protected void definitionStatus(String status) {
    }

    @Override
    protected void definitionType(String type) {
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public double getSellingProgress() {
        return sellingProgress;
    }

    public void setSellingProgress(double sellingProgress) {
        this.sellingProgress = sellingProgress;
    }

    /**
     * 判断是否售罄
     * 根据progress是否为100
     * @return
     */
    public boolean isSoldOut() {
        return sellingProgress >= 100;
    }
}