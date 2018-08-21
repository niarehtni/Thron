package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.module.account.activity.EmailBindAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountOCRAct;
import com.rd.zhongqipiaoetong.module.account.activity.PersonInfoAct;
import com.rd.zhongqipiaoetong.module.account.activity.PwdManageAct;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.view.PickPopupWindow;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/24 11:16
 * <p/>
 * Description: 账户与安全VM({@link PersonInfoAct})
 */
public class PersonInfoVM {
    public final ObservableField<PersonInfoMo> info = new ObservableField<>();
    public  PickPopupWindow popupWindow;
    private OnClickListener listener;
    public ObservableField<Boolean> isShowEmail=new ObservableField<>();


    public PersonInfoVM(OnClickListener listener) {
        this.listener = listener;
        isShowEmail.set(FeatureConfig.enableEmailFeature==1);
    }

    /**
     * 头像click事件
     */
    public void onAvatarClick(View view) {
        final Context context = view.getContext();
        popupWindow = new PickPopupWindow(context, listener);
        popupWindow.showAtLocation(view.getRootView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 邮箱click事件
     */
    public void onEmailClick(View view) {
        ActivityUtils.push(EmailBindAct.class);
    }

    /**
     * 密码管理click事件
     */
    public void onPwdManageClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.HASSETPAYPWD, info.get().getHas_set_paypwd());
        ActivityUtils.push(PwdManageAct.class, intent);
        //        view.getContext().startActivity(new Intent(view.getContext(), PwdManageAct.class));
    }

    /**
     * 支付账户click事件
     */
    public void onPaymentClick(View view) {
        if(info.get().getRealNameStatus() == 6){
//            ActivityUtils.push(BankCardListAct.class);
            return;
        }
        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(info.get().getRealNameStatus());
        if (RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_REGISTER, toPaymentMo, new ToPaymentCheck(null), false)) {
            return;
        }
        if(FeatureConfig.enableCertOCRFeature == 1){
            ActivityUtils.push(PaymentAccountOCRAct.class);
        }else{
            ActivityUtils.push(PaymentAccountAct.class);
        }
    }


    /**
     * 银行卡click事件
     */
    public void onBankCardClick(View view) {
        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(info.get().getRealNameStatus());
        toPaymentMo.setBankNum(info.get().getBankNum());
        if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_BINDCARD, toPaymentMo, new ToPaymentCheck(null), true)) {
            return;
        }
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_BINDCARD, null, null);
    }

    /**
     * 登出click事件
     */
    public void onSignOutClick(View view) {
        UserLogic.signOutToMain();
    }
}