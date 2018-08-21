package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.AccountEmailBindBinding;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.util.LinkedList;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: JinF
 * E-mail: jf@erongdu.com
 * Date: 2016/10/12 10:59
 * <p/>
 * Description:
 */
public class EmailBindVM {
    private AccountEmailBindBinding binding;
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher     = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.edListCheck(edList));
            enable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public LinkedList<EditText>                 edList      = new LinkedList<>();
    public ObservableField<Boolean>             enable      = new ObservableField<>(false);
    /**
     * 监听 code 需监听的editText
     */
    public ObservableField<Boolean>             codeEnable  = new ObservableField<>(false);
    public EditTextFormat.EditTextFormatWatcher codeWatcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            if (!binding.emailTimebtn.isRun()) {
                codeEnable.set(!TextUtils.isEmpty(binding.emailAccount.getText()));
                codeEnable.notifyChange();
            }
            enable.set(InputCheck.edListCheck(edList));
            enable.notifyChange();
        }
    };

    public EmailBindVM(AccountEmailBindBinding binding) {
        this.binding = binding;
    }

    /** 获取邮箱验证码 */
    public void getCodeClick(final View view) {
        String email = binding.emailAccount.getText().toString();
        if (email.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.email_hint));
            return;
        }
        if(!InputCheck.isEmail(email)){
            Utils.toast(view.getContext().getString(R.string.email_hint_error));
            return;
        }
        TreeMap<String, Object> bindMo = new TreeMap<>();
        bindMo.put("email", email);
        Call<HttpResult> call = RDClient.getService(AccountService.class).getEmailCode(bindMo);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                Utils.toast(view.getContext().getString(R.string.email_get_code_success));
                binding.emailTimebtn.runTime();
            }
        });
    }

    /** 提交绑定 */
    public void bindClick(final View view) {
        String email = binding.emailAccount.getText().toString();
        String code  = binding.emailCode.getText().toString();
        if (email.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.email_hint));
            return;
        }
        if (code.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.email_hint_err));
            return;
        }
        TreeMap<String, Object> bindMo = new TreeMap<>();
        bindMo.put("email", email);
        bindMo.put("validCode", code);
        Call<HttpResult> call = RDClient.getService(AccountService.class).bindEmail(bindMo);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                Utils.toast(view.getContext().getString(R.string.email_bind_success));
                ActivityUtils.pop(ActivityUtils.peek());
            }
        });
    }
}
