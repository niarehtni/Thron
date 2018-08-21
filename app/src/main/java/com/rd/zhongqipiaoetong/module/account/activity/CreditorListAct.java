package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.common.ui.BaseViewPagerVM;
import com.rd.zhongqipiaoetong.databinding.CommonViewPagerBinding;
import com.rd.zhongqipiaoetong.module.account.fragment.CanTransferListFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.TransferedListFrag;
import com.rd.zhongqipiaoetong.module.account.fragment.TransferingListFrag;
import com.rd.zhongqipiaoetong.utils.UpDataListener;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 2016/3/7 16:49
 * <p/>
 * Description:转让列表Act
 * 可转让列表({@link CanTransferListFrag})
 * <p/>
 * 转让中列表({@link TransferingListFrag})
 * <p/>
 * 已转让列表({@link TransferedListFrag})
 */
public class CreditorListAct extends BaseActivity {
    private CommonViewPagerBinding binding;
    private BaseViewPagerVM        viewModel;
    //可转让列表
    private CanTransferListFrag    canTransferListFrag;
    //转让中列表
    private TransferingListFrag    transferingListFrag;
    //已转让列表
    private TransferedListFrag     transferedListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_view_pager);
        viewModel = new BaseViewPagerVM(getResources().getStringArray(R.array.creditorListTitles), getSupportFragmentManager());
        binding.setViewModel(viewModel);

        //三列表数据联动，通知列表页面刷新数据
        UpDataListener<Boolean> upDataListener = new UpDataListener<Boolean>() {
            @Override
            public void updata(int type, Boolean o) {
                canTransferListFrag.autoRefresh();
                transferingListFrag.autoRefresh();
                transferedListFrag.autoRefresh();
            }
        };
        // 可转让列表
        canTransferListFrag = new CanTransferListFrag().create(upDataListener);
        viewModel.items.add(canTransferListFrag);

        // 转让中列表
        transferingListFrag = new TransferingListFrag().create(upDataListener);
        viewModel.items.add(transferingListFrag);

        // 已转让列表
        transferedListFrag = new TransferedListFrag().create(upDataListener);
        viewModel.items.add(new TransferedListFrag());
        binding.executePendingBindings();

        binding.tabs.setupWithViewPager(binding.pager);
        binding.tabs.setBackgroundResource(R.color.white);
        binding.pager.setOffscreenPageLimit(viewModel.items.size() - 1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.creditor_title);
    }
}