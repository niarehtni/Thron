package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.ProductBondDetailActBinding;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondDetailAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondRecordListAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondTenderAct;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.model.BondDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.BondMo;
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
 * <p/>
 * Description:债券项目详情({@link BondDetailAct})
 */
public class BondDetailVM {
    public       ObservableField<BondMo>           detail   = new ObservableField<>();
    /**
     * 刷新控件回调
     */
    public final ObservableField<PtrFrameListener> listener = new ObservableField<>();
    private String                      uuid;
    private String                      id;
    private Dialog                      calculateDialog;
    private ProductBondDetailActBinding binding;

    public BondDetailVM(String uuid, String id, ProductBondDetailActBinding binding) {
        this.uuid = uuid;
        this.id = id;
        this.binding = binding;

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
     * 获取债券详情
     */
    public void req_data(PtrFrameLayout ptrFrame) {
        Call<BondDetailMo> call = RDClient.getService(ProductService.class).bondDetail(uuid, id);
        call.enqueue(new RequestCallBack<BondDetailMo>(ptrFrame, true) {
            @Override
            public void onSuccess(Call<BondDetailMo> call, Response<BondDetailMo> response) {
                detail.set(response.body().getBond());
                if (detail.get().getProgressPercentage() == null)
                    detail.get().setProgressPercentage("0");
                binding.financingCountProgress.setScale(Float.valueOf(DisplayFormat.doubleFormat(detail.get().getSellingProgress())));
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
        intent.putExtra(BundleKeys.ID, detail.get().getId());
        intent.putExtra(BundleKeys.UUID, uuid);
        ActivityUtils.push(BondRecordListAct.class, intent);
    }

    /**
     * 受让协议
     */
    public void protocolClick(View view) {
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBondSellProtocol(detail.get().getId(),1);
        call.enqueue(new RequestCallBack<ProtocolMo>() {
            @Override
            public void onSuccess(Call<ProtocolMo> call, Response<ProtocolMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.DATA,response.body().getProtocolContext());
                intent.putExtra(BundleKeys.TITLE,"协议");
                ActivityUtils.push(RDWebViewAct.class,intent);
            }
        });
    }

    /**
     * 计算器
     */
    public void calculatorClick(View view) {
        if (calculateDialog == null) {
            calculateDialog = DialogUtils.calculatorDialog(view.getContext(), detail.get().getRateYear(), String.valueOf(detail.get().getRemainDays()), -1, true,
                    detail.get().getBondCapital(), detail.get().getInterest());
        }

        calculateDialog.show();
    }

    /**
     * 立即投资
     */
    public void investClick(View view) {
        req_data();
    }

    private void req_data(){
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
                intent.putExtra(BundleKeys.UUID, uuid);
                intent.putExtra(BundleKeys.ID, detail.get().getId());
                intent.putExtra(BundleKeys.BONDDETAIL, detail.get());

                ActivityUtils.push(BondTenderAct.class, intent);
            }
        });
    }

    /** 获取了解项目下属分项 */
    private void reqGetDetail() {
        Call<ProductInfoMo> call = RDClient.getService(ProductService.class).getProductDetail(detail.get().getBorrowId());
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<ProductInfoMo>() {
            @Override
            public void onSuccess(Call<ProductInfoMo> call, Response<ProductInfoMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.BORROWID, detail.get().getBorrowId());
                intent.putExtra(BundleKeys.IS_BONDDETAIL, true);
                intent.putExtra(BundleKeys.PRODUCTINFO, response.body());
                ActivityUtils.push(ProductInfoAct.class, intent);
            }
        });
    }

//    /**
//     * 投标初始化
//     */
//    private void req_data(final Context context) {
//        Call<BondTenderMo> call = RDClient.getService(ProductService.class).bondInitialize(id);
//        NetworkUtil.showCutscenes(call);
//        call.enqueue(new RequestCallBack<BondTenderMo>() {
//            @Override
//            public void onSuccess(Call<BondTenderMo> call, Response<BondTenderMo> response) {
//
//                ToPaymentMo toPaymentMo = new ToPaymentMo();
//                toPaymentMo.setHas_set_paypwd(response.body().isHasSetPayPwd());
//                //接口有问题,数据暂时写死
//                toPaymentMo.setAuthorizeType("1,2,3");
//                toPaymentMo.setAuthorize(response.body().isAuthorized());
//
//                if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_BOND, toPaymentMo, new ToPaymentCheck(null), true)) {
//                    return;
//                }
//
//                Intent intent = new Intent();
//                intent.putExtra(BundleKeys.UUID, uuid);
//                intent.putExtra(BundleKeys.ID, detail.get().getId());
//                intent.putExtra(BundleKeys.BONDDETAIL, detail.get());
//                intent.putExtra(BundleKeys.BONDTENDERDETAIL, response.body());
//                intent.putExtra(BundleKeys.BORROWID, detail.get().getBorrowId());
//
//                ActivityUtils.push(BondTenderAct.class, intent);
//            }
//
//            @Override
//            public void onFailure(Call<BondTenderMo> call, Throwable t) {
//
//                if (t.getMessage().equals("请先通过实名认证")) {
//                    new CustomDialog.Builder(context).setMessage(R.string.account_realname_no).setNegativeButton(R.string.cancel, null).setPositiveButton(R
//                            .string.confirm, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityUtils.push(PaymentAccountAct.class);
//                        }
//                    }).create().show();
//                } else {
//                    //非实名错误使用父类方法打印数据
//                    super.onFailure(call, t);
//                }
//            }
//        });
//    }
}