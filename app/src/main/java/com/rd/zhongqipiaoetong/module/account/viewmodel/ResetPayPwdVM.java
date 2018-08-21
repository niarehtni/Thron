package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.databinding.AccountResetpaypwdActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.ResetPayPwdAct;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Description: 重置支付密码VM({@link ResetPayPwdAct})
 */
public class ResetPayPwdVM {
    private AccountResetpaypwdActBinding binding;
    public ObservableField<String> phone = new ObservableField<>();

    public ResetPayPwdVM(AccountResetpaypwdActBinding binding) {
        this.binding = binding;

        binding.resetpaypwdEdPwd.setFilters(CommonMethod.getEdFilter());
        binding.resetpaypwdEdPwdagain.setFilters(CommonMethod.getEdFilter());
        binding.resetpaypwdEdIdentify.setFilters(getEdFilter());
        req_phone();
    }

    public void sureClick(View view) {
        String id_card   = binding.resetpaypwdEdIdentify.getText().toString().trim();
        String code      = binding.resetpaypwdEdCode.getText().toString().trim();
        String new_pwd   = binding.resetpaypwdEdPwd.getText().toString().trim();
        String pwd_again = binding.resetpaypwdEdPwdagain.getText().toString().trim();

        /*各种非人类的判断*/

        if (id_card.equals("") || id_card.length() < 6) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_card));
            return;
        }

        if (code.equals("")) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_code1));
            return;
        }
        if (code.length() < 6) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_code2));
            return;
        }

        if (new_pwd.equals("")) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_pwd));
            return;
        }

        if (!InputCheck.checkPayPwd(new_pwd)) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_check_pwd));
            return;
        }
        if (pwd_again.equals("")) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_pwdagain));
            return;
        }

        if (!new_pwd.equals(pwd_again)) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_notequal));
            return;
        }

        //加密
        new_pwd = Base64.encodeToString(new_pwd.getBytes(), Base64.DEFAULT);
        id_card = Base64.encodeToString(id_card.getBytes(), Base64.DEFAULT);

        req_data(id_card, new_pwd, code);
    }

    public void getCode(View view) {
        req_phoneCode();
    }

    //重置支付密码
    private void req_data(String id_card, String new_pwd, String code) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).resetPayPwd(id_card, new_pwd, code);
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

    /**
     * 获取绑定的手机号
     */
    private void req_phone() {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        call.enqueue(new RequestCallBack<PersonInfoMo>() {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                SharedInfo.getInstance().setValue(PersonInfoMo.class, response.body());
                phone.set(response.body().getPhone());
            }
        });
    }

    /**
     * 获取验证m码
     */
    private void req_phoneCode() {
        Call<String> call = RDClient.getService(AccountService.class).resetPayPwdGetCode();
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                binding.resetpaypwdBtn.runTime();
                Utils.toast(ActivityUtils.peek().getString(R.string.register_code_send));
            }
        });
    }

    /**
     * 设置输入框前5为只能为数字,最后一位可以数字 x X
     *
     * @return
     */
    public static InputFilter[] getEdFilter() {
        InputFilter[] FilterArray = new InputFilter[2];
        FilterArray[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String input = dest.toString();
                if (input.length() < 5 && !InputCheck.checkIsNumber(source)) {
                    return "";
                } else if (input.length() >= 5 && !InputCheck.checkIsNumberOrX(source)) {
                    return "";
                }

                return null;
            }
        };

        FilterArray[1] = new InputFilter.LengthFilter(6);

        return FilterArray;
    }
}