package com.rd.zhongqipiaoetong.module.product.model;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;

import com.rd.zhongqipiaoetong.module.product.viewmodel.FinancingListVM;
import com.rd.zhongqipiaoetong.utils.Converter;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/1 15:20
 * <p/>
 * Description: 理财产品MO({@link FinancingListVM})
 */
public class FinancingMo extends ProjectInfo {
    /**
     * 是否新手标 0普通标 1新手标
     */
    private int category;
    /**
     * 起投价格
     */
    private double investMin;
    /**
     * 最大投标金额
     */
    private double  most_account;
    /**
     * 倒计时时间
     */
    private String  timeToStart;
    /**
     * 还款方式
     */
    private String  style;
    /**
     * 还款方式 - 释义
     */
    private String  paybackTypeStr;

    private String rateYearMin;

    private int classify;

    private int isDirect;
    ///////////////////////////////////////////////////////////////////////////
    // 业务逻辑属性
    ///////////////////////////////////////////////////////////////////////////
    /**
     * 是否已售罄
     */
    //private String status;
    /**
     * 是否显示邮戳
     */
    private boolean isShowStamp;

    public int getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(int isDirect) {
        this.isDirect = isDirect;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    /**
     * 标状态 1-待售中->显示倒计时时间，
     * 2-投资中->我要投资，
     * 3-满标待审->已售罄，
     * 4-还款中->已售罄，
     * 5-已还款->已售罄，
     * 6-流标->已售罄，
     * 7-截标->已售罄，
     * 8-撤回->不显示按钮
     */
    @Override
    protected void definitionStatus(String status) {
    }

    @Override
    protected void definitionType(String type) {
    }

    public String getRateYear() {
        return rateYear;
    }

    public String getRateYearMin() {
        return rateYearMin;
    }

    public void setRateYearMin(String rateYearMin) {
        this.rateYearMin = rateYearMin;
    }

    public void setRateYear(String rateYear) {
        this.rateYear = rateYear;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getInvestMin() {
        return investMin;
    }

    public void setInvestMin(double investMin) {
        this.investMin = investMin;
    }

    public double getMost_account() {
        return most_account;
    }

    public void setMost_account(double most_account) {
        this.most_account = most_account;
    }

    public String getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(String timeToStart) {
        this.timeToStart = timeToStart;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPaybackTypeStr() {
        return paybackTypeStr;
    }

    public void setPaybackTypeStr(String paybackTypeStr) {
        this.paybackTypeStr = paybackTypeStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSoldOut() {
        //        if (TextUtils.isEmpty(status)) {
        //            status = "0";
        //        }
        //        int status = Integer.valueOf(this.status);
        //        return status >= 3 && status <= 7;
        return Double.valueOf(progressPercentage) < 100 ? false : true;
    }

    public boolean isShowStamp() {
        if (TextUtils.isEmpty(status)) {
            status = "0";
        }
        int status = Integer.valueOf(this.status);
        return !(status == 2 || status == 8);
    }

    public boolean isDay() {
        if (timeType.equals("0"))
            return false;
        return true;
    }

    public String getLimit() {
        return DisplayFormat.doubleFormat(getInvestMin()) + "元 ~ " + (getMost_account() != 0.0 ? DisplayFormat.doubleFormat(getMost_account()) + "元" : "无限制");
    }

    public String getStratTenderTime() {
        Long startTime = Long.valueOf(getTimeToStart()) + Long.valueOf(getAddTime());
        return String.valueOf(startTime);
    }

    public String getTimeType() {
        return isDay() ? "项目期限(天)" : "项目期限(月)";
    }

    public Spannable showRateYearMin() {
        String str = "";
        if(Converter.getDouble(rateYearMin) > 0){
            if (Converter.getDouble(platformRateYear) != 0) {
                str = DisplayFormat.doubleFormat(rateYearMin) + "%" + "起+" + DisplayFormat.doubleFormat(platformRateYear) + "%";
                Spannable span  = new SpannableString(str);
                int       index = str.indexOf(".");
                span.setSpan(new AbsoluteSizeSpan(14, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                return span;
            } else {
                str = DisplayFormat.doubleFormat(rateYearMin) + "%" + "起";
                Spannable span  = new SpannableString(str);
                int       index = str.indexOf(".");
                span.setSpan(new AbsoluteSizeSpan(14, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                return span;
            }
        }
        if (Converter.getDouble(platformRateYear) != 0) {
            str = DisplayFormat.doubleFormat(rateYear) + "%" + "+" + DisplayFormat.doubleFormat(platformRateYear) + "%";
        } else {
            str = DisplayFormat.doubleFormat(rateYear) + "%";
        }
        Spannable span  = new SpannableString(str);
        int       index = str.indexOf(".");
        span.setSpan(new AbsoluteSizeSpan(14, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return span;
    }

}