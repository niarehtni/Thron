package com.rd.zhongqipiaoetong.network.exception;

import android.util.Log;

import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.module.user.activity.LoginAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.HtmlRegexpUtil;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 11:06
 * <p/>
 * Description: HTTP请求异常类
 */
public class ApiException extends RuntimeException {
    private int    code;
    private String msg;

    public ApiException(int resultCode, String msg) {
        this(msg);
        this.code = resultCode;
        this.msg = HtmlRegexpUtil.filterHtml(msg);
        Log.d("msg", msg);
        Log.d("resultCode", resultCode + "");
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = HtmlRegexpUtil.filterHtml(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void apiExceptionCode(int code) {
        switch (code) {
            case AppResultCode.TOKEN_MISS://丢失（缺失）的token
            case AppResultCode.EX_TOKEN_INVALID://丢失（缺失）的token
            case AppResultCode.TOKEN_LOSE_EFFICACY: //长期不在线,token失效
            case AppResultCode.ERR_USER_LOCKED_CAUSE_PAYPWD://用户被锁定，多次键入支付密码错误
            case AppResultCode.TOKEN_TIMEOUT://token过期
            case AppResultCode.TOKEN_INVALID://token不存在，无效的token
                UserLogic.signOutToMain(ActivityUtils.peek(), true, false);
                break;
            case AppResultCode.ERR_USER_LOCKED_CAUSE_PWD://用户被锁定，多次键入登录密码错误
                if(ActivityUtils.peek() instanceof LoginAct){
                    return;
                }
                UserLogic.signOutToMain(ActivityUtils.peek(), true, false);
                break;
            case AppResultCode.ERR_USER_FREEZE://用户权限锁定
                if(ActivityUtils.peek() instanceof MainAct){
                    UserLogic.signOutToMain(ActivityUtils.peek(), true, false);
                } else {
                    ActivityUtils.peek().finish();
                }

                break;
            default:
        }
    }
}