package com.rd.zhongqipiaoetong.payment;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/12 下午8:49
 * <p/>
 * Description:
 */
public class ToPaymentMo {

    /**
     * 是否授权
     */
    private boolean isAuthorize;
    /**
     * 用户认证类型
     */
    private String  authorizeType;
    /**
     * 实名状态 1已实名，0未实名
     */
    private Integer realNameStatus;

    /**
     * 是否设置支付密码
     */
    private boolean has_set_paypwd;

    /**
     * 银行卡总数
     */
    private int     bankNum;
    /**
     * 是否开通快捷支付（联动优势独有）
     */
    private String  isFastPayment;

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

    public Integer getRealNameStatus() {
        return realNameStatus;
    }

    public void setRealNameStatus(Integer realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    public boolean isHas_set_paypwd() {
        return has_set_paypwd;
    }

    public void setHas_set_paypwd(boolean has_set_paypwd) {
        this.has_set_paypwd = has_set_paypwd;
    }

    public int getBankNum() {
        return bankNum;
    }

    public void setBankNum(int bankNum) {
        this.bankNum = bankNum;
    }

    public String getIsFastPayment() {
        return isFastPayment;
    }

    public void setIsFastPayment(String isFastPayment) {
        this.isFastPayment = isFastPayment;
    }
}
