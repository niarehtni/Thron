package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductChoiceUpBinding;
import com.rd.zhongqipiaoetong.module.account.model.UpRateMo;
import com.rd.zhongqipiaoetong.module.product.viewmodel.ChoiceUpVM;
import com.rd.zhongqipiaoetong.module.product.viewmodel.InvestmentVM;

import java.util.ArrayList;

/**
 * <p/>
 * Description: 选择加息券
 */
public class ChoiceUpAct extends BaseActivity {
    private ProductChoiceUpBinding binding;
    private InvestmentVM           vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<UpRateMo> upMoArrayList = (ArrayList<UpRateMo>) getIntent().getSerializableExtra(BundleKeys.UPLIST);
        String              upId      = getIntent().getStringExtra(BundleKeys.UPID);
        binding = DataBindingUtil.setContentView(this, R.layout.product_choice_up);
        binding.setViewModel(new ChoiceUpVM(upMoArrayList, upId));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.investment_xzup);
    }
}