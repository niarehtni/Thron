package com.rd.zhongqipiaoetong.payment.moneymoremore;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.money.more.activity.ControllerActivity;
import com.money.more.bean.AuthData;
import com.money.more.bean.RechargeData;
import com.money.more.bean.RegisterData;
import com.money.more.bean.TransferData;
import com.money.more.bean.WithdrawData;
import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.ref.RefException;

import java.lang.reflect.InvocationTargetException;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/26 下午3:10
 * <p/>
 * Description:
 */
public class MoneyMoreMoreTrigger {
    public static void controller(int type, AppPaymentMo mo) throws IllegalAccessException, InvocationTargetException,
            InstantiationException, RefException {
        controller(null, type, mo);
    }

    /**
     * 执行双乾方法
     *
     * @param fragment
     * @param type
     * @param mo
     *
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws RefException
     */
    public static void controller(Fragment fragment, int type, AppPaymentMo mo) throws IllegalAccessException, InvocationTargetException,
            InstantiationException, RefException {
        MoneyMoreMoreData.setMoneyConfig(mo);
        Intent intent = new Intent();
        intent.putExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_TYPE, type);
        int requestCode = 0;
        switch (type) {
            case MoneyMoreMoreWrapper.REGISTER:
                RegisterData registerData = MoneyMoreMoreData.setPaymentData(RegisterData.class, mo.getPaymentData(), type);
                registerData.setSignInfo(registerData.signDate());
                intent.putExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_DATA, registerData);
                requestCode = MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_REGISTER;
                break;
            case MoneyMoreMoreWrapper.AUTH:
                AuthData authData = MoneyMoreMoreData.setPaymentData(AuthData.class, mo.getPaymentData(), type);
                authData.setSignData(authData.signData());
                intent.putExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_DATA, authData);
                requestCode = MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_AUTH;
                break;
            case MoneyMoreMoreWrapper.RECHARGE:
                RechargeData rechargeData = MoneyMoreMoreData.setPaymentData(RechargeData.class, mo.getPaymentData(), type);
                rechargeData.setSignDate(rechargeData.signDate());
                intent.putExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_DATA, rechargeData);
                requestCode = MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_RECHARGE;
                break;
            case MoneyMoreMoreWrapper.WITHDRAW:
                WithdrawData withdrawData = MoneyMoreMoreData.setPaymentData(WithdrawData.class, mo.getPaymentData(), type);
                withdrawData.setSignDate(withdrawData.signData());
                intent.putExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_DATA, withdrawData);
                requestCode = MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_WITHDRAW;
                break;
            case MoneyMoreMoreWrapper.TRANSFER:
                TransferData transferData = MoneyMoreMoreData.setPaymentData(TransferData.class, mo.getPaymentData(), type);
                String sign = transferData.signData();
                transferData.setSignData(sign);
                intent.putExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_DATA, transferData);
                requestCode = MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_TRANSFER;
                break;
        }
        if (fragment == null) {
            ActivityUtils.push(ControllerActivity.class, intent, requestCode);
        } else {
            intent.setClass(fragment.getActivity(), ControllerActivity.class);
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 检查回调是否满足双乾Code
     *
     * @param type
     * @param requestCode
     * @param resultCode
     *
     * @return
     */
    public static boolean checkMoremoreCode(int type, int requestCode, int resultCode) {
        switch (type) {
            case MoneyMoreMoreWrapper.REGISTER:
                if (requestCode == MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_REGISTER && resultCode == MoneyMoreMoreBundleKeys
                        .RESULT_CODE_MOREMORE_REGISTER)
                    return true;
            case MoneyMoreMoreWrapper.AUTH:
                if (requestCode == MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_AUTH && resultCode == MoneyMoreMoreBundleKeys
                        .RESULT_CODE_MOREMORE_AUTH)
                    return true;
            case MoneyMoreMoreWrapper.RECHARGE:
                if (requestCode == MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_RECHARGE && resultCode == MoneyMoreMoreBundleKeys
                        .RESULT_CODE_MOREMORE_RECHARGE)
                    return true;
            case MoneyMoreMoreWrapper.WITHDRAW:
                if (requestCode == MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_WITHDRAW && resultCode == MoneyMoreMoreBundleKeys
                        .RESULT_CODE_MOREMORE_WITHDRAW)
                    return true;
            case MoneyMoreMoreWrapper.TRANSFER:
                if (requestCode == MoneyMoreMoreBundleKeys.REQUEST_CODE_MOREMORE_TRANSFER && resultCode == MoneyMoreMoreBundleKeys
                        .RESULT_CODE_MOREMORE_TRANSFER)
                    return true;
        }
        return false;
    }

    /**
     * 检查双乾回调是否成功
     *
     * @param type
     * @param requestCode
     * @param resultCode
     * @param data
     *
     * @return
     */
    public static boolean isSuccess(int type, int requestCode, int resultCode, Intent data, PayCallBack payCallBack) {
        if (checkMoremoreCode(type, requestCode, resultCode)) {
            int    code    = data.getIntExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_CODE, -1);
            String message = data.getStringExtra(MoneyMoreMoreBundleKeys.MONEYMOREMORE_MESSAGE);
            if (type == MoneyMoreMoreWrapper.REGISTER && (code == 103 || code == 102)) {//双乾提示BUG  双乾下个版本改进，暂写死
                Utils.toast("用户中途停止开户");
            } else {
                Utils.toast(message);
            }
            if (code == MoneyMoreMoreBundleKeys.MONEYMOREMORE_CODE_SUCCESSS) {
                return true;
            } else {
                if (payCallBack != null) {
                    payCallBack.callBack(false);
                }
            }
        } else {
            if (requestCode == PayController.REQUEST_CODE_BINDCARD && resultCode == PayController.RESULT_CODE_BANKCARD) {//绑卡
                return true;
            }
            if (requestCode == PayController.REQUEST_CODE_AUTO_ON) {//绑卡
                return true;
            }
            if (requestCode == PayController.REQUEST_CODE_AUTO_OFF) {//绑卡
                return true;
            }
            if (requestCode == PayController.REQUEST_CODE_AUTO_MODIFY) {//绑卡
                return true;
            }
        }
        return false;
    }
}
