/**
 * banner模型
 */
package com.rd.zhongqipiaoetong.module.homepage.model;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import com.rd.zhongqipiaoetong.utils.Converter;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

/**
 * @author yoseflin
 *         banner模型
 */
public class HomeModel {
    private TenderItem tenderItem;

    public TenderItem getTenderItem() {
        return tenderItem;
    }

    public void setTenderItem(TenderItem tenderItem) {
        this.tenderItem = tenderItem;
    }
    public boolean isDay(){
        return tenderItem.getTimeType() == 1;
    }

    public class TenderItem{
        //借款金额
        private String amountBorrow;
        //预期年化收益
        private double rateYear;
        //加入时间*/
        private long add_time;
        //奖励金额
        private String award;
        //自增ID
        private String id;
        //最低投标金额
        private long investMin;
        //产品名称
        private String name;
        //进度
        private double scales;

        //产品状态 信托状态 0上架;1下架;标状态  -1已撤回;0等待初审;1初审通过;2初审未通过;3复审通过;4/49复审未通过;
        //5/59用户取消;6还款中;7部分还款;8还款成功
        private int status;
        //付息方式 1等额本息 2一次性还款 3每月付息
        private int style;
        //项目期限
        private String timeLimit;
        //期限类型
        private int timeType;
        //标类型 101秒换标 102信用标 103抵押标 104净值标 110流转标 112担保标 120定期理财
        private int type;
        //产品id
        private long uuid;
        //投资笔数
        private int investedCount;
        private double platformRateYear;
        //是否新手标判断
        private int category;
        //年化收益率的显示类型判断
        private int classify;
        private String isRegionConfirm;
        //最低收益率
        private String rateYearMin;

        public int getTimeType() {
            return timeType;
        }

        public void setTimeType(int timeType) {
            this.timeType = timeType;
        }

        public String getAmountBorrow() {
            return amountBorrow;
        }

        public void setAmountBorrow(String amountBorrow) {
            this.amountBorrow = amountBorrow;
        }

        public double getRateYear() {
            return rateYear;
        }

        public void setRateYear(double rateYear) {
            this.rateYear = rateYear;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getAward() {
            return award;
        }

        public void setAward(String award) {
            this.award = award;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getInvestMin() {
            return investMin;
        }

        public void setInvestMin(long investMin) {
            this.investMin = investMin;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScales() {
            return scales;
        }

        public void setScales(double scales) {
            this.scales = scales;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public long getUuid() {
            return uuid;
        }

        public void setUuid(long uuid) {
            this.uuid = uuid;
        }

        public int getInvestedCount() {
            return investedCount;
        }

        public void setInvestedCount(int investedCount) {
            this.investedCount = investedCount;
        }

        public double getPlatformRateYear() {
            return platformRateYear;
        }

        public void setPlatformRateYear(double platformRateYear) {
            this.platformRateYear = platformRateYear;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getClassify() {
            return classify;
        }

        public void setClassify(int classify) {
            this.classify = classify;
        }

        public String getIsRegionConfirm() {
            return isRegionConfirm;
        }

        public void setIsRegionConfirm(String isRegionConfirm) {
            this.isRegionConfirm = isRegionConfirm;
        }

        public String getRateYearMin() {
            return rateYearMin;
        }

        public void setRateYearMin(String rateYearMin) {
            this.rateYearMin = rateYearMin;
        }

        public Spannable showRateYearMin(){
            String str = "";
            if(Converter.getDouble(rateYearMin) > 0){
                if (platformRateYear != 0) {
                    str = DisplayFormat.doubleFormat(rateYearMin) + "%" + "起+" + DisplayFormat.doubleFormat(platformRateYear) + "%";
                    Spannable span  = new SpannableString(str);
                    int       index = str.indexOf(".");
                    span.setSpan(new AbsoluteSizeSpan(40, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    return span;
                } else {
                    str = DisplayFormat.doubleFormat(rateYearMin) + "%" + "起";
                    Spannable span  = new SpannableString(str);
                    int       index = str.indexOf(".");
                    span.setSpan(new AbsoluteSizeSpan(40, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    return span;
                }
            }
            if (platformRateYear != 0) {
                str = DisplayFormat.doubleFormat(rateYear) + "%" + "+" + DisplayFormat.doubleFormat(platformRateYear) + "%";
            } else {
                str = DisplayFormat.doubleFormat(rateYear) + "%";
            }
            Spannable span  = new SpannableString(str);
            int       index = str.indexOf(".");
            span.setSpan(new AbsoluteSizeSpan(40, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return span;
        }
    }
}