package com.rd.zhongqipiaoetong.module.product.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductInvestmentActBinding;
import com.rd.zhongqipiaoetong.module.product.model.FinancingDetailMo;
import com.rd.zhongqipiaoetong.module.product.viewmodel.InvestmentVM;
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
public class InvestmentAct extends BaseActivity {
    private ProductInvestmentActBinding binding;
    private InvestmentVM                vm;
    private String                      uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.product_investment_act);
        uuid = getIntent().getStringExtra(BundleKeys.ID);
        FinancingDetailMo financingDetailMo = (FinancingDetailMo) getIntent().getSerializableExtra(BundleKeys.PRODUCTDETAIL);
        vm = new InvestmentVM(uuid, financingDetailMo, binding);
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
        vm.req_data(uuid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (vm.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        PayCallBack payCallBack = new PayCallBack() {
            @Override
            public void callBack(boolean status) {
                if (!status)
                    vm.req_data(uuid);
            }
        };
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTH, requestCode, resultCode, data, null)) {//授权回调
            vm.model.get().setAuthorized(true);
        }
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_INVEST, requestCode, resultCode, data, payCallBack)) { //购买回调
            ActivityUtils.pop(this);
        }
    }
}