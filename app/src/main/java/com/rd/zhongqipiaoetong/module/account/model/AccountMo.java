package com.rd.zhongqipiaoetong.module.account.model;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AccountVM;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/2/26 下午2:08
 * <p/>
 * Description: 账户资金({@link AccountVM})信息
 */
@SuppressLint("ParcelCreator")
public class AccountMo extends BaseObservable implements Parcelable {
    /**
     * 真实姓名
     */
    private String  realName;
    /**
     * 马赛克后的手机号码
     */
    private String  hideMobilePhone;
    /**
     * 可用金额
     */
    private String  balanceAvailable;
    private String  showBalanceAvailable;
    /**
     * 账户金额
     */
    private String  accountAmountTotal;
    private String  showAccountAmountTotal;
    /**
     * 冻结金额
     */
    private String  amountFrozen;
    /**
     * 银行卡总数
     */
    private int     bankNum;
    /**
     * 今日收益
     */
    private String  today_earn_amount;
    /**
     * 已收收益
     */
    private String  incomeCollected;
    private String  showIncomeCollected;
    /**
     * 累计收益
     */
    private String  incomeCollecting;
    private String  showIncomeCollecting;
    /**
     * 待收金额
     */
    private String  incomeCapital;
    /**
     * 在投金额
     */
    private String  amountInvesting;
    /**
     * 累计投资
     */
    private String  invest_amount;
    /**
     * 实名状态 1已实名，0未实名
     */
    private int     realNameStatus;
    /**
     * 是否设置支付密码
     */
    private boolean hasSetPayPwd;
    /**
     * 支付密码是否被锁定
     */
    private boolean payPwdLocked;
    /**
     * 头像前缀
     */
    private String  qiniuDomain;
    /**
     * 头像的相对路径
     */
    private String  headPortraitUrl;
    /**
     * 未读消息总数
     */
    private int     unreadMsgCount;

    //---阿梁：新增-----
    /**
     * 待收收益
     */
    private String  toCollectEarnMoney;
    /**
     * 债权待收金额
     */
    private String  toBondCollectMoney;
    /**
     * 债权投资待收收益
     */
    private String  toBondToCollectEarnMoney;
    /**
     * 债权投资金额
     */
    private String  bondInvestingAmount;
    /**
     * 债权累计收益
     */
    private String  bondEarnAmount;
    /**
     * 债权累计投资金额
     */
    private String  bondInvestedAmount;
    //---阿梁：新增-结束----
    /**
     * 用户投资记录总数
     */
    private double  tenderTotals;
    /**
     * 是否授权
     */
    private boolean isAuthorize;
    /**
     * 用户认证类型
     */
    private String  authorizeType;
    /**
     * 用户名  ＊＊＊
     */
    private String  username;
    /**
     * VIP等级
     */
    private int     vip;
    private boolean isShow = true;

    private boolean canBorrow = false;

    //--------------------------华丽分割线----------------------------------

    public boolean isCanBorrow() {
        return canBorrow;
    }

