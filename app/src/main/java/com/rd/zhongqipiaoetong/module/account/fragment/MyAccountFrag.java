package com.rd.zhongqipiaoetong.module.account.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rd.zhongqipiaoetong.MainVM;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.common.ui.BaseFragment;
import com.rd.zhongqipiaoetong.databinding.AccountBottomBinding;
import com.rd.zhongqipiaoetong.databinding.AccountHeadBinding;
import com.rd.zhongqipiaoetong.databinding.AccountMyAccountActBinding;
import com.rd.zhongqipiaoetong.module.account.AccountViewOnclick;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AccountBottomVM;
import com.rd.zhongqipiaoetong.module.account.viewmodel.AccountVM;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.view.pullToZoom.PullToZoomScrollViewEx;
import com.rd.zhongqipiaoetong.view.pullToZoom.WaveView;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/2/26 上午10:50
 * <p/>
 * Description: 我的账户中心({@link MainVM#mAccountFrag})
 */
public class MyAccountFrag extends BaseFragment {
    private AccountMyAccountActBinding binding;
    private AccountHeadBinding         headBinding;
    private AccountVM                  accountVM;
    private AccountBottomVM bottomVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.account_my_account_act, container, false);
        // 顶部布局
        headBinding = DataBindingUtil.inflate(inflater, R.layout.account_head, null, false);
        // 底部布局
        AccountBottomBinding bottomView = DataBindingUtil.inflate(inflater, R.layout.account_bottom, null, false);
//                initGrid(bottomView);
        // 水波效果
        WaveView zoomView = (WaveView) LayoutInflater.from(getActivity()).inflate(R.layout.account_wave, null);
        // 是否显示水波纹
        zoomView.setIsOpen(false);

        bottomVM = new AccountBottomVM(bottomView, new AccountViewOnclick() {
            @Override
            public void onClick(View view, int type) {
                accountVM.onClick(view, type);
            }
        });
        accountVM = new AccountVM(this,bottomVM);
        binding.setAccountVM(accountVM);
        headBinding.setAccountVM(accountVM);


        bottomView.setBottomVM(bottomVM);
        bottomView.executePendingBindings();
        bottomView.setAccountVM(accountVM);

        binding.accountDetail.setHeaderView(headBinding.getRoot());
        binding.accountDetail.setContentView(bottomView.getRoot());
        binding.accountDetail.setZoomView(zoomView);
        binding.accountDetail.setPullDownToRefresh(new PullToZoomScrollViewEx.PullDownToRefresh() {
            @Override
            public void onPullDownToRefresh() {
                accountVM.req_data();
            }
        });
        initHeight();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SharedInfo.getInstance().getValue(OauthTokenMo.class) != null && !TextUtils.isEmpty(SharedInfo.getInstance().getValue(OauthTokenMo.class).getOauthToken())){
            upData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            binding.accountDetail.getRootScrollView().scrollTo(0, 0);
        }
    }

    public void upData() {
        if (accountVM != null) {
            accountVM.req_data();
        }
    }

    /**
     * 动态设置head view的高度
     */
    private void initHeight() {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenWidth = localDisplayMetrics.widthPixels;
        headBinding.getRoot().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, headBinding.getRoot().getMeasuredHeight());
        binding.accountDetail.setHeaderLayoutParams(localObject);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RDPayment.getInstance().getPayController().resultCheck(PayController.TPYE_AUTH, requestCode, resultCode, data, null)) {
            refreshData(true);
        }
        refreshData(isRefresh(requestCode, resultCode));
    }

    /**
     * 刷新数据
     *
     * @param isRefresh
     */
    private void refreshData(boolean isRefresh) {
        if (isRefresh) {
            accountVM.req_data();
        }
    }

    /**
     * 是否满足刷新条件
     *
     * @param requestCode
     * @param resultCode
     *
     * @return
     */
    private boolean isRefresh(int requestCode, int resultCode) {
        if (requestCode == BundleKeys.REQUEST_CODE_MSGMANAGE && resultCode == BundleKeys.RESULT_CODE_MSGMANAGE) {//消息页面返回
            return true;
        }

        if (requestCode == BundleKeys.REQUEST_CODE_OPEN && resultCode == BundleKeys.RESULT_CODE_OPEN) {//开户
            return true;
        }
        if (requestCode == BundleKeys.REQUEST_CODE_INFO && resultCode == BundleKeys.RESULT_CODE_INFO) {//个人中心
            return true;
        }

        if (requestCode == BundleKeys.REQUEST_CODE_ASSESTS && resultCode == BundleKeys.RESULT_CODE_ASSESTS) {//资产统计
            return true;
        }

        if (requestCode == BundleKeys.REQUEST_CODE_RECHARGE && resultCode == BundleKeys.RESULT_CODE_RECHARGE) {//充值
            return true;
        }

        if (requestCode == BundleKeys.REQUEST_CODE_WITHDRAW && resultCode == BundleKeys.RESULT_CODE_WITHDRAW) {//充值
            return true;
        }

        if (requestCode == BundleKeys.REQUEST_CODE_PWDMANAGE && resultCode == BundleKeys.RESULT_CODE_PWDMANAGE) {//密码管理
            return true;
        }

        return false;
    }
}