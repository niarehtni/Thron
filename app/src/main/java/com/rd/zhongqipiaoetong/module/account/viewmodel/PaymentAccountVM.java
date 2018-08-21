package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.EditText;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.AccountPaymentAccountActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountAct;
import com.rd.zhongqipiaoetong.module.account.model.LoginingMo;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.AllCapTransformationMethod;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.SPUtil;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/25 18:09
 * <p/>
 * Description: 支付账户VM({@link PaymentAccountAct})
 */
public class PaymentAccountVM {
    private AccountPaymentAccountActBinding binding;
    private LoginingMo                      loginingmo;
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.edListCheck(edList));
            enable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public LinkedList<EditText>                 edList  = new LinkedList<>();
    public ObservableField<Boolean>             enable  = new ObservableField<>(false);

    public PaymentAccountVM(AccountPaymentAccountActBinding binding) {
        binding.paymentEtCard.setTransformationMethod(new AllCapTransformationMethod());
        this.binding = binding;
    }

    /**
     * 开通支付账户
     */
    public void onRegisterClick(View view) {
        String realname = binding.paymentEtRealname.getText().toString().trim();
        if (realname.isEmpty()) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.payment_account_input_realname));
            return;
        }

        if (!InputCheck.checkChinese(realname)) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.payment_account_input_realname_err));
            return;
        }

        String cardId = binding.paymentEtCard.getText().toString().trim();
        if (cardId.isEmpty()) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.payment_account_input_id_card));
            return;
        }

        if (!InputCheck.checkCard(cardId)) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.payment_account_input_id_card_err));
            return;
        }
        String imei = SPUtil.imeiSave(view.getContext());
        res_data(realname, cardId, imei);
    }

    /**
     * 开户（实名认证）
     *
     * @param realname
     * @param cardId
     */
    private void res_data(String realname, String cardId, String imei) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.REALNAME, realname);
        map.put(RequestParams.IDNO, cardId);
//        map.put(RequestParams.ACKTOKEN, imei);
//        map.put(RequestParams.ACKAPPKEY, RequestParams.ACKAPPKEY_NUM);
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_REGISTER, map, null);
    }
}