package com.rd.zhongqipiaoetong.module.product.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductBondtenderActBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.BondTenderVM;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/15 17:24
 * <p/>
 * Description: 投资界面(非债权投资)
 */
public class BondTenderAct extends BaseActivity {
    private ProductBondtenderActBinding binding;
    private BondTenderVM                vm;
    private String                      id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.product_bondtender_act);
        id = getIntent().getStringExtra(BundleKeys.ID);
        vm = new BondTenderVM(id, binding);
        binding.setVm(vm);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.investment_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.req_data(id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        PayCallBack payCallBack = new PayCallBack() {
            @Override
            public void callBack(boolean status) {
                if (!status) {
                    vm.req_data(id);
                }
            }
        };
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.REQUEST_CODE_BOND, requestCode, resultCode, data, payCallBack)) {//购买债转
            ActivityUtils.pop(this);
        }
    }
}