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
import com.rd.zhongqipiaoetong.module.product.viewmodel.FinancingListVM;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/7 16:58
 * <p/>
 * Description: 定期理财Frag({@link ProductListFrag})
 */
public class FinanceListFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private FinancingListVM          listVM;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        listVM = new FinancingListVM(binding);
        binding.setViewModel(listVM);
        binding.executePendingBindings();
        //解决ScrollView和RecyclerView滑动粘连的问题
        binding.recycler.setNestedScrollingEnabled(false);
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
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