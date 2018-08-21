package com.rd.zhongqipiaoetong.module.product.model;

import com.rd.zhongqipiaoetong.utils.DisplayFormat;

import java.io.Serializable;

/**
 * Created by xyy on 2016/11/2.
 */
public class FinancingDetailMo implements Serializable {
    private BorrowVO borrowVO;

    public BorrowVO getBorrowVO() {
        return borrowVO;
    }

    public void setBorrowVO(BorrowVO borrowVO) {
        this.borrowVO = borrowVO;
    }

    public class BorrowVO implements Serializable{
        //预期年化收益率
        private String rateYear;
        private double platformRateYear;
        //标ID
        private int id;
        private boolean isOwn;
        //是否密码标
        private String is_mb;
        //起投金额
        private String investMin;
        //最大可投
        private double investMax;
        //标的名称
        private String name;
        //标的进度
        private String progressPercentage;
        //标的状态
        private String status;
        //项目的投资笔数
        private int investedCount;
        //项目期限
        private String timeLimit;
        //标的类别
        private int type;
        //期限类型
        private String timeType;
        private String borrow_type;
        //1新手标0普通标
        private int category;
        //用户是否能投此标1可投（新手可投）0（非新手）不可投
        private int can_tender;
        //
        private long fixed_time;
        //是否天标
        private int is_day;
        //发布日期
        private long openTime;
        //倒计时时间
        private long countDown;
        //项目规模
        private double amountBorrow;
        //还款方式
        private int repayWay;
        private boolean investAble;
        //剩余可投
        private double amountInvestable;
        private int classify;
        private int isRegionConfirm;
        private double rateYearMin;
        private double amountInvested;
        private double surplusMoney;
        private long addtime;
        private int award;
        private String bid_no;
        private String content;
        private int funds;
        private double part_account;
        private String styleStr;
        private String uuid;
        private int warrant_id;
        private String warrant_name;
        private long put_start_time;

        public boolean isOwn() {
            return isOwn;
        }

        public void setOwn(boolean own) {
            isOwn = own;
        }

        public double getAmountBorrow() {
            return amountBorrow;
        }

        public void setAmountBorrow(double amountBorrow) {
            this.amountBorrow = amountBorrow;
        }

        public double getAmountInvested() {
            return amountInvested;
        }

        public void setAmountInvested(double amountInvested) {
            this.amountInvested = amountInvested;
        }

        public double getSurplusMoney() {
            return surplusMoney;
        }

        public void setSurplusMoney(double surplusMoney) {
            this.surplusMoney = surplusMoney;
        }

        public long getAddtime() {
            return addtime;
        }

        public void setAddtime(long addtime) {
            this.addtime = addtime;
        }

        public String getRateYear() {
            return rateYear;
        }

        public void setRateYear(String rateYear) {
            this.rateYear = rateYear;
        }

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public String getBid_no() {
            return bid_no;
        }

