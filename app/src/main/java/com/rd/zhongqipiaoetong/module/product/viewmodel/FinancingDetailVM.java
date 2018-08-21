package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.ProductFinancingDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.InvestListAct;
import com.rd.zhongqipiaoetong.module.product.activity.InvestmentAct;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.model.FinancingDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.ProductInfoMo;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DialogUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: YuLei
 * E-mail: yl3@erongdu.com
 * Date: 2016/3/1 15:41
 * <p/>
 * Description:理财项目详情({@link FinancingDetailAct})
 */
public class FinancingDetailVM {
    public ObservableField<FinancingDetailMo> detail = new ObservableField<>();
    private String                           id;
    private Dialog                           calculateDialog;
    private ProductFinancingDetailActBinding binding;
    public       ObservableField<Float>            scale    = new ObservableField<>();
    /**
     * 刷新控件回调
     */
    public final ObservableField<PtrFrameListener> listener = new ObservableField<>();

    public FinancingDetailVM(String id, ProductFinancingDetailActBinding binding) {
        this.binding = binding;
        this.id = id;
        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {

            }

            @Override
            public void ptrFrameRefresh() {

            }

            @Override
            public void ptrFrameRequest(PtrFrameLayout ptrFrame) {
                req_data(ptrFrame);
            }
        });
    }

    /**
     * 获取标详情数据
     */
    public void req_data(PtrFrameLayout ptrFrame) {
        Call<FinancingDetailMo> call = RDClient.getService(ProductService.class).investDetail(id);
        call.enqueue(new RequestCallBack<FinancingDetailMo>(ptrFrame, true) {
            @Override
            public void onSuccess(Call<FinancingDetailMo> call, Response<FinancingDetailMo> response) {
                detail.set(response.body());
                binding.financingCountProgress.setScale(Float.valueOf(DisplayFormat.doubleFormat(detail.get().getBorrowVO().getProgressPercentage())));
                binding.titleBar.appbar.setTitle(detail.get().getBorrowVO().getName());
            }
        });
    }

    /**
     * 了解项目
     */
    public void learnProjectClick(View view) {
        reqGetDetail();
    }

    /**
     * 投资记录
     */
    public void investRecordClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.ID, detail.get().getBorrowVO().getId() + "");
        ActivityUtils.push(InvestListAct.class, intent);
    }

    /**
     * 计算器
     */
    public void calculatorClick(View view) {
        if(detail.get() == null){
            return;
        }
        if (calculateDialog == null) {
            calculateDialog = DialogUtils.calculatorDialog(view.getContext(), Double.valueOf(detail.get().getBorrowVO().getRateYear()) + detail.get()
                    .getBorrowVO().getPlatformRateYear() + "", detail.get().getBorrowVO()
                            .getTimeLimit(),
                    detail.get().getBorrowVO().getRepayWay(), detail.get().getBorrowVO().isDay(), 0, 0);
        }

        calculateDialog.show();
    }

    /**
     * 立即投资
     */
    public void investClick(View view) {
        req_data(id, view.getContext());
    }

    /**
     * 投标初始化
     */
    private void req_data(String uuid, final Context context) {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<PersonInfoMo>() {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                ToPaymentMo toPaymentMo = new ToPaymentMo();
                toPaymentMo.setRealNameStatus(response.body().getRealNameStatus());
                toPaymentMo.setHas_set_paypwd(response.body().getHas_set_paypwd() == 1);
                //                toPaymentMo.setHasSetPayPwd(response.body().isHasSetPayPwd());
                //                toPaymentMo.setAuthorizeType(response.body().getAuthorizeType());
                //                toPaymentMo.setAuthorize(response.body().isAuthorized());

                if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_INVEST, toPaymentMo, new ToPaymentCheck(null), true)) {
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(BundleKeys.ID, id);
                intent.putExtra(BundleKeys.PRODUCTDETAIL, detail.get());
                ActivityUtils.push(InvestmentAct.class, intent);
            }
        });
    }

    /**
     * 浏览协议
     * @param view
     */
    public void protocolClick(View view) {
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBorrowProtocol(detail.get().getBorrowVO().getId() + "");
        call.enqueue(new RequestCallBack<ProtocolMo>() {
            @Override
            public void onSuccess(Call<ProtocolMo> call, Response<ProtocolMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.DATA, response.body().getProtocolContext());
                intent.putExtra(BundleKeys.TITLE, "协议");
                ActivityUtils.push(RDWebViewAct.class, intent);
            }
        });
    }

    /** 获取了解项目下属分项 */
    private void reqGetDetail() {
        Call<ProductInfoMo> call = RDClient.getService(ProductService.class).getProductDetail(detail.get().getBorrowVO().getId() + "");
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<ProductInfoMo>() {
            @Override
            public void onSuccess(Call<ProductInfoMo> call, Response<ProductInfoMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.UUID, id);
                intent.putExtra(BundleKeys.IS_BONDDETAIL, false);
                intent.putExtra(BundleKeys.PRODUCTINFO, response.body());
                ActivityUtils.push(ProductInfoAct.class, intent);
            }
        });
    }
}