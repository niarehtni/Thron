package com.rd.zhongqipiaoetong.module.product.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRdwebviewBinding;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.model.ProductInfoMo;
import com.rd.zhongqipiaoetong.module.product.viewmodel.BorrowerInfoListVM;

import java.util.List;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/9 15:34
 * <p/>
 * Description:借款资料列表({@link ProductInfoAct})
 */
public class BorrowerInfoListFrag extends BaseFragment {
    private CommonRecyclerViewBinding binding;
    private BorrowerInfoListVM        vm;
    private ProductInfoMo             productInfoMo;

    public static BorrowerInfoListFrag newInstance(ProductInfoMo productInfoMo) {
        BorrowerInfoListFrag borrowerInfoListFrag = new BorrowerInfoListFrag();
        Bundle               bundle               = new Bundle();
        bundle.putSerializable(BundleKeys.PRODUCTINFO, productInfoMo);
        borrowerInfoListFrag.setArguments(bundle);
        return borrowerInfoListFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        productInfoMo = (ProductInfoMo) getArguments().getSerializable(BundleKeys.PRODUCTINFO);
        vm = new BorrowerInfoListVM();
        if (productInfoMo != null && productInfoMo.getBorrowVO().getBorrowImageList() != null && productInfoMo.getBorrowVO().getBorrowImageList().size() > 0) {
            setImages(productInfoMo.getBorrowVO().getBorrowImageList(), productInfoMo.getQiniu_domain());
        } else {
            vm.setImages(null);
        }
        binding.setViewModel(vm);
        binding.getRoot().setBackgroundColor(Color.WHITE);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    public void setImages(List<ProductInfoMo.ImageList> list, String qinniuDomain) {

        for (ProductInfoMo.ImageList imageList : list) {
            imageList.setImageUrl(qinniuDomain + imageList.getImageUrl());
        }
        vm.setImages(list);
    }
}