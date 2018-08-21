package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.module.account.viewmodel.MyCouponVM;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.io.Serializable;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 10:02
 * <p/>
 * Description: 优惠券({@link MyCouponVM})信息
 */
public class CouponMo extends BaseObservable implements Serializable {
    /**
     * CheckBox是否选中
     */
    private int choose;
    /**
     * 红包ID
     */
    private String id;
    /**
     * 红包名称
     */
    private String name;
    /**
     * 红包金额
     */
    private String amount;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 使用时间
     */
    private String useTime;
    /**
     * 过期时间
     */
    private String expiredTime;
    /**
     * 状态：0 未使用|未审核 1已使用｜已审核 2冻结 3过期 4 关闭|｜未通过 5已使用
     */
    private String usedStatus;
    /**
     * 红包类型：0:虚拟红包 1:现金红包
     */
    private int type;
    /**
     * 是否能兑换
     */
    private boolean canExcharge;
    private String condition;
    /**
     * 最小使用金额
     */
    private String minCapital;
    //--------------------------华丽分割线----------------------------------
    private View.OnClickListener couponClick;
    private boolean isCheck;

    @Bindable
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        notifyPropertyChanged(BR.check);
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMinCapital() {
        return minCapital;
    }

    public void setMinCapital(String minCapital) {
        this.minCapital = minCapital;
    }

    public String getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(String usedStatus) {
        this.usedStatus = usedStatus;
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

    public boolean isCanExcharge() {
        return canExcharge;
    }

    public void setCanExcharge(boolean canExcharge) {
        this.canExcharge = canExcharge;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
        notifyPropertyChanged(BR.isCheck);
    }

    public String getShowTime() {
        if (type == 1) {
            return "领取时间";
        } else {
            if (usedStatus.equals(Constant.STATUS_1) || usedStatus.equals(Constant.STATUS_5)) {
                return "使用时间";
            } else {
                return "过期时间";
            }
        }
    }

    public String getLimit() {
        return "投资满" + DisplayFormat.doubleFormat(minCapital) + "元可用";
    }

    public String getTime() {
        if (type == 1) {
            return addTime;
        } else {
            if (usedStatus.equals(Constant.STATUS_1) || usedStatus.equals(Constant.STATUS_5)) {
                return useTime;
            } else {
                return expiredTime;
            }
        }
    }

    public Drawable getDrawable() {
        Context context = ActivityUtils.peek();
        if (type == 1) {
            if (usedStatus.equals(Constant.STATUS_0)) {
                return null;
            } else {
                return context.getResources().getDrawable(R.drawable.coupon_ydx);
            }
        } else {
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
    }

    /**
     * 是否过期
     *
     * @return
     */
    private boolean isExpired() {
        return System.currentTimeMillis() >= DisplayFormat.stringToLong(expiredTime);
    }

    /**
     * 未使用，未过期
     *
     * @return
     */
    public boolean enabled() {
        return usedStatus.equals(Constant.STATUS_0);
    }

    /**
     * 是否可兑换   条件  现金红包  && 兑换
     *
     * @return
     */
    public boolean isExchang() {
        return type == 1 && canExcharge;
    }

    public boolean isExchang(boolean isShow) {
        return isShow && isExchang();
    }

    public View.OnClickListener getCouponClick() {
        return couponClick;
    }

    public void setCouponClick(View.OnClickListener couponClick) {
        this.couponClick = couponClick;
    }

    /**
     * 兑换点击事件
     */
    public void exchangClick(View view) {
        couponClick.onClick(view);
    }
}