package com.rd.zhongqipiaoetong.module.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.animation.ShrinkLogic;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.UserRegisterFirstActBinding;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.module.user.activity.RegisterFirstAct;
import com.rd.zhongqipiaoetong.module.user.activity.RegisterSecondAct;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.UserService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.encryption.MD5Util;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/17 下午7:09
 * <p/>
 * Description: 注册控制({@link RegisterFirstAct})
 */
public class RegisterFirstVM {
    private boolean isFromH5 = false;
    /** 动画显示逻辑控制 */
    private ShrinkLogic                 shrinkLogic;
    /** 注册入参 */
    private UserRegisterFirstActBinding binding;
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.edListCheck(edList));
            enable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public LinkedList<EditText>                 edList  = new LinkedList<>();
    public ObservableField<Boolean>             enable  = new ObservableField<>(false);
    /**
     * 监听 code 需监听的editText
     */
    public ObservableField<Boolean>             codeEnable  = new ObservableField<>(false);
    public EditTextFormat.EditTextFormatWatcher codeWatcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            if(!binding.registerTimebtn.isRun()){
                codeEnable.set(!TextUtils.isEmpty(binding.registerPhone.getText()));
                codeEnable.notifyChange();
            }
            enable.set(InputCheck.edListCheck(edList));
            enable.notifyChange();
        }
    };

    public RegisterFirstVM(UserRegisterFirstActBinding binding, boolean isFromH5) {
        this.isFromH5 = isFromH5;
        this.binding = binding;
        shrinkLogic = new ShrinkLogic(binding.registerCenter, binding.appLogo);
        shrinkLogic.shrinkAnimation();
    }

    /**
     * 获取验证码
     */
    public void getCodeClick(View view) {
        String phone = binding.registerPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.register_phone_hint));
            return;
        }

        if (!InputCheck.isMobileNO(phone)) {
            Utils.toast(view.getContext().getString(R.string.register_phone_err));
            return;
        }

        res_checkPhone(phone);
    }

    /**
     * 下一步
     */
    public void nextClick(View view) {
        String phone = binding.registerPhone.getText().toString().trim();

        if (phone.isEmpty()) {
            Utils.toast(view.getContext().getString(R.string.register_phone_hint));
            return;
        }

        if (!InputCheck.isMobileNO(phone)) {
            Utils.toast(view.getContext().getString(R.string.register_phone_err));
            return;
        }
        String code = binding.registerCode.getText().toString().trim();
        if (!InputCheck.checkCode(code)) {
            Utils.toast(view.getContext().getString(R.string.register_code_err));
            return;
        }
        res_checkcode(phone, code);
    }

    /**
     * 去登录界面
     */
    public void loginClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.H5,isFromH5);
        ActivityUtils.push(LoginAct.class,intent);
        ActivityUtils.peek().finish();
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

    private void res_checkPhone(final String phone){
        Call<String> call = RDClient.getInstance().getService(UserService.class).checkPhone(phone);
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                res_getCode(phone);
            }
        });
    }

    private void res_getCode(String phone) {
        String ts = String.valueOf(System.currentTimeMillis());
        String sb = MD5Util.getMD5Str(BaseParams.APP_SECRET + ts);
        sb = MD5Util.getMD5Str(sb + BaseParams.APP_KEY);
        sb = MD5Util.getMD5Str(sb + phone).toUpperCase();
        RDClient.getInstance().getInject().setTs(ts);
        Call<String> call = RDClient.getService(UserService.class).getRegisterCode(phone,sb);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                binding.registerTimebtn.runTime();
                Utils.toast(ActivityUtils.peek().getString(R.string.register_code_send));
            }
        });
    }

    private void res_checkcode(final String phone, final String validcode) {
        Call<String> call = RDClient.getService(UserService.class).checkRegisterCode(phone, validcode);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.PHONE, phone);
                intent.putExtra(BundleKeys.VALIDCODE, validcode);
                intent.putExtra(BundleKeys.H5,isFromH5);
                ActivityUtils.push(RegisterSecondAct.class, intent);
                ActivityUtils.peek().finish();
            }
        });
    }
}