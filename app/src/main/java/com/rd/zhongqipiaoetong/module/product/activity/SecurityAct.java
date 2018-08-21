package com.rd.zhongqipiaoetong.module.product.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.product.fragment.GuaranteeFrag;
import com.rd.zhongqipiaoetong.module.product.fragment.RiskFrag;

/**
 * Created by tdh on 2017/4/27.
 */
public class SecurityAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewPagerBinding binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        BaseViewPagerVM        vm      = new BaseViewPagerVM(getResources().getStringArray(R.array.securityTips),getSupportFragmentManager());
        vm.items.add(new GuaranteeFrag());
        vm.items.add(new RiskFrag());
        binding.setViewModel(vm);
        binding.executePendingBindings();
        binding.tabs.setupWithViewPager(binding.pager);
        binding.pager.setOffscreenPageLimit(vm.items.size() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.security_title);
    }
}
