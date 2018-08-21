package com.rd.zhongqipiaoetong.payment.moneymoremore;

import java.util.HashMap;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/19/16.
 */
public class MoneyMoreMoreWrapper {
    /**
     * 注册（开户）
     */
    public final static  int                     REGISTER             = 1;
    /**
     * 充值
     */
    public final static  int                     RECHARGE             = 2;
    /**
     * 提现
     */
    public final static  int                     WITHDRAW             = 3;
    /**
     * 转账（投资）
     */
    public final static  int                     TRANSFER             = 4;

    /**
     * 授权
     */
    public final static  int                     AUTH                 = 5;
    /**
     * 开户map register
     */
    private final static HashMap<String, String> SET_REGISTER_WRAPPER = new HashMap<String, String>();
    /**
     * 充值相关 recharge
     */
    private final static HashMap<String, String> SET_RECHARGE_WRAPPER = new HashMap<String, String>();
    /**
     * 提现相关 withdraw
     */
    private final static HashMap<String, String> SET_WITHDRAW_WRAPPER = new HashMap<String, String>();
    /**
     * 投标相关(转账)
     */
    private final static HashMap<String, String> SET_TRANSFER_WRAPPER = new HashMap<String, String>();
    /**
     * 授权
     */
    private final static HashMap<String, String> SET_TAUTH_WRAPPER    = new HashMap<String, String>();

    static {
        //开户相关 register
        SET_REGISTER_WRAPPER.put("accountType", "setAccountType");
        SET_REGISTER_WRAPPER.put("Mobile", "setMobile");
        SET_REGISTER_WRAPPER.put("Email", "setEmail");
        SET_REGISTER_WRAPPER.put("RealName", "setRealName");
        SET_REGISTER_WRAPPER.put("IdentificationNo", "setIdentificationNo");
        SET_REGISTER_WRAPPER.put("LoanPlatformAccount", "setLoanPlatAccount");
        SET_REGISTER_WRAPPER.put("PlatformMoneymoremore", "setPlatAccount");
        SET_REGISTER_WRAPPER.put("RandomTimeStamp", "setRandomTimeStamp");
        SET_REGISTER_WRAPPER.put("Remark1", "setRemark1");
        SET_REGISTER_WRAPPER.put("Remark2", "setRemark2");
        SET_REGISTER_WRAPPER.put("Remark3", "setRemark3");
        SET_REGISTER_WRAPPER.put("NotifyURL", "setNotifyURL");

        //充值相关 recharge
        SET_RECHARGE_WRAPPER.put("RechargeMoneymoremore", "setRechargemdd");
        SET_RECHARGE_WRAPPER.put("PlatformMoneymoremore", "setPlatformdd");
        SET_RECHARGE_WRAPPER.put("OrderNo", "setOrderno");
        SET_RECHARGE_WRAPPER.put("Amount", "setAmount");
        SET_RECHARGE_WRAPPER.put("FeeType", "setFeeType");
        SET_RECHARGE_WRAPPER.put("RandomTimeStamp", "setRandomTime");
        SET_RECHARGE_WRAPPER.put("Remark1", "setRemark1");
        SET_RECHARGE_WRAPPER.put("Remark2", "setRemark2");
        SET_RECHARGE_WRAPPER.put("Remark3", "setRemark3");
        SET_RECHARGE_WRAPPER.put("NotifyURL", "setNotifyURL");

        //提现相关 withdraw
        SET_WITHDRAW_WRAPPER.put("WithdrawMoneymoremore", "setWithdrawnum");
        SET_WITHDRAW_WRAPPER.put("PlatformMoneymoremore", "setPlatformmdd");
        SET_WITHDRAW_WRAPPER.put("OrderNo", "setOrderno");
        SET_WITHDRAW_WRAPPER.put("Amount", "setAmount");
        SET_WITHDRAW_WRAPPER.put("FeeQuota", "setFeequota");
        SET_WITHDRAW_WRAPPER.put("CardNo", "setCardno");
        SET_WITHDRAW_WRAPPER.put("CardType", "setCardtype");
        SET_WITHDRAW_WRAPPER.put("BankCode", "setBankCode");
        SET_WITHDRAW_WRAPPER.put("BranchBankName", "setBranchBankName");
        SET_WITHDRAW_WRAPPER.put("Province", "setProvince");
        SET_WITHDRAW_WRAPPER.put("City", "setCity");
        SET_WITHDRAW_WRAPPER.put("RandomTimeStamp", "setRandomTime");
        SET_WITHDRAW_WRAPPER.put("Remark1", "setRemark1");
        SET_WITHDRAW_WRAPPER.put("Remark2", "setRemark2");
        SET_WITHDRAW_WRAPPER.put("Remark3", "setRemark3");
        SET_WITHDRAW_WRAPPER.put("NotifyURL", "setNotifyURL");

        // 投标相关(转账)
        SET_TRANSFER_WRAPPER.put("LoanJsonList", "setLoanJsonList");
        SET_TRANSFER_WRAPPER.put("PlatformMoneymoremore", "setPlatformdd");
        SET_TRANSFER_WRAPPER.put("TransferAction", "setTransferAction");
        SET_TRANSFER_WRAPPER.put("Action", "setAction");
        SET_TRANSFER_WRAPPER.put("TransferType", "setTransferType");
        SET_TRANSFER_WRAPPER.put("NeedAudit", "setNeedAudit");
        SET_TRANSFER_WRAPPER.put("Remark1", "setRemark1");
        SET_TRANSFER_WRAPPER.put("NotifyURL", "setNotifyurl");

        //授权
        SET_TAUTH_WRAPPER.put("AuthorizeTypeOpen", "setOpentype");
        SET_TAUTH_WRAPPER.put("MoneymoremoreId", "setMddid");
        SET_TAUTH_WRAPPER.put("NotifyURL", "setNotifyURL");
        SET_TAUTH_WRAPPER.put("PlatformMoneymoremore", "setPlatformdd");
        SET_TAUTH_WRAPPER.put("RandomTimeStamp", "setRandomTime");
    }

    /**
     * 是否包含 Method
     *
     * @param key
     *
     * @return
     */
    public static boolean isPrimitiveMethod(int type, String key) {
        return changeMap(type).containsKey(key);
    }

    public static String getValue(int type, String key) {
        return changeMap(type).get(key);
    }

    /**
     * map转换
     *
     * @param type
     *
     * @return
     */
    private static HashMap<String, String> changeMap(int type) {
        switch (type) {
            case REGISTER:
                return SET_REGISTER_WRAPPER;
            case RECHARGE:
                return SET_RECHARGE_WRAPPER;
            case WITHDRAW:
                return SET_WITHDRAW_WRAPPER;
            case TRANSFER:
                return SET_TRANSFER_WRAPPER;
            case AUTH:
                return SET_TAUTH_WRAPPER;
            default:
                return new HashMap<String, String>();
        }
    }
}
