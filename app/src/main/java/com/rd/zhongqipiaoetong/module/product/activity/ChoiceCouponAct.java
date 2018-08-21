package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductChoiceCouponBinding;
import com.rd.zhongqipiaoetong.module.account.model.CouponMo;
import com.rd.zhongqipiaoetong.module.product.viewmodel.ChoiceCouponVM;
import com.rd.zhongqipiaoetong.module.product.viewmodel.InvestmentVM;

import java.util.ArrayList;

/**
 * <p/>
 * Description: 选择红包界面
 */
public class ChoiceCouponAct extends BaseActivity {
    private ProductChoiceCouponBinding binding;
    private InvestmentVM               vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<CouponMo> couponMoList          = (ArrayList<CouponMo>) getIntent().getSerializableExtra(BundleKeys.COUPONLIST);
        double              tenderRedEnvelopeRate = getIntent().getDoubleExtra(BundleKeys.TENDERREDENVELOPERATE, 0);
        String              tenderMoney           = getIntent().getStringExtra(BundleKeys.TENDERMONEY);
        String redString = getIntent().getStringExtra(BundleKeys.REDSTRING);
        binding = DataBindingUtil.setContentView(this, R.layout.product_choice_coupon);
        binding.setViewModel(new ChoiceCouponVM(couponMoList, tenderRedEnvelopeRate, tenderMoney,redString));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.investment_xzhb);
    }
}