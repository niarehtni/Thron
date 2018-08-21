package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Context;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/27 下午6:10
 * <p/>
 * Description:
 */
public class AutoTenderDetailMo {
    /**
     * addTime : 1469153445000
     * aprDown : 4
     * enable : true
     * id : 9
     * max : 0
     * min : 0
     * money : 1000
     * style : 2, 1, 3
     * tenderStyle : 2
     * timelimitDown : 1
     * timelimitUp : 4
     * updateTime : 1469606827000
     */
    private String  addTime;
    private String  aprDown;
    private boolean authorizated;
    private String  authorizeType;
    private boolean enable;
    private int     id;
    private String  max;
    private String  min;
    private String  money;
    private String  style;
    private int     tenderStyle;
    private int     timelimitDown;
    private int     timelimitUp;
    private String  updateTime;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAprDown() {
        return aprDown;
    }

    public void setAprDown(String aprDown) {
        this.aprDown = aprDown;
    }

    public boolean isAuthorizated() {
        return authorizated;
    }

    public void setAuthorizated(boolean authorizated) {
        this.authorizated = authorizated;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        if (this.enable != enable) {
            this.enable = enable;
            initDat();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getTenderStyle() {
        return tenderStyle;
    }

    public void setTenderStyle(int tenderStyle) {
        this.tenderStyle = tenderStyle;
    }

    public int getTimelimitDown() {
        return timelimitDown;
    }

    public void setTimelimitDown(int timelimitDown) {
        this.timelimitDown = timelimitDown;
    }

    public int getTimelimitUp() {
        return timelimitUp;
    }

    public void setTimelimitUp(int timelimitUp) {
        this.timelimitUp = timelimitUp;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private void initDat() {
        money = "";
        max = "";
        min = "";
        timelimitUp = 0;
        timelimitDown = 1;
        aprDown = "0";
        style = "1,2,3";
    }

    /**
     * 投资金额类型状态 是否开启
     *
     * @return
     */
    public boolean isTendStyle(int tendstyle) {
        if (enable && tenderStyle == tendstyle)
            return true;
        return false;
    }

    /**
     * 还款方式 str
     *
     * @return
     */
    public String getStyleStr() {
        String[] repaymentStyle = style.split(",");
        String   str            = "";
        for (String repayment : repaymentStyle) {
            str = str + DisplayFormat.getRepayMentStr(repayment.trim()) + ",";
        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * 某还款方式  是否开启
     *
     * @return
     */
    public boolean isStyle(Object type) {
        return enable && isStyleContains(type);
    }

    /**
     * 是否包含 某还款方式
     *
     * @return
     */
    public boolean isStyleContains(Object type) {
        return style.contains(String.valueOf(type));
    }

    /**
     * 删除 某还款方式
     *
     * @return
     */
    public void removeStyle(Object type) {
        if (style.contains(String.valueOf(type) + ","))
            style = style.replace(String.valueOf(type) + ",", "");
        else if (style.contains(String.valueOf(type)))
            style = style.replace(String.valueOf(type), "");
        deleteStyle();
    }

    /**
     * 添加 某还款方式
     *
     * @return
     */
    public void addStyle(Object type) {
        if (!style.contains(String.valueOf(type)))
            style = style + "," + String.valueOf(type);
        deleteStyle();
    }

    /**
     * 删除style 前后 “，”
     *
     * @return
     */
    public void deleteStyle() {
        style = style.trim();
        if (style.length() > 1) {
            if (style.substring(0, 1).equals(",")) {
                style = style.substring(1, style.length());
            }
            if (style.substring(style.length() - 1, style.length()).equals(",")) {
                style = style.substring(0, style.length() - 1);
            }
        }
    }

    /**
     * 投资金额Style str
     *
     * @return
     */
    public String getTenderStyleStr() {
        Context context = ActivityUtils.peek();
        switch (tenderStyle) {
            case 1:
                return context.getString(R.string.auto_detail_money_type1);
            case 2:
                return context.getString(R.string.auto_detail_money_type2) + DisplayFormat.doubleMoney(money);
            case 3:
                return context.getString(R.string.auto_detail_money_type3) + DisplayFormat.doubleFormat(min) + "-" + DisplayFormat.doubleMoney(max);
            default:
                return context.getString(R.string.auto_detail_type);
        }
    }

    /**
     * 投资时间 str
     *
     * @return
     */
    public String getTimeStr() {
        Context context = ActivityUtils.peek();
        if (isTime()) {
            return context.getString(R.string.auto_detail_type);
        }
        return timelimitDown + "-" + timelimitUp + context.getString(R.string.month2);
    }

    /**
     * 投资时间是否 不限
     *
     * @return
     */
    public boolean isTime() {
        return timelimitUp <= 0;
    }

    public boolean isTime(boolean isOpen) {
        if (enable) {
            if (isOpen) {
                return !isTime();
            } else {
                return isTime();
            }
        } else {
            return false;
        }
    }

    /**
     * apr str
     *
     * @return
     */
    public String getAprStr() {
        Context context = ActivityUtils.peek();
        if (isApr()) {
            return context.getString(R.string.auto_detail_type);
        }
        return context.getString(R.string.auto_detail_apr_type) + DisplayFormat.doubleFormat(aprDown) + context.getString(R.string.unit_percent);
    }

    /**
     * apr是否 不限
     *
     * @return
     */
    public boolean isApr() {
        String apr = aprDown;
        if (aprDown.isEmpty()) {
            apr = "1.1";
        }
        try {
            return Double.valueOf(apr) <= 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * apr是否 不限
     *
     * @return
     */
    public boolean isApr(boolean isOpen) {
        if (enable) {
            if (isOpen) {
                return !isApr();
            } else {
                return isApr();
            }
        } else {
            return false;
        }
    }
}
