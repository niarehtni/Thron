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
 * Description: 体验券({@link MyCouponVM})信息
 */
public class ExperienceMo extends BaseObservable implements Serializable {
    /**
     * CheckBox是否选中
     */
    private boolean isCheck;
    /**
     * 体验券ID
     */
    private String  id;
    /**
     * 体验券金额
     */
    private String  amount;
    /**
     * 名称
     */
    private String  name;
    /**
     * 添加时间
     */
    private String  addTime;
    /**
     * 使用时间
     */
    private String  useTime;
    /**
     * 过期时间
     */
    private String  expiredTime;
    /**
     * 状态：1已使用，0未使用，2过期，3无效
     */
    private String  usedStatus;
    /**
     * 使用说明
     */
    private String  condition;

    //--------------------------华丽分割线----------------------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(String usedStatus) {
        this.usedStatus = usedStatus;
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
        if (usedStatus.equals(Constant.STATUS_1) || usedStatus.equals(Constant.STATUS_5)) {
            return context.getResources().getDrawable(R.drawable.coupon_used);
        } else if (usedStatus.equals(Constant.STATUS_2)) {
            return context.getResources().getDrawable(R.drawable.coupon_dongjie);
        } else if (usedStatus.equals(Constant.STATUS_3)) {
            return context.getResources().getDrawable(R.drawable.coupon_expired);
        } else {
            return null;
        }
    }

    public String getShowTime() {
        if (usedStatus.equals(Constant.STATUS_1) || usedStatus.equals(Constant.STATUS_5)) {
            return "使用时间";
        } else {
            return "过期时间";
        }
    }

    public String getTime() {
        if (usedStatus.equals(Constant.STATUS_1) || usedStatus.equals(Constant.STATUS_5)) {
            return useTime;
        } else {
            return expiredTime;
        }
    }

    private boolean isExpired() {
        return System.currentTimeMillis() >= DisplayFormat.stringToLong(expiredTime);
    }

    public boolean enabled() {
        return usedStatus.equals(Constant.STATUS_0) && !isExpired();
    }
}
