package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.activity.CreditorListAct;
import com.rd.zhongqipiaoetong.module.account.viewmodel.TransferedVM;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/21 上午10:22
 * <p/>
 * Description: 已转让列表({@link CreditorListAct})
 */
public class TransferedListFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private TransferedVM              listVM;
    private UpDataListener<Boolean>   upDataListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.common_recycler_view, container, false);
        listVM = new TransferedVM();
        binding.setViewModel(listVM);
        binding.executePendingBindings();
        ready();
        return binding.getRoot();
    }

    public TransferedListFrag create(UpDataListener<Boolean> upDataListener) {
        this.upDataListener = upDataListener;
        return this;
    }

    @Override
    protected void lazyLoad() {
        if (listVM.emptyState.isLoading()) {
            listVM.req_data(listVM.getPtrFrame());
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