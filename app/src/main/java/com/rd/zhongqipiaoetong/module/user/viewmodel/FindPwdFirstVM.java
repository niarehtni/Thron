package com.rd.zhongqipiaoetong.module.user.viewmodel;

import android.databinding.ObservableField;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.animation.ShrinkLogic;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.UserFindpwdFirstActBinding;
import com.rd.zhongqipiaoetong.module.user.activity.ForgetAct;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.UserService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Description: 找回密码({@link ForgetAct})
 */
public class FindPwdFirstVM {
    private UserFindpwdFirstActBinding binding;
    private String                     sesssionId;
    /** 动画显示逻辑控制 */
    private ShrinkLogic                shrinkLogic;
    /**
     * 密码是否可见，默认不可见
     */
    private boolean                              enable    = false;

    /**
     * 监听EditText 变化
     */
    public  EditTextFormat.EditTextFormatWatcher watcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            btnEnable.set(InputCheck.edListCheck(edList));
            btnEnable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public  LinkedList<EditText>                 edList    = new LinkedList<>();
    public  ObservableField<Boolean>             btnEnable = new ObservableField<>(false);

    /**
     * 监听 code 需监听的editText
     */
    public ObservableField<Boolean>             codeEnable  = new ObservableField<>(false);
    public EditTextFormat.EditTextFormatWatcher codeWatcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            btnEnable.set(InputCheck.edListCheck(edList));
            btnEnable.notifyChange();
        }
    };


    public ObservableField<Boolean>             pwdEnable  = new ObservableField<>(false);
    /**
     * 监听 Pwd  EditText 变化
     */
    public  EditTextFormat.EditTextFormatWatcher pwdWatcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            pwdEnable.set(InputCheck.checkPwdRule(str));
            pwdEnable.notifyChange();
        }
    };
    private String code;

    public FindPwdFirstVM(UserFindpwdFirstActBinding binding) {
        this.binding = binding;
        binding.findpwdPwd.setFilters(CommonMethod.getEdFilter());
        shrinkLogic = new ShrinkLogic(binding.findpwdFirst,binding.appLogo);
        shrinkLogic.shrinkAnimation();
    }

    /**
     * 获取验证码(点击事件)
     */
    public void getCodeClick(View view) {
        String phone = binding.findpwdPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.register_phone_hint));
            return;
        }
        if (!InputCheck.isMobileNO(phone)) {
            Utils.toast(view.getContext().getString(R.string.register_phone_err));
            return;
        }

        res_getCode(phone);
    }

    /**
     * 下一步(点击事件)
     */
    public void nextClick(View view) {
        String phone = binding.findpwdPhone.getText().toString().trim();

        if (phone.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.register_phone_hint));
            return;
        }

        if (!InputCheck.isMobileNO(phone)) {
            Utils.toast(view.getContext().getString(R.string.register_phone_err));
            return;
        }
        code = binding.findpwdCode.getText().toString().trim();

        if (code.equals("")) {
            Utils.toast(view.getContext().getString(R.string.resetpaypwd_error_code1));
            return;
        }

        if (!InputCheck.checkCode(code)) {
            Utils.toast(view.getContext().getString(R.string.register_code_err));
            return;
        }
        res_checkcode(phone, code);



    }

    /**
     * 找回密码(点击事件)
     */
    public void sureClick(View view) {
        String resetpwd = binding.findpwdPwd.getText().toString().trim();
        String phone    = binding.findpwdPhone.getText().toString().trim();

        if (resetpwd.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.register_pwd_notnull));
            return;
        }

        if (!InputCheck.checkPwdRule(resetpwd)) {
            Utils.toast(view.getContext().getString(R.string.register_pwd_err));
            return;
        }
//        resetpwd = Base64.encodeToString(resetpwd.getBytes(), Base64.DEFAULT);
        res_findpwd(phone, resetpwd, code);
    }

    public void eyesClick(View view) {
        if (enable) {
            binding.findpwdPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.findpwdEyes.setImageDrawable(binding.getRoot().getResources().getDrawable(R.drawable.signup_bxs_pressed));
        } else {
            binding.findpwdEyes.setImageDrawable(binding.getRoot().getResources().getDrawable(R.drawable.signup_bxs_normal));
            binding.findpwdPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        enable = !enable;
    }

    public void backEvent() {
        if (binding.findpwdFirst.getVisibility() == View.GONE) {
            shrinkLogic.changeAnimationView(binding.findpwdFirst);

            binding.findpwdFirst.setVisibility(View.VISIBLE);
            binding.findpwdSecond.setVisibility(View.GONE);
            binding.titleBar.appbar.setTitle(R.string.findpwd_title);
        } else {
            ActivityUtils.pop();
        }
    }

    /**
     * 焦点改变事件
     *
     * @param view
     *         用户名、密码输入框
     * @param hasFocus
     *         是否获得焦点
     */
    public void focusChange(View view, boolean hasFocus) {
        shrinkLogic.focusChange(view, hasFocus);
    }

    /**
     * 界面碰触事件
     */
    public boolean layoutTouch(View view, MotionEvent event) {
        return shrinkLogic.layoutTouch(view);
    }
    ///////////////////////////////////////////////////////////////////////////
    // 网络请求
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 获取验证码
     *
     * @param phone
     */
    private void res_getCode(String phone) {
        String ts = String.valueOf(System.currentTimeMillis());
        String sb = MD5Util.getMD5Str(BaseParams.APP_SECRET + ts);
        sb = MD5Util.getMD5Str(sb + BaseParams.APP_KEY);
        sb = MD5Util.getMD5Str(sb + phone).toUpperCase();
        RDClient.getInstance().getInject().setTs(ts);
        Call<String> call = RDClient.getService(UserService.class).getFindPwdCode(phone,sb);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                binding.findpwdTimebtn.runTime();
                Utils.toast(ActivityUtils.peek().getString(R.string.register_code_send));
            }
        });
    }

    /**
     * 验证验证码是否正确
     *
     * @param phone
     * @param validCode
     */
    private void res_checkcode(final String phone, final String validCode) {
        Call<HttpResult> call = RDClient.getService(UserService.class).checkFindPwdCode(phone, validCode);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {

                shrinkLogic.changeAnimationView(binding.findpwdSecond);
                binding.findpwdFirst.setVisibility(View.GONE);
                binding.findpwdSecond.setVisibility(View.VISIBLE);
                binding.titleBar.appbar.setTitle(R.string.findpwd_setpwd);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().getBody());
                    sesssionId = jsonObject.getJSONObject("resData").getString("sessionId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 找回登录密码
     *
     * @param phone
     * @param resetpwd
     * @param code
     */
    private void res_findpwd(String phone, String resetpwd, String code) {
        Call<String> call = RDClient.getService(UserService.class).checkFindPwd(phone, resetpwd, code);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                Utils.toast(ActivityUtils.peek().getString(R.string.login_find_pwd_success));
                ActivityUtils.pop();
            }
        });
    }
}