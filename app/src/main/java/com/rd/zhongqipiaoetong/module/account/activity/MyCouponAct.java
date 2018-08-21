package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.MyCouponFrag;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 9:40
 * <p/>
 * Description: 我的优惠券
 */
public class MyCouponAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonViewPagerBinding binding   = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        String[]               couponTip = FeatureUtils.getCouponTips(this);
        BaseViewPagerVM        viewModel = new BaseViewPagerVM(couponTip, getSupportFragmentManager());
        binding.setViewModel(viewModel);
        if (FeatureUtils.getEnableRedPackeModule()) {// 0 - 红包
            viewModel.items.add(new MyCouponFrag().create(Constant.NUMBER_0));
        }
        if (FeatureUtils.getEnableExperienceModule()) {// 1 - 体验券
            viewModel.items.add(new MyCouponFrag().create(Constant.NUMBER_1));
        }
        if (FeatureUtils.getEnableUpRateModule()) {// 2 - 加息券
            viewModel.items.add(new MyCouponFrag().create(Constant.NUMBER_2));
        }
        binding.executePendingBindings();

        binding.tabs.setupWithViewPager(binding.pager);
        binding.tabs.setBackgroundResource(R.color.white);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.coupon_title);
    }
}