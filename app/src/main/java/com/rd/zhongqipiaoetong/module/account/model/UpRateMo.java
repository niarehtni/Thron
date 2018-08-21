package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.module.account.viewmodel.MyCouponVM;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.io.Serializable;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 * Description: 加息券({@link MyCouponVM})信息
 */
public class UpRateMo extends BaseObservable implements Serializable {
    /**
     * CheckBox是否选中
     */
    private boolean isCheck;
    /**
     * 加息券ID
     */
    private String  id;
    /**
     * 名称
     */
    private String  name;
    /**
     * 加息幅度
     */
    private String  upApr;
    /**
     * 添加时间
     */
    private String  addTime;
    /**
     * 使用时间
     */
    private String  usedTime;
    /**
     * 截止时间
     */
    private String  expireTime;
    /**
     * 状态：1已使用，0未使用，2过期，3无效
     */
    private String  status;
    /**
     * 使用说明
     */
    private String  condition;

    private String upRate;

    /**使用状态 0未使用 2冻结 3已过期 5已使用*/
    private String usedStatus;

    /** 加息天数 0:全程加息 N:加息天数 */
    private String income;

    private String expiredTime;

    /** 收益时间(兑现时间) */
    private String paymentTime;


    //--------------------------华丽分割线----------------------------------

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getUpRate() {
        return upRate;
    }

    public void setUpRate(String upRate) {
        this.upRate = upRate;
    }

    public String getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(String usedStatus) {
        this.usedStatus = usedStatus;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
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

    public String getUpApr() {
        return upApr;
    }

    public void setUpApr(String upApr) {
        this.upApr = upApr;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Bindable
    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
        notifyPropertyChanged(BR.isCheck);
    }

    public Drawable getDrawable() {
        Context context = ActivityUtils.peek();
        switch (usedStatus) {
            case Constant.STATUS_5:
                return context.getResources().getDrawable(R.drawable.coupon_used);
            case Constant.STATUS_0:
                if (!isExpired()) {
                    return null;
                }
            case Constant.STATUS_3:
                return context.getResources().getDrawable(R.drawable.coupon_expired);
            case Constant.STATUS_2:
                return context.getResources().getDrawable(R.drawable.coupon_invalid);
            default:
                return null;
        }
    }

    private boolean isExpired() {
        return System.currentTimeMillis() >= DisplayFormat.stringToLong(expiredTime);
    }

    public boolean enabled() {
        return usedStatus.equals(Constant.STATUS_0) && !isExpired();
    }
}
