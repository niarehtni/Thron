package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountMyAssetsActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.MyAssetsVM;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/9 16:40
 * <p/>
 * Description: 账户总览（我的资产）
 */
public class MyAssetsAct extends BaseActivity {
    private AccountMyAssetsActBinding binding;
    private MyAssetsVM                myAssetsVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.account_my_assets_act);
        myAssetsVM = new MyAssetsVM(binding);
        binding.setViewModel(myAssetsVM);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.assets_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAssetsVM.req_data();
    }

    @Override
    public void finish() {
        setResult(BundleKeys.RESULT_CODE_ASSESTS);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTH, requestCode, resultCode, data, null);

        if (requestCode == BundleKeys.REQUEST_CODE_OPEN && resultCode == BundleKeys.RESULT_CODE_OPEN) {//开户
        }
    }
}