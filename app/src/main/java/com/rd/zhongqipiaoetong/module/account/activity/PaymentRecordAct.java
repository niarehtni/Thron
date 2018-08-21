package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.AlreadyPaymentFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.PendingPaymentFrag;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/8 13:55
 * <p/>
 * Description: 回款记录
 * <p/>
 * 待回款{@link PendingPaymentFrag}
 * <p/>
 * 已回款{@link AlreadyPaymentFrag}
 */
public class PaymentRecordAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CommonViewPagerBinding binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);

            BaseViewPagerVM viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.paymentRecordActTitles), getSupportFragmentManager());
            binding.setViewModel(viewModel);
            // 待回款
            viewModel.items.add(new PendingPaymentFrag());
            // 已回款
            viewModel.items.add(new AlreadyPaymentFrag());
            binding.executePendingBindings();

            binding.tabs.setupWithViewPager(binding.pager);
            binding.tabs.setBackgroundResource(R.color.white);
            binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.payment_record_title);
    }
}