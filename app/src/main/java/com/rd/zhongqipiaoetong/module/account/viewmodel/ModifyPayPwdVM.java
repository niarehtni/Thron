package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.AccountModfiypaypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.ModfiyLoginPwdAct;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Description: 修改支付密码VM({@link ModfiyLoginPwdAct})
 */
public class ModifyPayPwdVM {
    public ObservableField<String> oldPwd   = new ObservableField<>();
    public ObservableField<String> newPwd   = new ObservableField<>();
    public ObservableField<String> againPwd = new ObservableField<>();
    private AccountModfiypaypwdActBinding binding;
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.edListCheck(edList)&& InputCheck.checkPwdRule(binding.modifypawpwdEdNew.getText().toString()));
            enable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public LinkedList<EditText>                 edList  = new LinkedList<>();
    public ObservableField<Boolean>             enable  = new ObservableField<>(false);

    public ModifyPayPwdVM(AccountModfiypaypwdActBinding binding) {
        this.binding = binding;
        binding.modifypawpwdEdOld.setFilters(CommonMethod.getEdFilter());
        binding.modifypawpwdEdNew.setFilters(CommonMethod.getEdFilter());
        binding.modifypawpwdEdNewagain.setFilters(CommonMethod.getEdFilter());
    }

    public void modifyClick(View view) {

        if (oldPwd.get() == null || oldPwd.get().equals("")) {
            Utils.toast(view.getContext().getString(R.string.pwd_modify_notnull_old));
            return;
        }
        if (newPwd.get() == null || newPwd.get().equals("")) {
            Utils.toast(view.getContext().getString(R.string.pwd_modify_notnull_new));
            return;
        }

        if (againPwd.get() == null || againPwd.get().equals("")) {
            Utils.toast(view.getContext().getString(R.string.pwd_modify_notnull_newagain));
            return;
        }

        if (!InputCheck.checkPwdRule(oldPwd.get())) {
            Utils.toast(view.getContext().getString(R.string.pwd_modify_error_old));
            return;
        }

        if (!InputCheck.checkPwdRule(newPwd.get())) {
            Utils.toast(view.getContext().getString(R.string.pwd_modify_error_new));
            return;
        }

        if (!newPwd.get().equals(againPwd.get())) {
            Utils.toast(view.getContext().getString(R.string.pwd_modifypaynpww_notequals));
            return;
        }

        String oldpwd = Base64.encodeToString(oldPwd.get().getBytes(), Base64.DEFAULT);
        String newpwd = Base64.encodeToString(newPwd.get().getBytes(), Base64.DEFAULT);
        req_data(oldpwd, newpwd);
    }

    private void req_data(String oldPwd, String newPwd) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).modifyPayPwd(oldPwd, newPwd);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                if (response.body().getResCode() == 1) {
                    ActivityUtils.pop();
                    Utils.toast(response.body().getResMsg());
                }
            }
        });
    }
}