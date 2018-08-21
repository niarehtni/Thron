package com.rd.zhongqipiaoetong.module.account.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.databinding.AccountPwdManageActBinding;
import com.rd.zhongqipiaoetong.module.account.viewmodel.PwdManageVM;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/25 16:55
 * <p/>
 * Description: 密码管理
 */
public class PwdManageAct extends BaseActivity {
    private AccountPwdManageActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.account_pwd_manage_act);
        int hasSetPayPwd = getIntent().getIntExtra(BundleKeys.HASSETPAYPWD, 0);
        binding.setViewModel(new PwdManageVM(hasSetPayPwd));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(R.string.pwd_title);
    }

    @Override
    public void finish() {
        setResult(BundleKeys.RESULT_CODE_PWDMANAGE);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.getViewModel().onActivityResult(requestCode, resultCode, data);
    }
}