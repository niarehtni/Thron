package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.ProductChoiceExpBinding;
import com.rd.zhongqipiaoetong.module.account.model.ExperienceMo;
import com.rd.zhongqipiaoetong.module.product.viewmodel.ChoiceExpVM;
import com.rd.zhongqipiaoetong.module.product.viewmodel.InvestmentVM;

import java.util.ArrayList;

/**
 * <p/>
 * Description: 选择体验券
 */
public class ChoiceExpAct extends BaseActivity {
    private ProductChoiceExpBinding binding;
    private InvestmentVM            vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<ExperienceMo> experienceMoArrayList = (ArrayList<ExperienceMo>) getIntent().getSerializableExtra(BundleKeys.EXPLIST);
        String                  expString                 = getIntent().getStringExtra(BundleKeys.EXPSTRING);
        String              tenderMoney           = getIntent().getStringExtra(BundleKeys.TENDERMONEY);
        double maxExp = getIntent().getDoubleExtra(BundleKeys.MAXEXP, 0);
        boolean isInfinity = getIntent().getBooleanExtra(BundleKeys.ISINFINITY, false);
        binding = DataBindingUtil.setContentView(this, R.layout.product_choice_exp);
        binding.setViewModel(new ChoiceExpVM(experienceMoArrayList, expString, tenderMoney, maxExp,isInfinity));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.investment_xzexp);
    }
}