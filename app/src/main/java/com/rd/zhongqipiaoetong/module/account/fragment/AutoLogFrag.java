package com.rd.zhongqipiaoetong.module.account.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseLazyFragment;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AutoLogVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/27 下午8:41
 * <p/>
 * Description:
 */
public class AutoLogFrag extends BaseLazyFragment {
    private CommonRecyclerViewBinding binding;
    private AutoLogVM                 autoLogVM;
    /**
     * 1 - 投标成功
     * 0 - 投标失败
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
        autoLogVM = new AutoLogVM();
        binding.setViewModel(autoLogVM);
        binding.executePendingBindings();
        ready();
        return binding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        // 如果加载页面可见，则表示初次进入界面，需要请求数据
        if (autoLogVM.emptyState.isLoading()) {
            autoLogVM.req_data(autoLogVM.getPtrFrame());
        }
    }
}
