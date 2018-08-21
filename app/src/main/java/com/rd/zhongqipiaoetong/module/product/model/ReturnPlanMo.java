package com.rd.zhongqipiaoetong.module.product.model;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/1 11:13
 * <p/>
 * Description:还款计划
 */
public class ReturnPlanMo {
    /**
     * 总共期数
     */
    private String allCount;
    /**
     * 还款期数
     */
    private String nowCount;
    /**
     * 还款状态 0-未还 1-已还
     */
    private String status;

    public ReturnPlanMo(String nowCount, String status, String allCount) {
        this.nowCount = nowCount;
        this.status = status;
        this.allCount = allCount;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getNowCount() {
        return nowCount;
    }

    public void setNowCount(String nowCount) {
        this.nowCount = nowCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}