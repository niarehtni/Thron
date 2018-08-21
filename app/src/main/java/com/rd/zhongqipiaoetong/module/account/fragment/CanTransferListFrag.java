package com.rd.zhongqipiaoetong.module.account.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.CreditorListAct;
import com.rd.zhongqipiaoetong.module.account.viewmodel.CanTransferVM;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/21 上午10:21
 * <p/>
 * Description: 可转让列表({@link CreditorListAct})
 */
public class CanTransferListFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private CanTransferVM             listVM;
    private UpDataListener<Boolean>   upDataListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        listVM = new CanTransferVM(this);
        binding.setViewModel(listVM);
        binding.executePendingBindings();
        ready();
        return binding.getRoot();
    }

    public CanTransferListFrag create(UpDataListener<Boolean> listener) {
        this.upDataListener = listener;
        return this;
    }

    @Override
    protected void lazyLoad() {
        if (listVM.emptyState.isLoading()) {
            listVM.req_data(listVM.getPtrFrame());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BundleKeys.REQUEST_CODE_DOTRANSFER && resultCode == BundleKeys.RESULT_CODE_DOTRANSFER) {
            upDataListener.updata(0, true);
            //通知act 数据变化，其他列表同时刷新数据
        }
    }

    /**
     * 刷新数据
     */
    public void autoRefresh() {
        if (listVM == null) {
            return;
        }
        if (listVM.getPtrFrame() == null) {
            return;
        }
        listVM.getPtrFrame().autoRefresh();
    }
}