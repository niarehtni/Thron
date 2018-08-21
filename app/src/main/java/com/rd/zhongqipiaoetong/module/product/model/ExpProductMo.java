package com.rd.zhongqipiaoetong.module.product.model;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.module.account.model.ExperienceMo;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.util.ArrayList;

public class ExpProductMo {
    private ExpProduct expBorrowDetail;

    public ExpProduct getExpBorrowDetail() {
        return expBorrowDetail;
    }

    public void setExpBorrowDetail(ExpProduct expBorrowDetail) {
        this.expBorrowDetail = expBorrowDetail;
    }

    public class ExpProduct {
        private String                  id;
        private String                  name;
        private String                  rate;
        private double                  rateYear;
        private long                    borrowTime;
        private int                     timeType;
        private int                     timeLimit;
        private String                  repayWayTranslate;
        private String                  rateYearTranslate;
        private String                  openTime;
        private String                  repayWay;
        private String                  repayWayStr;
        private int                     investedCount;
        private ArrayList<ExperienceMo> ticketExpList;
        private double                  progressPercentage;
        private int                     status;
        private double                  amountInvestable;
        private double                  amountBorrow;
        private double investMin;

        public double getInvestMin() {
            return investMin;
        }

        public void setInvestMin(double investMin) {
            this.investMin = investMin;
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

        public double getRateYear() {
            return rateYear;
        }

        public void setRateYear(double rateYear) {
            this.rateYear = rateYear;
        }

        public long getBorrowTime() {
            return borrowTime;
        }

        public void setBorrowTime(long borrowTime) {
            this.borrowTime = borrowTime;
        }

        public int getTimeType() {
            return timeType;
        }

        public void setTimeType(int timeType) {
            this.timeType = timeType;
        }

        public int getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(int timeLimit) {
            this.timeLimit = timeLimit;
        }

        public String getRepayWayTranslate() {
            return repayWayTranslate;
        }

        public void setRepayWayTranslate(String repayWayTranslate) {
            this.repayWayTranslate = repayWayTranslate;
        }

        public String getRateYearTranslate() {
            return rateYearTranslate;
        }

        public void setRateYearTranslate(String rateYearTranslate) {
            this.rateYearTranslate = rateYearTranslate;
        }

        public String getRate() {
            return DisplayFormat.doubleFormat(rateYear) + "%";
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public double getAmountInvestable() {
            return amountInvestable;
        }

        public void setAmountInvestable(double amountInvestable) {
            this.amountInvestable = amountInvestable;
        }

        public String getRepayWay() {
            return repayWay;
        }

        public String getRepayWayStr() {
            switch (repayWay) {
                case Constant.STATUS_1:
                    repayWayStr = Constant.TYPE_FINANCING_FQHK;
                    break;
                case Constant.STATUS_2:
                    repayWayStr = Constant.TYPE_FINANCING_YCXHK;
                    break;
                case Constant.STATUS_3:
                    repayWayStr = Constant.TYPE_FINANCING_DQHB;
                    break;
                case Constant.STATUS_4:
                    repayWayStr = Constant.TYPE_FINANCING_TQFX;
                    break;
                case Constant.STATUS_5:
                    repayWayStr = Constant.TYPE_FINANCING_TQFXDQHB;
                    break;
            }
            return repayWayStr;
        }

        public int getInvestedCount() {
            return investedCount;
        }

        public ArrayList<ExperienceMo> getTicketExpList() {
            return ticketExpList;
        }

        public void setTicketExpList(ArrayList<ExperienceMo> ticketExpList) {
            this.ticketExpList = ticketExpList;
        }

        public String getTimetypeStr() {
            return isDay() ? "项目期限(天)" : "项目期限(月)";
        }

        public double getProgressPercentage() {
            return progressPercentage;
        }

        public void setProgressPercentage(double progressPercentage) {
            this.progressPercentage = progressPercentage;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getAmountBorrow() {
            return amountBorrow;
        }

        public void setAmountBorrow(double amountBorrow) {
            this.amountBorrow = amountBorrow;
        }

        public String getStatusStr() {
            String statusStr = "";
            switch (status) {
                case 0:
                case 9:
                case 10:
                case 11:
                case 12:
                    statusStr = "待初审";
                    break;
                case 1:
                    statusStr = "立即投资";
                    break;
                case 2:
                case 3:
                case 4:
                case 7:
                    statusStr = "招标结束";
                    break;
                case 5:
                case 6:
                    statusStr = "招标失败";
                    break;
                case 8:
                    statusStr = "已还款";
                    break;
            }
            return statusStr;
        }

        public boolean canInvest() {
            return progressPercentage != 100 && status == 1;
        }

        public int getProgress() {
            return (int) progressPercentage;
        }

        public boolean hasExp() {
            return ticketExpList != null && ticketExpList.size() > 0;
        }

        public Spannable showYearRate() {
            String    str   = DisplayFormat.doubleFormat(rateYear) + "%";
            Spannable span  = new SpannableString(str);
            int       index = str.indexOf(".");
            span.setSpan(new AbsoluteSizeSpan(14, true), index, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            return span;
        }

        public boolean isDay() {
            if (timeType == 0)
                return false;
            return true;
        }

        public boolean isSoldOut() {
            return Double.valueOf(progressPercentage) < 100 ? false : true;
        }
    }
}
