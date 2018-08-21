package com.rd.zhongqipiaoetong.module.more.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.MainVM;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseFragment;
import com.rd.zhongqipiaoetong.databinding.MoreFragBinding;
import com.rd.zhongqipiaoetong.module.more.viewmodel.MoreVM;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/16 下午4:15
 * <p/>
 * Description: 更多页面({@link MainVM#mMoreFrag})
 */
public class MoreFrag extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MoreFragBinding binding   = DataBindingUtil.inflate(inflater, R.layout.more_frag, container, false);
        MoreVM          viewModel = new MoreVM(binding);
        binding.setViewModel(viewModel);
        binding.titleBar.appbar.setTitle(R.string.more_title);
        binding.titleBar.appbar.setBackOption(false);
        return binding.getRoot();
    }

    /**
     * 使用微博SSO授权添加代码
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}