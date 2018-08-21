package com.rd.zhongqipiaoetong.module.gesturelock.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/18 下午4:05
 * <p/>
 * Description: 手势密码登录管理类
 */
public class LockVM extends BaseObservable {
    private String nickname;

    public LockVM() {
        OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
        if (null != mo) {
            nickname = mo.getUsername();
        }
    }

    /**
     * 忘记手势密码
     */
    public void forgetPwd(View view) {
        UserLogic.signOutToLogin(view.getContext().getString(R.string.lock_forgot_prompt), true);
    }

    /**
     * 其他用户登录
     */
    public void otherUserLogin(View view) {
        UserLogic.signOutToLogin(view.getContext().getString(R.string.lock_visitor_prompt), false);
    }

    @Bindable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        notifyPropertyChanged(BR.nickname);
    }
}