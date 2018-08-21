package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.databinding.AccountAutoSetupActBinding;
import com.rd.zhongqipiaoetong.module.account.model.AccountMo;
import com.rd.zhongqipiaoetong.module.account.model.AutoTenderDetailMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.EditTextFormat;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.ref.RefException;
import com.rd.zhongqipiaoetong.utils.ref.RefObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/7/28 下午4:36
 * <p/>
 * Description:
 */
public class AutoSetupVM {
    public ObservableField<String>             useMoney = new ObservableField<>();
    public ObservableField<AutoTenderDetailMo> mo       = new ObservableField<>();
    public AccountAutoSetupActBinding binding;
    public OptionsPickerView          timedownPickerView;
    public OptionsPickerView          timeupPickerView;
    public boolean                              isFirst      = false;
    public EditTextFormat.EditTextFormatWatcher moneyWatcher = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            mo.get().setMoney(str);
            mo.notifyChange();
        }
    };
    public EditTextFormat.EditTextFormatWatcher minWatcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            mo.get().setMin(str);
            mo.notifyChange();
        }
    };
    public EditTextFormat.EditTextFormatWatcher maxWatcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            mo.get().setMax(str);
            mo.notifyChange();
        }
    };
    public EditTextFormat.EditTextFormatWatcher aprWatcher   = new EditTextFormat.EditTextFormatWatcher() {
        @Override
        public void OnTextWatcher(String str) {
            if (str.equals("0")) {
                str = "";
            }
            mo.get().setAprDown(str);
            mo.notifyChange();
        }
    };

    public AutoSetupVM(AccountAutoSetupActBinding binding, boolean isFirst) {
        this.binding = binding;
        this.isFirst = isFirst;
        initview();
        initAuto();
        req_data();
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
            }
        });
    }

    /**
     * 个人账户信息
     */
    public void req_data() {
        Call<AccountMo> call = RDClient.getService(AccountService.class).basic();
        call.enqueue(new RequestCallBack<AccountMo>() {
            @Override
            public void onSuccess(Call<AccountMo> call, Response<AccountMo> response) {
                useMoney.set(response.body().getBalanceAvailable());
            }
        });
    }

    private void initview() {
        Context           context    = binding.getRoot().getContext();
        ArrayList<String> downPicker = new ArrayList<>();
        ArrayList<String> upPicker   = new ArrayList<>();
        String[]          months     = context.getResources().getStringArray(R.array.monthTips);
        downPicker.add(context.getString(R.string.month0));
        for (String month : months) {
            downPicker.add(month);
        }
        for (String month : months) {
            upPicker.add(month);
        }

        timedownPickerView = new OptionsPickerView(context);
        timedownPickerView.setPicker(downPicker);
        timedownPickerView.setCyclic(false);
        timedownPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mo.get().setTimelimitDown(options1);
                mo.notifyChange();
            }
        });

        timeupPickerView = new OptionsPickerView(context);
        timeupPickerView.setPicker(upPicker);
        timeupPickerView.setCyclic(false);
        timeupPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                mo.get().setTimelimitUp(options1 + 1);
                mo.notifyChange();
            }
        });
    }

    /**
     * 可用余额全投onClick
     */
    public void onMoneyType1Click(View v) {
        mo.get().setMoney("");
        mo.get().setMax("");
        mo.get().setMin("");
        changeTenderStyle(1);
    }

    /**
     * 固定每次投资金额onClick
     */
    public void onMoneyType2Click(View v) {
        mo.get().setMax("");
        mo.get().setMin("");
        if("0".equals(mo.get().getMoney())){
            mo.get().setMoney("");
        }
        changeTenderStyle(2);
    }

    /**
     * 投资金额区间onClick
     */
    public void onMoneyType3Click(View v) {
        if("0".equals(mo.get().getMax())){
            mo.get().setMax("");
        }
        if("0".equals(mo.get().getMin())){
            mo.get().setMin("");
        }
        mo.get().setMoney("");
        changeTenderStyle(3);
    }

    /**
     * 修改自动投资金额style
     */
    private void changeTenderStyle(int type) {
        binding.autoSetupMoneymin.clearFocus();
        binding.autoSetupMoneymax.clearFocus();
        binding.autoSetupMoney.clearFocus();
        mo.get().setTenderStyle(type);
        mo.get().setEnable(true);
        mo.notifyChange();
    }

    /**
     * 投资时间 不可限onClick
     */
    public void onTimeTypeClick(View v) {
        mo.get().setTimelimitDown(1);
        changeTime(0);
    }

    /**
     * 月范围必须在 onClick
     */
    public void onTimeType1Click(View view) {
        if (mo.get().getTimelimitUp() > 0)
            return;
        changeTime(1);
    }

    /**
     * 时间下限 onClick
     */
    public void onTimeDowdClick(View v) {
        timedownPickerView.show();
    }

    /**
     * 时间上限 onClick
     */
    public void onTimeUpClick(View v) {
        timeupPickerView.show();
    }

    /**
     * 修改自动投资时间参数
     */
    private void changeTime(int time) {
        mo.get().setTimelimitUp(time);
        mo.notifyChange();
    }

    /**
     * 年化收益 不可限 onClick
     */
    public void onAprTypeClick(View v) {
        binding.autoSetupApr.setText("");
        binding.autoSetupApr.clearFocus();
        changeApr("0");
    }

    /**
     * 不低于 onClick
     */
    public void onAprType1Click(View v) {
        changeApr("");
    }

    /**
     * 修改自动投资apr参数
     */
    private void changeApr(String apr) {
        mo.get().setAprDown(apr);
        mo.notifyChange();
    }

    /**
     * 等额本息 onClick
     */
    public void onRepaymentType1Click(View v) {
        changerepayment("1");
    }

    /**
     * 一次性到期还款 onClick
     */
    public void onRepaymentType2Click(View v) {
        changerepayment("2");
    }

    /**
     * 每月还息到期还本 onClick
     */
    public void onRepaymentType3Click(View v) {
        changerepayment("3");
    }

    /**
     * 修改自动投资repayment参数
     */
    private void changerepayment(String repayment) {

        if (mo.get().isStyleContains(repayment)) {
            mo.get().removeStyle(repayment);
        } else {
            mo.get().addStyle(repayment);
        }
        mo.notifyChange();
    }

    public void onCommitClick(View v) {
        AutoTenderDetailMo checkMo = mo.get();
        if (checkMo.getTenderStyle() == 2) {
            if (checkMo.getMoney().isEmpty()) {
                Utils.toast(R.string.auto_invest_toast1);
                return;
            }
            if (Integer.parseInt(checkMo.getMoney()) < 100) {
                Utils.toast(R.string.auto_invest_toast17);
                return;
            }
        } else if (checkMo.getTenderStyle() == 3) {
            if (checkMo.getMin().isEmpty()) {
                Utils.toast(R.string.auto_invest_toast4);
                return;
            }
            if (checkMo.getMax().isEmpty()) {
                Utils.toast(R.string.auto_invest_toast5);
                return;
            }
            if (Integer.parseInt(checkMo.getMin()) < 100) {
                Utils.toast(R.string.auto_invest_toast2);
                return;
            }
            if (Integer.parseInt(checkMo.getMax()) < Integer.parseInt(checkMo.getMin())) {
                Utils.toast(R.string.auto_invest_toast3);
                return;
            }
        }

        if (!checkMo.isTime()) {
            if (checkMo.getTimelimitDown() > checkMo.getTimelimitUp()) {
                Utils.toast(R.string.auto_invest_toast8);
                return;
            }
        }

        if (!checkMo.isApr()) {
            if (checkMo.getAprDown().isEmpty() && !checkMo.getAprDown().equals("0")) {
                Utils.toast(R.string.auto_invest_toast9);
                return;
            }
        } else {
            checkMo.setAprDown("0");
        }

        if (checkMo.getStyle().trim().isEmpty()) {
            Utils.toast(R.string.auto_invest_toast12);
            return;
        }
        req_commit(checkMo);
    }

    private void req_commit(AutoTenderDetailMo mo) {
        try {
            RefObject               refobj = RefObject.wrap(mo);
            HashMap<String, Object> param  = refobj.getField();
            PayCallBack payCallBack = new PayCallBack() {
                @Override
                public void callBack(boolean isSuccess) {
                    if (isSuccess) {
                        ActivityUtils.pop();
                    }
                }
            };
            if (isFirst) {
                RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_AUTO_ON, param, payCallBack);
            } else {
                RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_AUTO_MODIFY, param, payCallBack);
            }
        } catch (RefException e) {
            e.printStackTrace();
        }
    }
}
