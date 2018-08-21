package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.activity.JieKuanDetailAct;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.io.Serializable;

/**
 * Created by pzw on 2018/1/22.
 */
public class JieKuanMo extends BaseObservable implements Serializable {
    //借款金额
    private String  amountBorrow;
    //已投金额：截标时为截标金额
    private String  amountInvested;
    //标的编号
    private String  id;
    //借款标题
    private String  name;
    //进度比例
    private String  progressPercentage;
    //标状态 0待初审;1招标中(初审通过);2初审未通过;3满标待审;4还款中;5复审未通过;6已撤回;
    private String  status;
    private boolean statusColor;

    public boolean getStatusColor() {
        return "2".equals(status) || "5".equals(status);
    }

    public String getAmountBorrow() {
        return amountBorrow;
    }

    public void setAmountBorrow(String amountBorrow) {
        this.amountBorrow = amountBorrow;
    }

    public String getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(String amountInvested) {
        this.amountInvested = amountInvested;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(String progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getStatus() {
        return DisplayFormat.getStatus(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void click(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.ID, id);
        ActivityUtils.push(JieKuanDetailAct.class, intent);
    }
}
