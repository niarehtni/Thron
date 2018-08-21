package com.rd.zhongqipiaoetong.module.user.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.igexin.sdk.PushManager;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.animation.ShrinkLogic;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.UserRegisterSecondActBinding;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.module.user.model.dto.RegisterDTO;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.UserService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.SPUtil;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.statistics.DeviceInfoUtils;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/17 下午7:09
 * <p/>
 * Description: 注册控制
 */
public class RegisterSecondVM {
    private boolean isFromH5 = false;
    private String      phone;
    private String      code;
    /** 动画显示逻辑控制 */
    private ShrinkLogic shrinkLogic;
    /**
     * 密码是否可见，默认不可见
     */
    private boolean enable = false;
    /** 注册入参 */
    private UserRegisterSecondActBinding binding;
    /**
     * 手机imei
     */
    private String                       imei;
    /**
     * 监听EditText 变化
     */
    public EditTextFormat.EditTextFormatWatcher watcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
//            btnEnable.set(InputCheck.checkPwdRule(str));
            if(str.length()>=8||str.length()<=16){
                btnEnable.set(true);
            }else{
                btnEnable.set(false);
            }
            btnEnable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public LinkedList<EditText>                 edList    = new LinkedList<>();
    public ObservableField<Boolean>             btnEnable = new ObservableField<>(false);
    public ObservableField<Boolean>             isVisible = new ObservableField<>(false);

    public RegisterSecondVM(String phone, String code, UserRegisterSecondActBinding binding, boolean isFromH5) {
        this.isFromH5 = isFromH5;
        this.phone = phone;
        this.code = code;
        this.binding = binding;
        shrinkLogic = new ShrinkLogic(binding.registerCenter, binding.appLogo);
        shrinkLogic.shrinkAnimation();
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

    /**
     * 邀请码点击
     */
    public void inviteClick(View view) {
        if (isVisible.get()) {
            isVisible.set(false);
        } else {
            isVisible.set(true);
        }
    }

    /**
     * 注册点击
     */
    public void registerClick(View view) {
        String pwd = binding.registerPwd.getText().toString().trim();
        if (!InputCheck.checkPwdRule(pwd)) {
            Utils.toast(view.getContext().getString(R.string.register_pwd_err));
            return;
        }
        imei = SPUtil.imeiSave(view.getContext());
        RegisterDTO dto = new RegisterDTO(phone, pwd, code, imei, RequestParams.ACKAPPKEY_NUM, DeviceInfoUtils.getIdentifier(MyApplication.getInstance()),
                "".equals(MyApplication.getInstance().getCLIENTID()) ? PushManager.getInstance().getClientid(binding.getRoot().getContext())
                        : MyApplication.getInstance().getCLIENTID());
        dto.setAppCode(DeviceInfoUtils.getAppMetaData(MyApplication.getInstance(), "UMENG_CHANNEL"));
        String invit = binding.registerInviter.getText().toString().trim();
        if (!TextUtils.isEmpty(invit)) {
            dto.setUserInviteCode(invit);
        }
        res_doregister(dto);
    }

    /**
     * 注册协议点击
     */
    public void agreeClick(View view) {
        Call<ProtocolMo> call = RDClient.getService(UserService.class).getRegisterProtocolContext();
        call.enqueue(new RequestCallBack<ProtocolMo>() {
            @Override
            public void onSuccess(Call<ProtocolMo> call, Response<ProtocolMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, binding.getRoot().getContext().getString(R.string.register_agree_two));
                intent.putExtra(BundleKeys.DATA, response.body().getDate());
                ActivityUtils.push(RDWebViewAct.class, intent);
            }
        });
    }

    private void res_doregister(final RegisterDTO dto) {
        Call<OauthTokenMo> call = RDClient.getService(UserService.class).doRegister(dto);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<OauthTokenMo>() {
            @Override
            public void onSuccess(Call<OauthTokenMo> call, Response<OauthTokenMo> response) {
                Utils.toast(ActivityUtils.peek().getString(R.string.register_success));
                if (isFromH5) {
                    UserLogic.saveToken(response.body());
                } else {
                    UserLogic.login(response.body());
                }
                ActivityUtils.peek().finish();
            }
        });
    }

    /**
     * 密码可见点击
     */
    public void eyesClick(View view) {
        if (enable) {
            binding.registerEyes.setImageDrawable(binding.getRoot().getResources().getDrawable(R.drawable.signup_bxs_pressed));
            binding.registerPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            binding.registerPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.registerEyes.setImageDrawable(binding.getRoot().getResources().getDrawable(R.drawable.signup_bxs_normal));
        }
        enable = !enable;
    }
}