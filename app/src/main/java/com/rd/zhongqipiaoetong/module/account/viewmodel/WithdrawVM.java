package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.databinding.AccountWithdrawActBinding;
import com.rd.zhongqipiaoetong.databinding.DialogPwdBinding;
import com.rd.zhongqipiaoetong.module.account.activity.WithdrawAct;
import com.rd.zhongqipiaoetong.module.account.model.BankCardMo;
import com.rd.zhongqipiaoetong.module.account.model.TipsMo;
import com.rd.zhongqipiaoetong.module.account.model.ToCashMo;
import com.rd.zhongqipiaoetong.module.account.model.WithdrawMo;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.PayService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DialogUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.ref.RefException;
import com.rd.zhongqipiaoetong.utils.ref.RefObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/17 14:36
 * <p/>
 * Description: 提现VM({@link WithdrawAct})
 */
public class WithdrawVM {
    // 提现按钮是否显示
    public final ObservableBoolean           enable = new ObservableBoolean(false);
    // 提现初始化信息
    public final ObservableField<WithdrawMo> item   = new ObservableField<>();
    // 初始化银行卡
    public final ObservableField<ToCashMo>   bankmo = new ObservableField<>();
    public ToCashMo toCashMo;
    private ArrayList<BankCardMo> bankCardMos = new ArrayList<>();
    /**
     * 支付密码dialog
     */
    private DialogPwdBinding          dialogPwdBinding;
    private Dialog                    pwdDialog;
    /**
     * 提现金额
     */
    private String                    money;
    private String                    id;
    private OptionsPickerView         optionsPickerView;
    private AccountWithdrawActBinding binding;
    private InputMethodManager        imm;
    public ObservableField<String> warmTips = new ObservableField<>("");

    public WithdrawVM(AccountWithdrawActBinding accountWithdrawActBinding) {
        toCashMo = new ToCashMo();
        imm = (InputMethodManager) accountWithdrawActBinding.getRoot().getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.binding = accountWithdrawActBinding;
        LayoutInflater inflater = (LayoutInflater) accountWithdrawActBinding.getRoot().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogPwdBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_pwd, null, false);
        dialogPwdBinding.pwdGridInfo.setVisibility(View.GONE);
        optionsPickerView = new OptionsPickerView(dialogPwdBinding.getRoot().getContext());
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                upBankCard(bankCardMos.get(options1));
            }
        });
        req_data();
        req_tips();
    }

    /**
     * 获取提现提示
     */
    private void req_tips(){
        Call<TipsMo> call = RDClient.getService(PayService.class).getWithdrawTips();
        call.enqueue(new RequestCallBack<TipsMo>() {
            @Override
            public void onSuccess(Call<TipsMo> call, Response<TipsMo> response) {
                warmTips.set(response.body().getTips());
            }
        });
    }

    /**
     * 提现金额限制
     */
    public void input(Editable s) {
        if (s != null && !TextUtils.isEmpty(s.toString())) {
            money = s.toString();

            //过滤无效 0
            String  regEx = "^0[0-9]";
            Pattern p     = Pattern.compile(regEx);
            Matcher m     = p.matcher(money);
            if (m.find()) {
                String value = DisplayFormat.stringToint(money) + "";
                binding.withdrawEt.setText(value);
                binding.withdrawEt.setSelection(value.length());
                return;
            }
            if (!enable.get()) {
                enable.set(true);
            }
            if (DisplayFormat.stringToDouble(money) > DisplayFormat.stringToDouble(item.get().getBalance())) {
                binding.withdrawEt.setText(item.get().getBalance());
                return;
            }
        } else {
            if (enable.get()) {
                enable.set(false);
            }
        }
    }

    /**
     * 隐藏输入法
     */
    public void hideKeyborad(View view) {
        if (imm != null) {
            if (imm.isActive(binding.withdrawEt)) {
                //如果开启
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 选择提现银行卡
     *
     * @param view
     */
    public void toBanKListClick(View view) {
        hideKeyborad(view);
        if (bankmo.get() != null) {
            optionsPickerView.show();
        }
    }

    /**
     * 提现
     */
    public void submit(View view) {
        try{
            Double.valueOf(money);
        }catch (Exception e){
            e.printStackTrace();
            Utils.toast("请输入正确的数字");
            return;
        }
        if (Double.valueOf(money) > Double.valueOf(item.get().getBalance())) {
            Utils.toast(binding.getRoot().getContext().getString(R.string.withdraw_money_hint));
            return;
        }
        if (Double.valueOf(money) < Double.valueOf(item.get().getMinInvest())) {
            Utils.toast(String.format(binding.getRoot().getContext().getString(R.string.withdraw_money_hint_error), item.get().getMinInvest()));
            return;
        }
        toCashMo = new ToCashMo();
        toCashMo.setCash(money);
        toCashMo.setBankId(id);
        if (FeatureConfig.enablePayPwdFeature == 1) {
            if (pwdDialog == null) {
                pwdDialog = DialogUtils.paypwdDialog(view.getContext(), dialogPwdBinding.getRoot(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String paypwd = dialogPwdBinding.edPaypwd.getText().toString();
                        if (paypwd.equals("")) {
                            Utils.toast(v.getContext().getString(R.string.investment_paypwd));
                            return;
                        }
                        paypwd = new String(Base64.encode(paypwd.getBytes(), Base64.DEFAULT));
                        toCashMo.setPaypwd(paypwd);
                        res_doCash();
                        pwdDialog.dismiss();
                    }
                });
            }
            dialogPwdBinding.edPaypwd.setText("");
            pwdDialog.show();
        } else {
            res_doCash();
        }
    }

    /**
     * 网络请求，提现初始化
     */
    public void req_data() {
        Call<WithdrawMo> call = RDClient.getService(PayService.class).toCash();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new InitRequestCallBack<WithdrawMo>() {
            @Override
            public void onSuccess(Call<WithdrawMo> call, Response<WithdrawMo> response) {
                WithdrawMo mo = response.body();
                //                toCashMo.setSessionId(mo.getSessionId());
                if (bankmo.get() == null) {
                    upBankCard(mo.getAccountBankList().get(0));
                }
                item.set(mo);
                bankCardMos = mo.getAccountBankList();
                optionsPickerView.setPicker(bankCardMos);
                optionsPickerView.setCyclic(false);
            }

            @Override
            public void onFailure(Call<WithdrawMo> call, Throwable t) {
                ActivityUtils.pop(ActivityUtils.peek());
                super.onFailure(call, t);
            }
        });
    }

    private void res_doCash() {
        try {
            RefObject               refobj = RefObject.wrap(toCashMo);
            HashMap<String, Object> param  = refobj.getField();
            RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_WITHDRAW, param, new PayCallBack() {
                @Override
                public void callBack(boolean isSuccess) {
                    req_data();
                }
            });
        } catch (RefException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改提现银行卡
     *
     * @param bankCardMo
     */
    public void upBankCard(BankCardMo bankCardMo) {
        toCashMo.setBankNO(bankCardMo.getBankNo());
        toCashMo.setBank(bankCardMo.getBank());
        toCashMo.setLogoPicUrl(bankCardMo.getLogoPicUrl());
        id = bankCardMo.getId();
        bankmo.set(toCashMo);
        bankmo.notifyChange();
    }
}