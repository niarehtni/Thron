package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.view.View;
import android.widget.EditText;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.AccountModfiypwdActBinding;
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
 * Description: 修改登陆密码VM({@link ModfiyLoginPwdAct})
 */
public class ModifyLoginPwdVM {
    public ObservableField<String> oldPwd = new ObservableField<>();
    public ObservableField<String> newPwd = new ObservableField<>();
    private AccountModfiypwdActBinding binding;
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.edListCheck(edList));// && InputCheck.checkPwdRule(binding.modifypwdEdNew.getText().toString()));
            enable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public LinkedList<EditText>                 edList  = new LinkedList<>();
    public ObservableField<Boolean>             enable  = new ObservableField<>(false);

    public ModifyLoginPwdVM(AccountModfiypwdActBinding binding) {
        this.binding = binding;
        binding.modifypwdEdOld.setFilters(CommonMethod.getEdFilter());
        binding.modifypwdEdNew.setFilters(CommonMethod.getEdFilter());
    }

    public void modifyClick(View view) {

        if (oldPwd.get() == null) {
            Utils.toast(view.getContext().getString(R.string.pwd_modifylogin_notnull_old));
            return;
        }
        if (newPwd.get() == null) {
            Utils.toast(view.getContext().getString(R.string.pwd_modifylogin_notnull_new));
            return;
        }

        if (!InputCheck.checkPwdRule(oldPwd.get())) {
            Utils.toast(view.getContext().getString(R.string.register_pwd_old_err));
            return;
        }

        if (!InputCheck.checkPwdRule(newPwd.get())) {
            Utils.toast(view.getContext().getString(R.string.register_pwd_new_err));
            return;
        }

//        String oldpwd = Base64.encodeToString(oldPwd.get().getBytes(), Base64.DEFAULT);
//        String newpwd = Base64.encodeToString(newPwd.get().getBytes), Base64.DEFAULT);
        String oldpwd = oldPwd.get();
        String newpwd = newPwd.get();
        req_data(oldpwd, newpwd);
    }

    private void req_data(String oldPwd, String newPwd) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).modifyLoginPwd(oldPwd, newPwd);
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