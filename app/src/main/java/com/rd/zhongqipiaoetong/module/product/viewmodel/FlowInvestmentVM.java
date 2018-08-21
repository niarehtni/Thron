package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.databinding.DialogPwdBinding;
import com.rd.zhongqipiaoetong.databinding.ProductFlowinvestmentActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.RechargeAct;
import com.rd.zhongqipiaoetong.module.product.activity.InvestmentAct;
import com.rd.zhongqipiaoetong.module.product.model.FlowDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.FlowInitMo;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.SPUtil;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.CustomDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/16 11:43
 * <p/>
 * Description: 流转投资VM ({@link InvestmentAct})
 */
public class FlowInvestmentVM {
    public final ObservableField<FlowInitMo> model       = new ObservableField<>();
    public final ObservableField<String>     pay         = new ObservableField<>();
    public final ObservableField<String>     tenderMoney = new ObservableField<>("0");
    public final ObservableField<String>     earn        = new ObservableField<>();
    private Dialog           payDialog;
    private DialogPwdBinding dialogPwdBinding;
    private String           uuid;
    private String           paypwd;
    private String           dirpwd;
    private FlowDetailMo     financingMo;
    public final ObservableField<String> tCopies      = new ObservableField<>("0");
    public       ObservableBoolean       minuseEnable = new ObservableBoolean(false);
    public       ObservableBoolean       addEnable    = new ObservableBoolean(false);
    //VM对应的databinding
    private ProductFlowinvestmentActBinding binding;

    public FlowInvestmentVM(String uuid, FlowDetailMo financingMo, FlowInitMo investmentMo, ProductFlowinvestmentActBinding binding) {
        this.uuid = uuid;
        this.financingMo = financingMo;
        this.binding = binding;
        model.set(investmentMo);
        tCopies.set(model.get().showCount());
        binding.tenderEd.setText(tCopies.get());
        pay.set(String.valueOf(Integer.valueOf(tCopies.get()) * financingMo.getEachMoney()));
        minuseEnable.set(isMinuseEnable());
        addEnable.set(isAddEnable());
    }

    /**
     * 投资金额
     */
    public void investMoney(Editable s) {

        String count = s.toString().isEmpty() ? "0" : s.toString();

        //当大于剩余份数时，改为剩余份数
        if (!s.toString().isEmpty()) {
            if (Integer.valueOf(count) > model.get().getRemainCount()) {
                count = String.valueOf(model.get().getRemainCount());
                binding.tenderEd.setText(count);
                binding.tenderEd.setSelection(count.length());
            }
        }
        tenderMoney.set(String.valueOf(Integer.valueOf(count) * financingMo.getEachMoney()));
        // 按照利率计算金额
        earn.set(CommonMethod.calculate(String.valueOf(financingMo.getApr()), String.valueOf(financingMo.getTimeLimit()), tenderMoney.get(),
                financingMo.getRepayStyle(), financingMo.isDay()) + "");

        // 按照红包计算应付金额
        tCopies.set(count);
        pay.set(String.valueOf(tenderMoney.get()));
        minuseEnable.set(isMinuseEnable());
        addEnable.set(isAddEnable());
    }

    /**
     * 确认支付
     */
    public void submitClick(final View view) {
        double  money   = Double.valueOf(tenderMoney.get());
        Context context = view.getContext();
        double  count   = Double.valueOf(tCopies.get());

        //判断是否是最后一笔
        if (model.get().getMinCount() < model.get().getRemainCount()) {
                /*不是最后一笔*/

            //判断是否小于最小起投奖金额
            if (count < model.get().getMinCount()) {
                Utils.toast(view.getContext(), String.format(context.getString(R.string.investment_less_mincount), String.valueOf((int) model.get()
                        .getMinCount())));
                return;
            }

            //判断是否超出最大投标金额
            if (count > model.get().getMaxCount() && model.get().getMaxCount() != 0) {
                Utils.toast(view.getContext(), String.format(context.getString(R.string.investment_less_maxcount), String.valueOf((int) model.get()
                        .getMaxCount())));
                return;
            }
        } else {
                /*最后一笔*/

            if (count < model.get().getRemainCount()) {
                Utils.toast(view.getContext(), context.getString(R.string.investment_buyallcount));
                return;
            }
        }

        if (count * financingMo.getEachMoney() > model.get().getUseMoney()) {
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

        req_submit(view);
    }

    /**
     * 投标
     */
    private void req_submit(View view) {
        HashMap<String, Object> treeMap = new HashMap<>();
        treeMap.put(RequestParams.UUID, uuid);
        treeMap.put(RequestParams.ID, SharedInfo.getInstance().getValue(OauthTokenMo.class).getUserId());
        treeMap.put(RequestParams.PAYPASSWORD, paypwd);
        treeMap.put(RequestParams.DIRPWD, dirpwd);
        treeMap.put(RequestParams.SESSION_ID, model.get().getSessionId());
        treeMap.put(RequestParams.TCOPIES, tCopies.get());
        treeMap.put(RequestParams.ACKTOKEN, SPUtil.imeiSave(view.getContext()));
        treeMap.put(RequestParams.ACKAPPKEY, RequestParams.ACKAPPKEY_NUM);
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_FLOW, treeMap, new PayCallBack() {
            @Override
            public void callBack(boolean isSuccess) {
                if (!isSuccess)
                    req_data(uuid);
            }
        });
    }

    /**
     * 投标初始化
     */
    public void req_data(String uuid) {
        Call<FlowInitMo> call = RDClient.getService(ProductService.class).flowInvestInitialize(uuid);
        call.enqueue(new InitRequestCallBack<FlowInitMo>() {
            @Override
            public void onSuccess(Call<FlowInitMo> call, Response<FlowInitMo> response) {
                model.set(response.body());
                minuseEnable.set(isMinuseEnable());
                addEnable.set(isAddEnable());
            }
        });
    }


    /**
     * 增加份数
     */
    public void addCopie(View view) {
        tCopies.set(String.valueOf((int) (Integer.valueOf(tCopies.get()) + 1)));
        binding.tenderEd.setText(tCopies.get());
        minuseEnable.set(isMinuseEnable());
        addEnable.set(isAddEnable());
    }

    /**
     * 减少份数
     */
    public void deductCopie(View view) {
        tCopies.set(String.valueOf((int) (Integer.valueOf(tCopies.get()) - 1)));
        binding.tenderEd.setText(tCopies.get());
        minuseEnable.set(isMinuseEnable());
        addEnable.set(isAddEnable());
    }

    private boolean isMinuseEnable() {
        if (Integer.valueOf(tCopies.get()) <= model.get().getMinCount()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isAddEnable() {
        if (Integer.valueOf(tCopies.get()) >= model.get().getRemainCount()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isEtEnable() {

        if (!minuseEnable.get() && !addEnable.get()) {
            return false;
        }
        return true;
    }
}
