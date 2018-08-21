package com.rd.zhongqipiaoetong.payment;

import android.support.v4.app.Fragment;

import com.rd.zhongqipiaoetong.module.account.activity.BankCardListAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/12 下午8:30
 * <p/>
 * Description:
 */
public class ToPaymentCheck implements ToPaymentHandler {
    public static final int REALNAME   = 0x001;
    public static final int AUTO       = 0x002;
    public static final int BANKCARD   = 0x003;
    public static final int SETPAYPWD  = 0x004;
    public static final int TOBANKLIST = 0x005;
    private Fragment fragment;

    public ToPaymentCheck(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void check(int code, String value) {

        switch (code) {
            case REALNAME://提示实名
                UserLogic.accountToOpen(fragment);
                break;
            case AUTO://提示授权
                UserLogic.accountToAuthor(fragment, value);
                break;
            case BANKCARD://提示绑卡
                UserLogic.bindCard(fragment);
                break;
            case SETPAYPWD://提示设置支付密码
                UserLogic.setPayPwd(fragment);
                break;

            case TOBANKLIST://  TODO:  BankCardListAct
                ActivityUtils.push(BankCardListAct.class);
                break;
        }
    }
}
