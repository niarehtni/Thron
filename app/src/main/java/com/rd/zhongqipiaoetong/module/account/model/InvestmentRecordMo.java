package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Intent;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.activity.InvestmentDetailAct;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/26/16.
 */
public class InvestmentRecordMo {
        /**
         * 标的ID
         */
        private String id;
        /**
         * 标的名称
         */
        private String borrowName;
        /**
         * t投资金额
         */
        private String capital;
        /**
         * 投资时间
         */
        private String addTime;
        /**
         * 投资状态 0:投标待处理 1:待回款 2:已结算
         * 3:投资失败(复审不通过) 4:投资失败(投资撤回)"
         */
        private String status;
        private String statusStr;
        /**
         * 待收收益
         */
        private String interest;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
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
        intent.putExtra(BundleKeys.INVESTMENT_ID, id);
        ActivityUtils.push(InvestmentDetailAct.class, intent);
    }
}
