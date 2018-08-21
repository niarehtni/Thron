package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.CommonRecyclerViewBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AutoLogVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/27 下午8:16
 * <p/>
 * Description:
 */
public class AutoInvestLogAct extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonRecyclerViewBinding binding   = DataBindingUtil.setContentView(this, R.layout.common_recycler_view);
        AutoLogVM                 autoLogVM = new AutoLogVM();
        autoLogVM.hidden.set(false);
        binding.setViewModel(autoLogVM);
        binding.executePendingBindings();
        autoLogVM.req_data(autoLogVM.getPtrFrame());
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.auto_log_title);
    }
}
