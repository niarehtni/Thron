package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.module.account.activity.InvestmentDetailAct;
import com.rd.zhongqipiaoetong.module.account.fragment.FinancialRecordFrag;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/28/16.
 * <p/>
 * Description: 充值提现记录的VM({@link FinancialRecordFrag})
 */
public class CashRecordMo {

    /**
     * 投标ID
     */
    private String borrowTenderId;
    /**
     * 借款标名称
     */
    private String name;
    /**
     * 债权总额
     */
    private String amount;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 状态
     */
    private int    status;
    /**
     * 投资状态（文本）
     */
    private String statusStr;
    private String tenderId;

    private String borrowName;

    private String capital;
    private String addTime;

    private String id;

    //--------------------------华丽分割线----------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBorrowTenderId() {
        return borrowTenderId;
    }

    public void setBorrowTenderId(String borrowTenderId) {
        this.borrowTenderId = borrowTenderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getStatusStr() {

        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    /**
     * 点击事件
     */
    public void onItemClick(View view) {

        // TODO 页面跳转
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TRANSFERTYPE, Constant.NUMBER_1);
        intent.putExtra(BundleKeys.INVESTMENT_ID, id);
        ActivityUtils.push(InvestmentDetailAct.class, intent);
    }
}
