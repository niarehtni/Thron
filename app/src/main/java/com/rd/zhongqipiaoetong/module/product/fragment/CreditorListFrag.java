package com.rd.zhongqipiaoetong.module.product.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.product.viewmodel.CreditorListVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/7 17:13
 * <p/>
 * Description:债券转让Frag({@link ProductListFrag})
 */
public class CreditorListFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private CreditorListVM            listVM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        listVM = new CreditorListVM();
        binding.setViewModel(listVM);
        binding.executePendingBindings();
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
//        if (listVM.emptyState.isLoading()) {
//            listVM.req_data(listVM.getPtrFrame());
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listVM.getPtrFrame().autoRefresh();
    }
}