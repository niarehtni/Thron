package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.module.account.activity.AutoSetupAct;
import com.rd.zhongqipiaoetong.module.account.model.AutoTenderDetailMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/28 上午11:31
 * <p/>
 * Description:
 */
public class AutoDetailVM {
    public ObservableField<AutoTenderDetailMo> mo = new ObservableField<>();

    public AutoDetailVM() {
        initAuto();
    }

    /**
     * 自动投标初始化
     */
    public void initAuto() {
        Call<AutoTenderDetailMo> call = RDClient.getService(AccountService.class).autoTenderDetail();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<AutoTenderDetailMo>() {
            @Override
            public void onSuccess(Call<AutoTenderDetailMo> call, Response<AutoTenderDetailMo> response) {
                mo.set(response.body());
                mo.notifyChange();
            }
        });
    }

    /**
     * 修改自动投标onClick
     */
    public void onToSetupClick(View v) {
        ActivityUtils.push(AutoSetupAct.class);
    }
}
