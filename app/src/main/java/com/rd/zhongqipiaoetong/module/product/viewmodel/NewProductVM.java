package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.ProductNewProductDetailBinding;
import com.rd.zhongqipiaoetong.module.product.activity.ChoiceExpAct;
import com.rd.zhongqipiaoetong.module.product.activity.InvestListAct;
import com.rd.zhongqipiaoetong.module.product.activity.ProductInfoAct;
import com.rd.zhongqipiaoetong.module.product.activity.SecurityAct;
import com.rd.zhongqipiaoetong.module.product.model.ExpInfoMo;
import com.rd.zhongqipiaoetong.module.product.model.ExpProductMo;
import com.rd.zhongqipiaoetong.module.product.model.ProductInfoMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.CommonService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DialogUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.listener.PtrFrameListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 2017/4/20 下午3:20
 * <p/>
 * Description:
 */
public class NewProductVM {
    private ProductNewProductDetailBinding binding;
    private String                         id;
    public       ObservableField<ExpProductMo.ExpProduct> exproduct = new ObservableField<>();
    public       ObservableDouble                         expAmount = new ObservableDouble(0);
    public final ObservableField<PtrFrameListener>        listener  = new ObservableField<>();
    PtrFrameLayout ptrFrame;

    public NewProductVM(ProductNewProductDetailBinding binding, String id) {
        this.binding = binding;
        this.id = id;
        ptrFrame = binding.ptrAll;
        listener.set(new PtrFrameListener() {
            @Override
            public void ptrFrameInit(PtrClassicFrameLayout ptrFrame) {
                request(ptrFrame);
            }

            @Override
            public void ptrFrameRefresh() {

            }

            @Override
            public void ptrFrameRequest(PtrFrameLayout ptrFrame) {
                request(ptrFrame);
            }
        });
    }

    private void request(PtrFrameLayout ptrFrame) {
        Call<ExpProductMo> call = RDClient.getService(CommonService.class).expBorrowDetail(id);
        call.enqueue(new RequestCallBack<ExpProductMo>(ptrFrame, true) {
            @Override
            public void onSuccess(Call<ExpProductMo> call, Response<ExpProductMo> response) {
                exproduct.set(response.body().getExpBorrowDetail());
                binding.financingCountProgress.setScale((float) exproduct.get().getProgressPercentage());
            }
        });
    }

    /**
     * 了解项目
     */
    public void learnProjectClick(View view) {
        //        if (exproduct.get() == null) {
        //            return;
        //        }
        //
        //        Intent intent = new Intent();
        //        intent.putExtra(BundleKeys.UUID, exproduct.get().getId());
        //        intent.putExtra(BundleKeys.IS_BONDDETAIL, false);
        //        ActivityUtils.push(ProductInfoAct.class, intent);
        reqGetDetail();
    }

    /**
     * 计算器
     */
    private Dialog calculateDialog;

    public void calculatorClick(View view) {
        if (exproduct.get() == null) {
            return;
        }
        if (calculateDialog == null) {
            calculateDialog = DialogUtils.calculatorDialog(view.getContext(), exproduct.get().getRateYear() + "", exproduct.get().getTimeLimit() + "",
                    Integer.valueOf(exproduct.get().getRepayWay()), true, 0, 0);
        }

        calculateDialog.show();
    }

    /**
     * 投资记录
     */
    public void investRecordClick(View view) {
        if (exproduct.get() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.ID, String.valueOf(exproduct.get().getId()));
        ActivityUtils.push(InvestListAct.class, intent);
    }

    /**
     * 安全保障
     */
    public void onSecurityClick(View view) {
        ActivityUtils.push(SecurityAct.class);
    }

    //选中的体验券
    private String expIdString = "";

    /**
     * 选择体验券
     */
    public void expClick(View view) {
        if (exproduct.get() == null) {
            return;
        }
        if (exproduct.get().getTicketExpList().size() == 0) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_SHOW, true);
        intent.putExtra(BundleKeys.EXPLIST, exproduct.get().getTicketExpList());
        intent.putExtra(BundleKeys.EXPSTRING, expIdString);
        intent.putExtra(BundleKeys.MAXEXP, exproduct.get().getAmountInvestable());
        intent.putExtra(BundleKeys.ISINFINITY, exproduct.get().getAmountBorrow() <= 0);
        ActivityUtils.push(ChoiceExpAct.class, intent, BundleKeys.REQUEST_CODE_EXPLIST);
    }

    /**
     * 投资体验券
     */
    public void investClick(View view) {
        if (exproduct.get() == null) {
            return;
        }
        if (TextUtils.isEmpty(expIdString)) {
            Utils.toast(R.string.newproduct_hint);
            return;
        }
        ExpInfoMo expInfoMo = new ExpInfoMo();
        expInfoMo.setExpIds(expIdString);
        expInfoMo.setBorrowId(exproduct.get().getId());
        Call<String> call = RDClient.getService(CommonService.class).expBorrowInvest(expInfoMo);
        call.enqueue(new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                DialogUtils.showMsg(ActivityUtils.peek(), "投资成功");
                expIdString = "";
                expAmount.set(0);
                request(ptrFrame);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                super.onFailure(call, t);
                expIdString = "";
                expAmount.set(0);
                request(ptrFrame);
            }
        });
    }

    /**
     * 页面回调
     */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        //体验券
        if (requestCode == BundleKeys.REQUEST_CODE_EXPLIST && resultCode == Activity.RESULT_OK) {
            expIdString = data.getStringExtra(BundleKeys.EXPSTRING);
            String selectExp = data.getStringExtra(BundleKeys.EXPAMOUNT);
            if (selectExp != null && !"".equals(selectExp) && Double.valueOf(selectExp) > 0) {
                expAmount.set(Double.valueOf(selectExp));
            } else {
                expAmount.set(0);
            }
            //
            //            String money = Double.valueOf(expAmount.get()) + Double.valueOf(tenderMoney.get()) + "";
            //            earn.set(CommonMethod.calculate(financingDetailMo.getBorrowVO().getRateYear(), financingDetailMo.getBorrowVO().getTimeLimit(),
            // money, Integer.valueOf
            //                    (financingDetailMo.getBorrowVO().getRepayWay()), financingDetailMo.getBorrowVO().isDay()) + "");
            return true;
        }
        //加息券
        return false;
    }

    /** 获取了解项目下属分项 */
    private void reqGetDetail() {
        Call<ProductInfoMo> call = RDClient.getService(ProductService.class).getProductDetail(exproduct.get().getId() + "");
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
