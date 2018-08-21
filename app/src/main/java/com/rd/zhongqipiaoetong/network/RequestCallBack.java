package com.rd.zhongqipiaoetong.network;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.network.exception.ApiException;
import com.rd.zhongqipiaoetong.utils.Utils;

import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: hebihe
 * E-mail: hbh@erongdu.com
 * Date: 2016/4/22 19:31
 * <p/>
 * Description: 网络请求回调封装类
 */
public abstract class RequestCallBack<T> implements Callback<T> {
    public abstract void onSuccess(Call<T> call, Response<T> response);

    private PtrFrameLayout ptrFrame;
    private boolean isShow = false;

    public RequestCallBack() {

    }

    public RequestCallBack(boolean isShow) {
        this.isShow = isShow;
    }

    public RequestCallBack(PtrFrameLayout ptrFrame) {
        this.ptrFrame = ptrFrame;
    }

    public RequestCallBack(PtrFrameLayout ptrFrame, boolean isShow) {
        this.ptrFrame = ptrFrame;
        this.isShow = isShow;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (ptrFrame != null && ptrFrame.isRefreshing()) {
            ptrFrame.refreshComplete();
        }
        if (response.isSuccessful()) {
            onSuccess(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (ptrFrame != null && ptrFrame.isRefreshing()) {
            ptrFrame.refreshComplete();
        }
        NetworkUtil.dismissCutscenes();
        if (t instanceof ApiException) {
            ApiException apiException = (ApiException) t;
            apiException.apiExceptionCode(apiException.getCode());
            Utils.toast(apiException.getMsg());
        } else if (isShow) {
            Utils.toast(R.string.msg_net_error);
        }
        t.printStackTrace();
    }
}