    public void setCanBorrow(boolean canBorrow) {
        this.canBorrow = canBorrow;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHideMobilePhone() {
        return hideMobilePhone;
    }

    public void setHideMobilePhone(String hideMobilePhone) {
        this.hideMobilePhone = hideMobilePhone;
    }

    public String getIncomeCollected() {
        return incomeCollected;
    }

    @Bindable
    public String getShowIncomeCollected() {
        if (isShow) {
            return incomeCollected;
        } else {
            return "****";
        }
    }

    public void setIncomeCollected(String incomeCollected) {
        this.incomeCollected = incomeCollected;
    }

    public int getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(int realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    public boolean isHasSetPayPwd() {
        return hasSetPayPwd;
    }

    public void setHasSetPayPwd(boolean hasSetPayPwd) {
        this.hasSetPayPwd = hasSetPayPwd;
    }

    public boolean isPayPwdLocked() {
        return payPwdLocked;
    }

    public void setPayPwdLocked(boolean payPwdLocked) {
        this.payPwdLocked = payPwdLocked;
    }

    public String getBalanceAvailable() {
        return balanceAvailable;
    }

    @Bindable
    public String getShowBalanceAvailable() {
        if (isShow()) {
            return balanceAvailable;
        } else {
            return "****";
        }
    }

    public void setBalanceAvailable(String balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public String getAccountAmountTotal() {
        return accountAmountTotal;
    }

    @Bindable
    public String getShowAccountAmountTotal() {
        if (isShow) {
            return accountAmountTotal;
        } else {
            return "****";
        }
    }

    public void setAccountAmountTotal(String accountAmountTotal) {
        this.accountAmountTotal = accountAmountTotal;
    }

    public String getAmountFrozen() {
        return amountFrozen;
    }

    public void setAmountFrozen(String amountFrozen) {
        this.amountFrozen = amountFrozen;
    }

    public int getBankNum() {
        return bankNum;
    }

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public String getToday_earn_amount() {
        return today_earn_amount;
    }

    public void setToday_earn_amount(String today_earn_amount) {
        this.today_earn_amount = today_earn_amount;
    }

    public String getIncomeCollecting() {
        return incomeCollecting;
    }

    @Bindable
    public String getShowIncomeCollecting() {
        if (isShow) {
            return incomeCollecting;
        } else {
            return "****";
        }
    }

    public void setIncomeCollecting(String incomeCollecting) {
        this.incomeCollecting = incomeCollecting;
    }

    public String getIncomeCapital() {
        return incomeCapital;
    }

    public void setIncomeCapital(String incomeCapital) {
        this.incomeCapital = incomeCapital;
    }

    public String getAmountInvesting() {
        return amountInvesting;
    }

    public void setAmountInvesting(String amountInvesting) {
        this.amountInvesting = amountInvesting;
    }

    public String getInvest_amount() {
        return invest_amount;
    }

    public void setInvest_amount(String invest_amount) {
        this.invest_amount = invest_amount;
    }

    public String getQiniuDomain() {
        return qiniuDomain;
    }

    public void setQiniuDomain(String qiniuDomain) {
        this.qiniuDomain = qiniuDomain;
    }

    public String getHeadPortraitUrl() {
        return qiniuDomain + headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public int getUnreadMsgCount() {
        return unreadMsgCount;
    }

    public void setUnreadMsgCount(int unreadMsgCount) {
        this.unreadMsgCount = unreadMsgCount;
    }

    public String getToBondCollectMoney() {
        return toBondCollectMoney;
    }

    public void setToBondCollectMoney(String toBondCollectMoney) {
        this.toBondCollectMoney = toBondCollectMoney;
    }

    public String getToCollectEarnMoney() {
        return toCollectEarnMoney;
    }

    public void setToCollectEarnMoney(String toCollectEarnMoney) {
        this.toCollectEarnMoney = toCollectEarnMoney;
    }

    public String getToBondToCollectEarnMoney() {
        return toBondToCollectEarnMoney;
    }

    public void setToBondToCollectEarnMoney(String toBondToCollectEarnMoney) {
        this.toBondToCollectEarnMoney = toBondToCollectEarnMoney;
    }

    public String getBondInvestingAmount() {
        return bondInvestingAmount;
    }

    public void setBondInvestingAmount(String bondInvestingAmount) {
        this.bondInvestingAmount = bondInvestingAmount;
    }

    public String getBondEarnAmount() {
        return bondEarnAmount;
    }

    public void setBondEarnAmount(String bondEarnAmount) {
        this.bondEarnAmount = bondEarnAmount;
    }

    public String getBondInvestedAmount() {
        return bondInvestedAmount;
    }

    public void setBondInvestedAmount(String bondInvestedAmount) {
        this.bondInvestedAmount = bondInvestedAmount;
    }

    public double getTenderTotals() {
        return tenderTotals;
    }

    public void setTenderTotals(double tenderTotals) {
        this.tenderTotals = tenderTotals;
    }

    public boolean isAuthorize() {
        return isAuthorize;
    }

    public void setAuthorize(boolean authorize) {
        isAuthorize = authorize;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
        notifyPropertyChanged(BR.showBalanceAvailable);
        notifyPropertyChanged(BR.showAccountAmountTotal);
        notifyPropertyChanged(BR.showIncomeCollected);
        notifyPropertyChanged(BR.showIncomeCollecting);
    }

    public void setShowBalanceAvailable(String showBalanceAvailable) {
        this.showBalanceAvailable = showBalanceAvailable;
    }

    public void setShowAccountAmountTotal(String showAccountAmountTotal) {
        this.showAccountAmountTotal = showAccountAmountTotal;
    }

    public void setShowIncomeCollected(String showIncomeCollected) {
        this.showIncomeCollected = showIncomeCollected;
    }

    public void setShowIncomeCollecting(String showIncomeCollecting) {
        this.showIncomeCollecting = showIncomeCollecting;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.realName);
        dest.writeString(this.hideMobilePhone);
        dest.writeString(this.balanceAvailable);
        dest.writeString(this.accountAmountTotal);
        dest.writeString(this.amountFrozen);
        dest.writeInt(this.bankNum);
        dest.writeString(this.today_earn_amount);
        dest.writeString(this.invest_amount);
        dest.writeString(this.incomeCapital);
        dest.writeString(this.amountInvesting);
        dest.writeString(this.incomeCollecting);
        dest.writeInt(this.realNameStatus);
        dest.writeByte(this.hasSetPayPwd ? (byte) 1 : (byte) 0);
        dest.writeByte(this.payPwdLocked ? (byte) 1 : (byte) 0);
        dest.writeString(this.qiniuDomain);
        dest.writeString(this.headPortraitUrl);
        dest.writeInt(this.unreadMsgCount);
        dest.writeString(this.toCollectEarnMoney);
        dest.writeString(this.toBondCollectMoney);
        dest.writeString(this.toBondToCollectEarnMoney);
        dest.writeString(this.bondInvestingAmount);
        dest.writeString(this.bondEarnAmount);
        dest.writeString(this.bondInvestedAmount);
        dest.writeDouble(this.tenderTotals);
        dest.writeByte(this.isAuthorize ? (byte) 1 : (byte) 0);
        dest.writeString(this.authorizeType);
        dest.writeString(this.username);
        dest.writeByte(this.isShow ? (byte) 1 : (byte) 0);
    }

    protected AccountMo(Parcel in) {
        this.realName = in.readString();
        this.hideMobilePhone = in.readString();
        this.balanceAvailable = in.readString();
        this.accountAmountTotal = in.readString();
        this.amountFrozen = in.readString();
        this.bankNum = in.readInt();
        this.today_earn_amount = in.readString();
        this.invest_amount = in.readString();
        this.incomeCapital = in.readString();
        this.amountInvesting = in.readString();
        this.incomeCollecting = in.readString();
        this.realNameStatus = in.readInt();
        this.hasSetPayPwd = in.readByte() != 0;
        this.payPwdLocked = in.readByte() != 0;
        this.qiniuDomain = in.readString();
        this.headPortraitUrl = in.readString();
        this.unreadMsgCount = in.readInt();
        this.toCollectEarnMoney = in.readString();
        this.toBondCollectMoney = in.readString();
        this.toBondToCollectEarnMoney = in.readString();
        this.bondInvestingAmount = in.readString();
        this.bondEarnAmount = in.readString();
        this.bondInvestedAmount = in.readString();
        this.tenderTotals = in.readDouble();
        this.isAuthorize = in.readByte() != 0;
        this.authorizeType = in.readString();
        this.username = in.readString();
        this.isShow = in.readByte() != 0;
    }

    public static final Creator<AccountMo> CREATOR = new Creator<AccountMo>() {
        @Override
        public AccountMo createFromParcel(Parcel source) {
            return new AccountMo(source);
        }

        @Override
        public AccountMo[] newArray(int size) {
            return new AccountMo[size];
        }
    };

    /**
     * 是否显示VIP
     */
    public boolean isShowVip() {
        return FeatureUtils.getEnableShowVipFeature() && vip >= 0;
    }
}