package com.rd.zhongqipiaoetong.payment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/8/3 下午11:46
 * <p/>
 * Description:
 */
public abstract class PayController {
    /**
     * 开户
     */
    public static final int    TPYE_REGISTER             = 0x001;
    public static final String REGISTER                  = "account/openAccount";
    public static final int    REQUEST_CODE_REGISTER     = 0x101;
    /**
     * 授权
     */
    public static final int    TPYE_AUTH                 = 0x002;
    public static final String AUTH                      = "account/authorization.html";
    public static final int    REQUEST_CODE_AUTH         = 0x102;
    /**
     * 充值
     */
    public static final int    TPYE_RECHARGE             = 0x003;
    public static final String RECHARGE                  = "account/charge";
    public static final int    REQUEST_CODE_RECHARGE     = 0x103;
    /**
     * 提现
     */
    public static final int    TPYE_WITHDRAW             = 0x004;
    public static final String WITHDRAW                  = "account/withdraw";
    public static final int    REQUEST_CODE_WITHDRAW     = 0x104;
    /**
     * 普通投标
     */
    public static final int    TPYE_INVEST               = 0x005;
    public static final String INVEST                    = "tender/invest";
    public static final int    REQUEST_CODE_INVEST       = 0x105;
    /**
     * 债转投标
     */
    public static final int    TPYE_BOND                 = 0x006;
    public static final String BOND                      = "bond/bondInvestForm";
    public static final int    REQUEST_CODE_BOND         = 0x106;
    /**
     * 绑卡
     */
    public static final int    TPYE_BINDCARD             = 0x007;
    public static final String BINDCARD                  = "account/addBankCard";
    public static final int    REQUEST_CODE_BINDCARD     = 0x107;
    public static final int    RESULT_CODE_BANKCARD      = 0x0017;
    /**
     * 自动投标打开
     */
    public static final int    TPYE_AUTO_ON              = 0x008;
    public static final String AUTO_ON                   = "invest/autoTender.html";
    public static final int    REQUEST_CODE_AUTO_ON      = 0x108;
    /**
     * 自动投标关闭
     */
    public static final int    TPYE_AUTO_OFF             = 0x009;
    public static final String AUTO_OFF                  = "invest/autoStatus.html";
    public static final int    REQUEST_CODE_AUTO_OFF     = 0x109;
    /**
     * 自动投标修改
     */
    public static final int    TPYE_AUTO_MODIFY          = 0x010;
    public static final String AUTO_MODIFY               = "invest/autoTender.html";
    public static final int    REQUEST_CODE_AUTO_MODIFY  = 0x110;
    /**
     * 页面绑卡
     */
    public static final int    TPYE_PAGEBINDCARD         = 0x011;
    public static final int    REQUEST_CODE_PAGEBINDCARD = 0x111;
    /**
     * 计息理财
     */
    public static final int    TPYE_FLOW                 = 0x012;
    public static final String FLOW                      = "flow/doFlowInvset.html";
    public static final int    REQUEST_CODE_FLOW         = 0x112;

    public void doPayment(Fragment fragment, int paymentType, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        switch (paymentType) {
            case PayController.TPYE_REGISTER:
                doRegister(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_AUTH:
                doAuth(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_RECHARGE:
                doRechage(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_WITHDRAW:
                doWithdraw(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_INVEST:
                doInvest(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_FLOW:
                doFlow(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_BOND:
                doBond(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_BINDCARD:
                doBindCard(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_AUTO_ON:
                doAutoOn(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_AUTO_OFF:
                doAutoOff(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_AUTO_MODIFY:
                doAutoModify(fragment, paramMap, payCallBack);
                break;
            case PayController.TPYE_PAGEBINDCARD:
                doPageBindCard(fragment, paramMap, payCallBack);
                break;
        }
    }

    public boolean toPayment(int paymentType, ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {

        switch (paymentType) {
            case PayController.TPYE_REGISTER:
                return toRegister(mo, toPaymentCheck, isShow);
            case PayController.TPYE_AUTH:
                return toAuth(mo, toPaymentCheck, isShow);
            case PayController.TPYE_RECHARGE:
                return toRechage(mo, toPaymentCheck, isShow);
            case PayController.TPYE_WITHDRAW:
                return toWithdraw(mo, toPaymentCheck, isShow);
            case PayController.TPYE_INVEST:
                return toInvest(mo, toPaymentCheck, isShow);
            case PayController.TPYE_FLOW:
                return toFlow(mo, toPaymentCheck, isShow);
            case PayController.TPYE_BOND:
                return toBond(mo, toPaymentCheck, isShow);
            case PayController.TPYE_BINDCARD:
                return toBindCard(mo, toPaymentCheck, isShow);
            case PayController.TPYE_AUTO_ON:
                return toAutoOn(mo, toPaymentCheck, isShow);
            case PayController.TPYE_AUTO_OFF:
                return toAutoOff(mo, toPaymentCheck, isShow);
            case PayController.TPYE_AUTO_MODIFY:
                return toAutoModify(mo, toPaymentCheck, isShow);
        }
        return true;
    }

    public abstract void doRegister(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toRegister(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doAuth(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toAuth(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doRechage(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toRechage(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doWithdraw(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toWithdraw(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doInvest(final Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack);

    public abstract boolean toInvest(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doFlow(final Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack);

    public abstract boolean toFlow(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doBond(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toBond(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doBindCard(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract void doPageBindCard(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toBindCard(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract boolean toAuto(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doAutoOn(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toAutoOn(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doAutoOff(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toAutoOff(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract void doAutoModify(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack);

    public abstract boolean toAutoModify(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow);

    public abstract boolean resultCheck(int type, int requestCode, int resultCode, Intent data, PayCallBack payCallBack);
}
