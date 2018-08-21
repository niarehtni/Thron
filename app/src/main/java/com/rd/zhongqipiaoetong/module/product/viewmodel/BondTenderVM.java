package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.DialogPwdBinding;
import com.rd.zhongqipiaoetong.databinding.ProductBondtenderActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.RechargeAct;
import com.rd.zhongqipiaoetong.module.product.activity.BondTenderAct;
import com.rd.zhongqipiaoetong.module.product.model.BondTenderMo;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DialogUtils;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.CustomDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * <p/>
 * Description: 债券投资VM ({@link BondTenderAct})
 */
public class BondTenderVM {
    public final ObservableField<BondTenderMo> model       = new ObservableField<>();
    public final ObservableField<String>       actPay      = new ObservableField<>();
    public final ObservableField<String>       discount    = new ObservableField<>();
    public final ObservableField<String>       earn        = new ObservableField<>();
    public final ObservableField<String>       tenderMoney = new ObservableField<>("0");
    private Dialog                      payDialog;
    private ProductBondtenderActBinding binding;
    private String                      id;
    private String                      paypwd;

    public BondTenderVM(String id, ProductBondtenderActBinding binding) {
        this.id = id;
        this.binding = binding;
    }

    /**
     * 投标
     */
    private void req_submit() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.ID, id);
        map.put(RequestParams.INVESTCAPITAL,model.get().getBondRes().getBondCapital());
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_BOND, map, new PayCallBack() {
            @Override
            public void callBack(boolean isSuccess) {
                if (!isSuccess) {
                    req_data(id);
                }
            }
        });
    }

    /**
     * 确认支付
     */
    public void submitClick(final View view) {
        Context context = view.getContext();
        double  money   = model.get().getBondRes().getBondCapital();

        if (money > model.get().getBalanceAvailable()) {
            new CustomDialog.Builder(context).setMessage(context.getString(R.string.investment_torecharge)).setNegativeButton(context.getString(R.string
                    .cancel), null).setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityUtils.push(RechargeAct.class);
                    dialog.dismiss();
                }
            }).create().show();
            return;
        }
        if (FeatureUtils.getNeedPayPwd()) {
            if (payDialog == null) {
                LayoutInflater         inflater         = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final DialogPwdBinding dialogPwdBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_pwd, null, false);
                dialogPwdBinding.pwdGridInfo.setVisibility(View.GONE);
                payDialog = DialogUtils.paypwdDialog(view.getContext(), dialogPwdBinding.getRoot(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (FeatureUtils.getNeedPayPwd()) {
                            paypwd = dialogPwdBinding.edPaypwd.getText().toString();

                            if (paypwd.equals("")) {
                                Utils.toast(view.getContext(), view.getContext().getString(R.string.investment_paypwd));
                                return;
                            }
                            paypwd = Base64.encodeToString(paypwd.getBytes(), Base64.DEFAULT);
                        }

                        payDialog.dismiss();
                    }
                });
            }
            payDialog.show();
        } else {
            req_submit();
        }
    }

    /**
     * 投标初始化
     *
     * @param id
     *         标的Id
     */
    public void req_data(String id) {
        Call<BondTenderMo> call = RDClient.getService(ProductService.class).bondInitialize(id);
        call.enqueue(new InitRequestCallBack<BondTenderMo>() {
            @Override
            public void onSuccess(Call<BondTenderMo> call, Response<BondTenderMo> response) {
                model.set(response.body());
                discount.set(String.valueOf(response.body().getBondRes().getBondCapital() * response.body().getBondRes().getDiscountRate() / 100));
                actPay.set(String.valueOf(response.body().getBondRes().getBondCapital() * (1 - response.body().getBondRes().getDiscountRate() / 100)));
            }
        });
    }
}