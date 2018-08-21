package com.rd.zhongqipiaoetong.module.product.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.module.account.model.CouponMo;
import com.rd.zhongqipiaoetong.module.account.model.ExperienceMo;
import com.rd.zhongqipiaoetong.module.account.model.UpRateMo;
import com.rd.zhongqipiaoetong.module.product.viewmodel.InvestmentVM;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/16 11:43
 * <p/>
 * Description: 投资信息({@link InvestmentVM})
 */
public class InvestmentMo extends BaseObservable implements Serializable {
    /**
     * 账户余额
     */
    private double  useableBalanceAvailable;
    /**
     * 起购金额
     */
    private double  investMin;
    /**
     * 最大投资金额
     */
    private double  investMax;
    /**
     * 此次投标会话ID
     */
    private String  sessionId;
    /**
     * 剩余可投金额
     */
    private double  amountInvestable;
    /**
     * 预期收益
     */
    private String  estimatedEarnings;
    /**
     * 是否是定向标
     */
    private int     isDirect;
    private boolean isPwd;
    /**
     * 双乾帐户是否授权
     */
    private boolean authorized;
    /**
     * 授权类型
     */
    private String  authorizeType;
    /**
     * 是否设置交易密码
     */
    private boolean hasSetPayPwd;
    /**
     * 可使用的红包比例
     */
    private double  redPacketInvestMaxRatioKey;
    /**
     * 可使用的最大体验券
     */
    private double  canUseMaxExperienceAmount;
    /**
     * 是否可以使用体验券
     */
    private boolean canUseExperience;

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    /**
     * 可用红包
     *
     * @return
     */
    private ArrayList<CouponMo>     redPacketResArrays;
    /**
     * 可用加息券
     *
     * @return
     */
    private ArrayList<UpRateMo>     ticketRateResArrays;
    /**
     * 可用体验券
     *
     * @return
     */
    private ArrayList<ExperienceMo> availableExperiences;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public double getUseableBalanceAvailable() {
        return useableBalanceAvailable;
    }

    public void setUseableBalanceAvailable(double useableBalanceAvailable) {
        this.useableBalanceAvailable = useableBalanceAvailable;
    }

    public double getInvestMin() {
        return investMin;
    }

    public void setInvestMin(double investMin) {
        this.investMin = investMin;
    }

    public double getInvestMax() {
        return investMax;
    }

    public void setInvestMax(double investMax) {
        this.investMax = investMax;
    }

    public double getAmountInvestable() {
        return amountInvestable;
    }

    public void setAmountInvestable(double amountInvestable) {
        this.amountInvestable = amountInvestable;
    }

    public int getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(int isDirect) {
        this.isDirect = isDirect;
    }

    public boolean isPwd() {
        return isPwd;
    }

    public void setPwd(boolean pwd) {
        isPwd = pwd;
    }

    public ArrayList<CouponMo> getRedPacketResArrays() {
        return redPacketResArrays;
    }

    public void setRedPacketResArrays(ArrayList<CouponMo> redPacketResArrays) {
        this.redPacketResArrays = redPacketResArrays;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public boolean isHasSetPayPwd() {
        return hasSetPayPwd;
    }

    public void setHasSetPayPwd(boolean hasSetPayPwd) {
        this.hasSetPayPwd = hasSetPayPwd;
    }

    public ArrayList<UpRateMo> getTicketRateResArrays() {
        return ticketRateResArrays;
    }

    public void setTicketRateResArrays(ArrayList<UpRateMo> ticketRateResArrays) {
        this.ticketRateResArrays = ticketRateResArrays;
    }

    public ArrayList<ExperienceMo> getAvailableExperiences() {
        return availableExperiences;
    }

    public void setAvailableExperiences(ArrayList<ExperienceMo> availableExperiences) {
        this.availableExperiences = availableExperiences;
    }

    public double getRedPacketInvestMaxRatioKey() {
        return redPacketInvestMaxRatioKey;
    }

    public void setRedPacketInvestMaxRatioKey(double redPacketInvestMaxRatioKey) {
        this.redPacketInvestMaxRatioKey = redPacketInvestMaxRatioKey;
    }

    public double getCanUseMaxExperienceAmount() {
        return canUseMaxExperienceAmount;
    }

    public void setCanUseMaxExperienceAmount(double canUseMaxExperienceAmount) {
        this.canUseMaxExperienceAmount = canUseMaxExperienceAmount;
    }

    public boolean isCanUseExperience() {
        return canUseExperience;
    }

    public void setCanUseExperience(boolean canUseExperience) {
        this.canUseExperience = canUseExperience;
    }

    @Bindable
    public String getEstimatedEarnings() {
        return estimatedEarnings;
    }

    public void setEstimatedEarnings(String estimatedEarnings) {
        this.estimatedEarnings = estimatedEarnings;
        notifyPropertyChanged(BR.estimatedEarnings);
    }

    /**
     * 设置投资范围
     *
     * @return
     */
    public String getLimit() {
        return DisplayFormat.doubleFormat(getInvestMin()) + "元 - " + (getInvestMax() != 0.0 ? DisplayFormat.doubleFormat(getInvestMax()) + "元" : "无限制");
    }

    public boolean getCanUseRedCoupen() {
        return FeatureUtils.getEnableRedPackeModule() && redPacketResArrays != null && redPacketResArrays.size() != 0;
    }

    public boolean getCanUseUpCoupen() {
        return FeatureUtils.getEnableUpRateModule() && ticketRateResArrays != null && ticketRateResArrays.size() != 0;
    }

    public boolean getCanUseExpCoupen() {
        return FeatureUtils.getEnableExperienceModule() && availableExperiences != null && !(availableExperiences.size() == 0 || !isCanUseExperience());
    }
}