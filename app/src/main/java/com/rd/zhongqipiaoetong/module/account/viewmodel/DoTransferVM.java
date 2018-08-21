package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.databinding.AccountDoTransferActBinding;
import com.rd.zhongqipiaoetong.module.account.model.DoAddBondMo;
import com.rd.zhongqipiaoetong.module.account.model.ToAddBondMo;
import com.rd.zhongqipiaoetong.module.account.model.TransferMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.module.user.model.ProtocolMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.Arith;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.Utils;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/3/21 下午8:24
 * <p/>
 * Description: 债权转让控制器
 */
public class DoTransferVM {
    public ObservableField<ToAddBondMo> mo = new ObservableField<>();
    private double maxRate;
    private double minRate;
    AccountDoTransferActBinding binding;
    public TransferMo transfermo;

    public DoTransferVM(final AccountDoTransferActBinding binding, final TransferMo transfermo, double maxRate, double minRate, double sellFee, String feeType) {
        this.binding = binding;
        this.transfermo = transfermo;
        this.maxRate = maxRate;
        this.minRate = minRate;

        ToAddBondMo toAddBondMo = new ToAddBondMo();
        toAddBondMo.setFeeType(feeType);
        toAddBondMo.setMoney(transfermo.getRemainCapital());
        toAddBondMo.setSellFee(sellFee);
        toAddBondMo.setMinRate(minRate);
        toAddBondMo.setDiscountApr(minRate);
        mo.set(toAddBondMo);

        binding.discountApr.setText(String.valueOf(minRate));
        binding.transferMoney.setText(String.valueOf(transfermo.getRemainCapital()));
        binding.transferMoney.setEnabled(false);
        binding.transferMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double etMoney = DisplayFormat.stringToDouble(s.toString());
                if (etMoney > transfermo.getRemainCapital()) {
                    etMoney = transfermo.getRemainCapital();
                    binding.transferMoney.setText(DisplayFormat.doubleFormat(etMoney));
                    Editable editable = binding.transferMoney.getText();
                    Selection.setSelection(editable, editable.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 加法事件
     */
    public void additionClick(View view) {
        String apr = binding.discountApr.getText().toString();
        if (apr.isEmpty()) {
            apr = "0";
        }
        double dapr = DisplayFormat.stringToDouble(apr);
        if (dapr >= maxRate) {
            setAprText(dapr);
        } else {
            setAprText(Arith.add(dapr, 0.1));
        }
    }

    /**
     * 减法事件
     */
    public void subtractionClick(View view) {
        String apr = binding.discountApr.getText().toString();
        if (apr.isEmpty()) {
            apr = "0";
        }
        double dapr = DisplayFormat.stringToDouble(apr);
        if (dapr == minRate) {
            setAprText(dapr);
        } else {
            setAprText(Arith.sub(dapr, 0.1));
        }
    }

    private void setAprText(double apr) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String        aprString     = decimalFormat.format(apr);
        binding.discountApr.setText(aprString);
        mo.get().setDiscountApr(apr);
        mo.notifyChange();
    }

    /**
     * 确认转让点击事件
     *
     * @param view
     */
    public void confirmTransfer(View view) {

        final DoAddBondMo doAddBonsMo = new DoAddBondMo();
        doAddBonsMo.setBondCapital(transfermo.getRemainCapital());
        doAddBonsMo.setDiscountRate(Double.parseDouble(binding.discountApr.getText().toString().trim()));
        doAddBonsMo.setBorrowInvestmentId(transfermo.getBorrowInvestmentId());
        doAddBonsMo.setAgree(true);
        UserLogic.createDialog(R.string.transfer_sumit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doAddBond(doAddBonsMo);
            }
        });
    }

    private void doAddBond(DoAddBondMo mo) {
        Call<DoAddBondMo> call = RDClient.getService(ProductService.class).doAddBond(mo);
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<DoAddBondMo>() {
            @Override
            public void onSuccess(Call<DoAddBondMo> call, Response<DoAddBondMo> response) {
                ActivityUtils.pop(ActivityUtils.peek(), BundleKeys.RESULT_CODE_DOTRANSFER, new Intent());
                Utils.toast(binding.getRoot().getContext().getString(R.string.transfer_money_success));
            }
        });
    }

    /**
     * 协议点击事件
     *
     * @param view
     */
    public void protrcolClick(View view) {
        Call<ProtocolMo> call = RDClient.getService(ProductService.class).getBondSellProtocolTwo(transfermo.getBorrowInvestmentId() + "",2);
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
}