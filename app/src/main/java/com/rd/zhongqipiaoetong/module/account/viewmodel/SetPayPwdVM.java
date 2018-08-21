package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.util.Base64;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.AccountSetpaypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.SetPayPwdAct;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Description: 设置支付密码VM({@link SetPayPwdAct})
 */
public class SetPayPwdVM {
    private AccountSetpaypwdActBinding binding;
    public ObservableField<String>              phone   = new ObservableField<>();
    public ObservableField<Boolean>             enable  = new ObservableField<>(false);
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.checkPwdRule(str));
            enable.notifyChange();
        }
    };

    public SetPayPwdVM(AccountSetpaypwdActBinding binding) {
        this.binding = binding;
        binding.setpaypwdEdPwd.setFilters(CommonMethod.getEdFilter());
        binding.setpaypwdEdPwdagain.setFilters(CommonMethod.getEdFilter());
    }

    public void sureClick(View view) {
        String paypwd    = binding.setpaypwdEdPwd.getText().toString().trim();
        String pwd_again = binding.setpaypwdEdPwdagain.getText().toString().trim();

        /*各种非人类的判断*/

        if (paypwd.equals("")) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_pwd));
            return;
        }

        if (!InputCheck.checkPwdRule(paypwd)) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_check_pwd));
            return;
        }

        if (pwd_again.equals("")) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_pwdagain));
            return;
        }

        if (!paypwd.equals(pwd_again)) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_notequal));
            return;
        }

        //加密
        paypwd = Base64.encodeToString(paypwd.getBytes(), Base64.DEFAULT);

        req_data(paypwd);
    }

    //设置支付密码
    private void req_data(String paypwd) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).setPayPwd(paypwd);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                if (response.body().getResCode() == 1) {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.HASSETPAYPWD_SUCCESSS, true);
                    ActivityUtils.pop(ActivityUtils.peek(), intent);
                    Utils.toast(response.body().getResMsg());
                }
            }
        });
    }
}