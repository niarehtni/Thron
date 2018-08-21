package com.rd.zhongqipiaoetong.module.account.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.zhongqipiaoetong.BR;

import java.io.Serializable;

/**

 * <p/>
 * Description: 体验券信息
 */
public class ExpMo extends BaseObservable implements Serializable{
    /**
     * ID
     */
    private String  id;
    /**
     * 金额
     */
    private double  amount;
    /**
     * 状态
     */
    private String  status;
    /**
     * 添加时间
     */
    private String  addTime;
    /**
     * 使用时间
     */
    private String  usedTime;
    /**
     * 过期时间
     */
    private String  expireTime;
    /**
     * 红包名称
     */
    private String  name;

    ///////////////////////////////////////////////////////////////////////////
    // 业务逻辑处理属性
    ///////////////////////////////////////////////////////////////////////////
    /**
     * CheckBox是否选中
     */
    private boolean isCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Bindable
    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
        notifyPropertyChanged(BR.isCheck);
    }
}