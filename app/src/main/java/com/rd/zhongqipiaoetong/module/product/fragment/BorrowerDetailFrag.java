package com.rd.zhongqipiaoetong.module.product.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.ProductBorrowerDetailActBinding;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.viewmodel.BorrowerDetailVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/9 15:27
 * <p/>
 * Description: 借款人详情({@link ProductInfoAct})
 */
public class BorrowerDetailFrag extends BaseLazyFragment {
    private ProductBorrowerDetailActBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.product_borrower_detail_act, container, false);
        binding.setViewModel(new BorrowerDetailVM());
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {

    }
}