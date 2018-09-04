package com.rd.zhongqipiaoetong.module.homepage.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

import com.rd.zhongqipiaoetong.MainVM;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.ui.BaseFragment;
import com.rd.zhongqipiaoetong.databinding.HomeFragBinding;
import com.rd.zhongqipiaoetong.module.homepage.viewmodel.HomeVM;
import com.rd.banner.banner.ConvenientBanner;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/1 下午7:10
 * <p/>
 * Description: 首页({@link MainVM#mHomeFrag})
 */
public class HomeFrag extends BaseFragment {
    private ConvenientBanner convenientBanner;
    HomeFragBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_frag, container, false);
        convenientBanner = binding.convenientBanner;
        binding.setViewModel(new HomeVM(binding,convenientBanner));
        binding.convenientBanner.invalidate();
        binding.recycler.setNestedScrollingEnabled(false);
        binding.recyclerActivity.setNestedScrollingEnabled(false);
        if(FeatureConfig.enableExtButtonFeature == 1){
            binding.homeExtraButton.setVisibility(View.VISIBLE);
        }
        initBannerSize();
        return binding.getRoot();
    }

    private void initBannerSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        LayoutParams  params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (dm.widthPixels *7.0/15));
        convenientBanner.setLayoutParams(params);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(binding.ptrAll != null){
            binding.ptrAll.autoRefresh();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            convenientBanner.stopTurning();
        } else {
            convenientBanner.startTurning(5000);
        }
    }
}