        public void setBid_no(String bid_no) {
            this.bid_no = bid_no;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFunds() {
            return funds;
        }

        public void setFunds(int funds) {
            this.funds = funds;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIs_mb() {
            return is_mb;
        }

        public void setIs_mb(String is_mb) {
            this.is_mb = is_mb;
        }

        public String getInvestMin() {
            return investMin;
        }

        public void setInvestMin(String investMin) {
            this.investMin = investMin;
        }

        public double getInvestMax() {
            return investMax;
        }

        public void setInvestMax(double investMax) {
            this.investMax = investMax;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPart_account() {
            return part_account;
        }

        public void setPart_account(double part_account) {
            this.part_account = part_account;
        }

        public String getProgressPercentage() {
            return progressPercentage;
        }

        public void setProgressPercentage(String progressPercentage) {
            this.progressPercentage = progressPercentage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getRepayWay() {
            return repayWay;
        }

        public void setRepayWay(int repayWay) {
            this.repayWay = repayWay;
        }

        public String getStyleStr() {
            return styleStr;
        }

        public void setStyleStr(String styleStr) {
            this.styleStr = styleStr;
        }

        public int getInvestedCount() {
            return investedCount;
        }

        public void setInvestedCount(int investedCount) {
            this.investedCount = investedCount;
        }

        public String getTimeLimit() {
            return timeLimit;
        }

        public void setTimeLimit(String timeLimit) {
            this.timeLimit = timeLimit;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getWarrant_id() {
            return warrant_id;
        }

        public void setWarrant_id(int warrant_id) {
            this.warrant_id = warrant_id;
        }

        public String getWarrant_name() {
            return warrant_name;
        }

        public void setWarrant_name(String warrant_name) {
            this.warrant_name = warrant_name;
        }

        public int getType() {
            return type;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public String getBorrow_type() {
            return borrow_type;
        }

        public void setBorrow_type(String borrow_type) {
            this.borrow_type = borrow_type;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getCan_tender() {
            return can_tender;
        }

        public void setCan_tender(int can_tender) {
            this.can_tender = can_tender;
        }

        public long getFixed_time() {
            return fixed_time;
        }

        public void setFixed_time(long fixed_time) {
            this.fixed_time = fixed_time;
        }

        public long getPut_start_time() {
            return put_start_time;
        }

        public void setPut_start_time(long put_start_time) {
            this.put_start_time = put_start_time;
        }

        public int getIs_day() {
            return is_day;
        }

        public void setIs_day(int is_day) {
            this.is_day = is_day;
        }

        public long getOpenTime() {
            return openTime;
        }

        public void setOpenTime(long openTime) {
            this.openTime = openTime;
        }

        public long getCountDown() {
            return countDown;
        }

        public void setCountDown(long countDown) {
            this.countDown = countDown;
        }

        public boolean isInvestAble() {
            return investAble;
        }

        public void setInvestAble(boolean investAble) {
            this.investAble = investAble;
        }

        public double getPlatformRateYear() {
            return platformRateYear;
        }

        public void setPlatformRateYear(double platformRateYear) {
            this.platformRateYear = platformRateYear;
        }

        public double getAmountInvestable() {
            return amountInvestable;
        }

        public void setAmountInvestable(double amountInvestable) {
            this.amountInvestable = amountInvestable;
        }

        public int getClassify() {
            return classify;
        }

        public void setClassify(int classify) {
            this.classify = classify;
        }

        public int getIsRegionConfirm() {
            return isRegionConfirm;
        }

        public void setIsRegionConfirm(int isRegionConfirm) {
            this.isRegionConfirm = isRegionConfirm;
        }

        public double getRateYearMin() {
            return rateYearMin;
        }

        public void setRateYearMin(double rateYearMin) {
            this.rateYearMin = rateYearMin;
        }

        public boolean isDay(){
            if (timeType.equals("0"))
                return false;
            return true;
        }
        public String getTimetype(){
            return isDay()?"项目期限(天)":"项目期限(月)";
        }
        public String getLimit(){
            return DisplayFormat.doubleFormat(getInvestMin()) + "元 ~" +
                    (getInvestMax() != 0.0 ? DisplayFormat.doubleFormat(getInvestMax()) + "元" : "无限制");
        }

        public String getShowTime() {
            return isDay() ? timeLimit + "天" : timeLimit + "个月";
        }

        public String getBorrowType() {
            switch (type) {
                case 101:
                    return "担保标";
                case 102:
                    return "抵押标";
                case 103:
                    return "信用标";
                case 104:
                    return "秒还标";
                case 105:
                    return "质押标";
                default:
                    return "普通标";
            }
        }

        public String showRateYear(){
            String showRateYear;
            if(classify == 1){
                showRateYear =  DisplayFormat.doubleFormat(rateYearMin) + "%~" + DisplayFormat.doubleFormat(rateYear) + "%";
            } else {
                showRateYear =  DisplayFormat.doubleFormat(rateYear)+"%";
            }
            if(platformRateYear != 0){
                showRateYear = showRateYear + "+" + DisplayFormat.doubleFormat(platformRateYear) + "%";
            }
            return showRateYear;
        }
    }

}
