package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.model.TppConfineMo;
import com.rd.zhongqipiaoetong.module.account.viewmodel.BankCardListVM;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 16:52
 * <p/>
 * Description: 银行卡列表
 */
public class BankCardListAct extends BaseActivity {
    private BankCardListVM listVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonRecyclerViewBinding binding = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        listVM = new BankCardListVM(this, getIntent().getBooleanExtra(BundleKeys.IS_SELECTED, false), new UpDataListener<TppConfineMo>() {
            @Override
            public void updata(int type, TppConfineMo mo) {
                FeatureUtils.AddBankCardButtonLogic(type, mo, BankCardListAct.this);
            }
        });
        binding.setViewModel(listVM);
        binding.executePendingBindings();
        binding.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                listVM.emptyState.setLoading(false);
            }
        }, 10000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.bc_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listVM.req_data();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PayCallBack payCallBack = new PayCallBack() {
            @Override
            public void callBack(boolean status) {
                listVM.req_data();
            }
        };
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTH, requestCode, resultCode, data, payCallBack)) {
        }
    }
}