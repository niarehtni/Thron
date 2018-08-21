package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountBankCardBindActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.BankCardBindVM;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 11:14
 * <p/>
 * Description: 银行卡绑定
 */
public class BankCardBindAct extends BaseActivity implements View.OnLayoutChangeListener {
    BankCardBindVM                bankCardBindVM;
    AccountBankCardBindActBinding mbinding;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight    = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.account_bank_card_bind_act);
        bankCardBindVM = new BankCardBindVM(mbinding);
        mbinding.setViewModel(bankCardBindVM);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.bc_add_card);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mbinding.getRoot().addOnLayoutChangeListener(this);
    }

    /**
     * dialog 未关闭先关闭
     */
    @Override
    public void onBackPressed() {
        if (dismissPickerView(bankCardBindVM.opendingPickerView) || dismissPickerView(bankCardBindVM.areaPickerView)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void finish() {
        setResult(PayController.RESULT_CODE_BANKCARD);
        super.finish();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            dismissPickerView(bankCardBindVM.opendingPickerView);
            dismissPickerView(bankCardBindVM.areaPickerView);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

        }
    }

    private boolean dismissPickerView(OptionsPickerView pickerView) {
        if (pickerView != null) {
            if (pickerView.isShowing()) {
                pickerView.dismiss();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RDPayment.getInstance().getPayController().resultCheck(requestCode, requestCode, resultCode, data, null)) {
            ActivityUtils.pop(this);
        }
    }
}