package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.MyCouponVM;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/30/16.
 */
public class MyCouponFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private MyCouponVM                couponVM;
    /**
     * 0 - 红包
     * 1 - 体验券
     * 3 - 加息券
     */
    private int                       type;

    public Fragment create(int type) {
        this.type = type;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        couponVM = new MyCouponVM(type, FeatureConfig.enableCouponChange == 1);
        binding.setViewModel(couponVM);
        binding.executePendingBindings();
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        if (couponVM.emptyState.isLoading()) {
            couponVM.req_data(couponVM.getPtrFrame());
        }
    }
}
