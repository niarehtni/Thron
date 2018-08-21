package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.databinding.ProductFlowDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountOCRAct;
import com.rd.zhongqipiaoetong.module.product.activity.FinancingDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.FlowInvestListAct;
import com.rd.zhongqipiaoetong.module.product.activity.FlowInvestmentAct;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.model.FlowDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.FlowInitMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DialogUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.view.CustomDialog;
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
public class FlowDetailVM {
    public ObservableField<FlowDetailMo> detail = new ObservableField<>();
    private String                      uuid;
    private Dialog                      calculateDialog;
    private ProductFlowDetailActBinding binding;
    public       ObservableField<Float>            scale    = new ObservableField<>();
    /**
     * 刷新控件回调
     */
    public final ObservableField<PtrFrameListener> listener = new ObservableField<>();

    public FlowDetailVM(String uuid, ProductFlowDetailActBinding binding) {
        this.binding = binding;
        this.uuid = uuid;
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
        Call<FlowDetailMo> call = RDClient.getService(ProductService.class).flowDetail(uuid);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<FlowDetailMo>(ptrFrame, true) {
            @Override
            public void onSuccess(Call<FlowDetailMo> call, Response<FlowDetailMo> response) {
                detail.set(response.body());
                binding.financingCountProgress.setScale(Float.valueOf(DisplayFormat.doubleFormat(detail.get().getScales() * 100)));
            }
        });
    }

    /**
     * 了解项目
     */
    public void learnProjectClick(View view) {
        if (detail.get() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_FLOW, true);
        intent.putExtra(BundleKeys.UUID, uuid);
        intent.putExtra(BundleKeys.IS_BONDDETAIL, false);
        ActivityUtils.push(ProductInfoAct.class, intent);
    }

    /**
     * 投资记录
     */
    public void investRecordClick(View view) {
        if (detail.get() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.ID, detail.get().getId());
        ActivityUtils.push(FlowInvestListAct.class, intent);
    }

    /**
     * 计算器
     */
    public void calculatorClick(View view) {
        if (detail.get() == null) {
            return;
        }
        if (calculateDialog == null) {
            calculateDialog = DialogUtils.calculatorDialog(view.getContext(), detail.get().getApr() + "", detail.get().getTimeLimit() + "", Integer.valueOf
                    (detail.get
                            ().getRepayStyle()), detail.get().isDay(), 0, 0);
        }

        calculateDialog.show();
    }

    /**
     * 立即投资
     */
    public void investClick(View view) {
        if (detail.get() == null) {
            return;
        }
        req_data(uuid, view.getContext());
    }

    /**
     * 投标初始化
     */
    private void req_data(String uuid, final Context context) {
        Call<FlowInitMo> call = RDClient.getService(ProductService.class).flowInvestInitialize(uuid);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<FlowInitMo>() {
            @Override
            public void onSuccess(Call<FlowInitMo> call, Response<FlowInitMo> response) {

                ToPaymentMo toPaymentMo = new ToPaymentMo();
                toPaymentMo.setHas_set_paypwd(response.body().isHasSetPayPwd());
                toPaymentMo.setAuthorizeType(response.body().getAuthorizeType());
                toPaymentMo.setAuthorize(response.body().isAuthorized());

                if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_FLOW, toPaymentMo, new ToPaymentCheck(null), true)) {
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(BundleKeys.UUID, detail.get().getUuid());
                intent.putExtra(BundleKeys.PRODUCTDETAIL, detail.get());
                intent.putExtra(BundleKeys.INVESTDETAIL, response.body());
                ActivityUtils.push(FlowInvestmentAct.class, intent);
            }

            @Override
            public void onFailure(Call<FlowInitMo> call, Throwable t) {

                if (t.getMessage().equals("请先通过实名认证")) {
                    new CustomDialog.Builder(context).setMessage(R.string.account_realname_no).setNegativeButton(R.string.cancel, null).setPositiveButton(R
                            .string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(FeatureConfig.enableCertOCRFeature == 1){
                                ActivityUtils.push(PaymentAccountOCRAct.class);
                            }else{
                                ActivityUtils.push(PaymentAccountAct.class);
                            }
                        }
                    }).create().show();
                } else {
                    //非实名错误使用父类方法打印数据
                    super.onFailure(call, t);
                }
            }
        });
    }
}
