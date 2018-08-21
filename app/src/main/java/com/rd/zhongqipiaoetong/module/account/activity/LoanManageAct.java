package com.rd.zhongqipiaoetong.module.account.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountLoanContentBinding;
import com.rd.zhongqipiaoetong.databinding.AccountLoanHeadBinding;
import com.rd.zhongqipiaoetong.databinding.AccountLoanManageActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.LoanManageVM;
import com.rd.zhongqipiaoetong.view.pullToZoom.WaveView;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/11 14:58
 * <p/>
 * Description: 借款管理
 */
public class LoanManageAct extends BaseActivity {
    private AccountLoanManageActBinding binding;
    private LoanManageVM                viewModel;
    private WaveView                    zoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_loan_manage_act);
        LayoutInflater inflater = LayoutInflater.from(this);
        // 顶部布局
        AccountLoanHeadBinding headBinding = DataBindingUtil.inflate(inflater, R.layout.account_loan_head, null, false);
        // 顶部布局
        AccountLoanContentBinding contentBinding = DataBindingUtil.inflate(inflater, R.layout.account_loan_content, null, false);
        // 水波效果
        zoomView = (WaveView) inflater.inflate(R.layout.account_wave, null);
        // 是否显示水波纹
        zoomView.setIsOpen(false);

        viewModel = new LoanManageVM();
        binding.setViewModel(viewModel);
        headBinding.setViewModel(viewModel);
        contentBinding.setViewModel(viewModel);
        binding.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewModel.emptyState.setLoading(false);
            }
        }, 2000);
        binding.loanDetail.setHeaderView(headBinding.getRoot());
        binding.loanDetail.setContentView(contentBinding.getRoot());
        binding.loanDetail.setZoomView(zoomView);
        initHeight();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.loan_title);
        setAppBarColor(zoomView);
    }

    /**
     * 动态设置head view的高度
     */
    private void initHeight() {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int                       mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject  = new LinearLayout.LayoutParams(mScreenWidth, (int) (mScreenWidth / 3.0F));
        binding.loanDetail.setHeaderLayoutParams(localObject);
    }
}