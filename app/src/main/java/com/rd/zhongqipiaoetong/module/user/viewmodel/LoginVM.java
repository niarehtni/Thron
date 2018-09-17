package com.rd.zhongqipiaoetong.module.user.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.igexin.sdk.PushManager;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.animation.ShrinkLogic;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.databinding.UserLoginActBinding;
import com.rd.zhongqipiaoetong.module.user.activity.ForgetAct;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.module.user.activity.RegisterFirstAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.module.user.model.dto.LoginDTO;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.UserService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.InputCheck;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.statistics.DeviceInfoUtils;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/16 上午10:37
 * <p/>
 * Description: 登录控制器({@link LoginAct})
 */
public class LoginVM {
    private boolean isFromH5 = false;
    private UserLoginActBinding binding;
    /** 动画显示逻辑控制 */
    private ShrinkLogic         shrinkLogic;
    private Context mContext;
    /**
     * 监听EditText 变化
     */
    public  EditTextFormat.EditTextFormatWatcher watcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            enable.set(InputCheck.edListCheck(edList));
            enable.notifyChange();
        }
    };
    /**
     * 需监听的editText list
     */
    public  LinkedList<EditText>                 edList    = new LinkedList<>();
    public  ObservableField<Boolean>             enable    = new ObservableField<>(false);
    /**
     * 密码是否可见，默认不可见
     */
    private boolean                              isVisible = false;

    public LoginVM(UserLoginActBinding binding, Context mContext, boolean isFromH5) {
        this.binding = binding;
        this.isFromH5 = isFromH5;
        this.mContext = mContext;
        shrinkLogic = new ShrinkLogic(binding.loginCenter, binding.appLogo);
        shrinkLogic.shrinkAnimation();
    }

    /**
     * 登录点击事件
     */
    public void loginClick(final View view) {
        // 手机号/用户名/邮箱
        String id = binding.loginUsername.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.register_phone_notnull));
            return;
        }

        // 密码
        String pwd = binding.loginPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.register_pwd_notnull));
            return;
        }
        if (!InputCheck.checkPwdRule(pwd)) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.register_pwd_err));
            return;
        }

        // Base64加密后的密码
        String encodePwd = new String(Base64.encode(pwd.getBytes(), Base64.NO_WRAP));
        res_logo(id, encodePwd);
    }

    /**
     * 登录
     *
     * @param id
     * @param pwd
     */
    public void res_logo(String id, String pwd) {
        Call<OauthTokenMo> call = RDClient.getService(UserService.class).doLogin(new LoginDTO(id, pwd, DeviceInfoUtils.getIdentifier(MyApplication
                .getInstance()), "".equals(MyApplication.getInstance().getCLIENTID()) ? PushManager.getInstance().getClientid(binding.getRoot().getContext())
                : MyApplication.getInstance().getCLIENTID()));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<OauthTokenMo>() {
            @Override
            public void onSuccess(Call<OauthTokenMo> call, Response<OauthTokenMo> response) {
                if (isFromH5) {
                    UserLogic.saveToken(response.body());
                    ActivityUtils.pop();
                } else {
                    UserLogic.login(response.body());
                }
                //登录成功发送通知
                Intent loginSuccess = new Intent(Constant.LOGINSUCCESS);
                mContext.sendBroadcast(loginSuccess);
            }
        });
    }

    public void eyesClick(View view) {
        if (isVisible) {
            binding.loginEyes.setImageDrawable(binding.getRoot().getResources().getDrawable(R.drawable.signup_bxs_pressed));
            binding.loginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            binding.loginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.loginEyes.setImageDrawable(binding.getRoot().getResources().getDrawable(R.drawable.signup_bxs_normal));
        }
        isVisible = !isVisible;
    }

    /**
     * 忘记密码点击事件
     */
    public void forgetPwdClick(View view) {
        ActivityUtils.push(ForgetAct.class);
    }

    /**
     * 注册新用户
     */
    public void registerClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.H5, isFromH5);
        ActivityUtils.push(RegisterFirstAct.class, intent);
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
}