package com.rd.zhongqipiaoetong.module.product.viewmodel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.DialogPwdBinding;
import com.rd.zhongqipiaoetong.databinding.ProductInvestmentActBinding;
import com.rd.zhongqipiaoetong.module.account.activity.RechargeAct;
import com.rd.zhongqipiaoetong.module.account.model.AccountMo;
import com.rd.zhongqipiaoetong.module.product.activity.ChoiceCouponAct;
import com.rd.zhongqipiaoetong.module.product.activity.ChoiceExpAct;
import com.rd.zhongqipiaoetong.module.product.activity.ChoiceUpAct;
import com.rd.zhongqipiaoetong.module.product.activity.InvestmentAct;
import com.rd.zhongqipiaoetong.module.product.model.FinancingDetailMo;
import com.rd.zhongqipiaoetong.module.product.model.InvestmentMo;
import com.rd.zhongqipiaoetong.network.InitRequestCallBack;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.view.CustomDialog;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

import static com.rd.zhongqipiaoetong.module.user.logic.UserLogic.createDialog;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/16 11:43
 * <p/>
 * Description: 投资VM ({@link InvestmentAct})
 */
public class InvestmentVM {
    public final ObservableField<InvestmentMo> model = new ObservableField<>();
    public final ObservableField<String> pay = new ObservableField<>();
    public final ObservableField<String> tenderMoney = new ObservableField<>("0");
    public final ObservableField<String> earn = new ObservableField<>();
    private Dialog payDialog;
    private DialogPwdBinding dialogPwdBinding;
    private String uuid;
    private String paypwd;
    private String dirpwd;
    private FinancingDetailMo financingDetailMo;
    private String redIds;
    //VM对应的databinding
    private ProductInvestmentActBinding binding;
    //选中的红包
    private String redIdString = "";
    public final ObservableField<String> redAmount = new ObservableField<>("0");
    //选中的加息券
    private String upIdString = "";
    public final ObservableField<String> upRate = new ObservableField<>("0");
    //选中的体验券
    private String expIdString = "";
    public final ObservableField<String> expAmount = new ObservableField<>("0");

    public InvestmentVM(String uuid, FinancingDetailMo financingDetailMo, ProductInvestmentActBinding binding) {
        this.uuid = uuid;
        this.financingDetailMo = financingDetailMo;
        this.binding = binding;
    }

    /**
     * 投资金额
     */
    public void investMoney(Editable s) {
        //每次改变输入金额后,重置红包,加息券,体验券
        redIdString = "";
        upIdString = "";
        expIdString = "";
        redAmount.set("0");
        upRate.set("0");
        expAmount.set("0");

        String money = (s.toString() == null || s.toString().equals("")) ? "0" : s.toString();

        //过滤无效 0
        String regEx = "^0[0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(money);
        if (m.find()) {
            String value = DisplayFormat.stringToint(money) + "";
            binding.tenderEd.setText(value);
            binding.tenderEd.setSelection(value.length());
            return;
        }
        //        String remain = String.valueOf(model.get().getRemainAmount());
        //如果输入的金额超出了剩余的可投金额,自动填充为剩余金额
        if (Double.valueOf(money) > model.get().getAmountInvestable()) {
            String intValue = String.valueOf(new Double(model.get().getAmountInvestable()).intValue());
            binding.tenderEd.setText(intValue);
            binding.tenderEd.setSelection(intValue.length());
            tenderMoney.set(String.valueOf(model.get().getAmountInvestable()));
        } else {
            tenderMoney.set(money);
        }
        // 按照利率计算金额
        earn.set(CommonMethod.calculate(Double.valueOf(financingDetailMo.getBorrowVO().getRateYear()) + financingDetailMo.getBorrowVO().getPlatformRateYear()
                + "", financingDetailMo.getBorrowVO().getTimeLimit(), tenderMoney.get(), Integer.valueOf
                (financingDetailMo.getBorrowVO().getRepayWay()), financingDetailMo.getBorrowVO().isDay()) + "");
        // 按照红包计算应付金额
        pay.set(String.valueOf(tenderMoney.get()));
    }

    /**
     * 选择红包
     */
    public void couponClick(View view) {
        if (tenderMoney.get().equals("0")) {
            Utils.toast(view.getContext().getString(R.string.investment_input_hint));
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_SHOW, true);
        intent.putExtra(BundleKeys.COUPONLIST, model.get().getRedPacketResArrays());
        intent.putExtra(BundleKeys.TENDERREDENVELOPERATE, model.get().getRedPacketInvestMaxRatioKey());
        intent.putExtra(BundleKeys.TENDERMONEY, tenderMoney.get());
        intent.putExtra(BundleKeys.REDSTRING, redIdString);
        ActivityUtils.push(ChoiceCouponAct.class, intent, BundleKeys.REQUEST_CODE_REDLIST);
    }

    /**
     * 选择加息券
     */
    public void upClick(View view) {
        if (tenderMoney.get().equals("0")) {
            Utils.toast(view.getContext().getString(R.string.investment_input_hint));
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_SHOW, true);
        intent.putExtra(BundleKeys.UPLIST, model.get().getTicketRateResArrays());
        intent.putExtra(BundleKeys.UPID, upIdString);
        ActivityUtils.push(ChoiceUpAct.class, intent, BundleKeys.REQUEST_CODE_UPLIST);
    }

    /**
     * 选择体验券
     */
    public void expClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_SHOW, true);
        intent.putExtra(BundleKeys.EXPLIST, model.get().getAvailableExperiences());
        intent.putExtra(BundleKeys.EXPSTRING, expIdString);
        intent.putExtra(BundleKeys.TENDERMONEY, tenderMoney.get());
        intent.putExtra(BundleKeys.MAXEXP, model.get().getCanUseMaxExperienceAmount());
        ActivityUtils.push(ChoiceExpAct.class, intent, BundleKeys.REQUEST_CODE_EXPLIST);
    }

    /**
     * 确认支付
     */
    public void submitClick(final View view) {
        double money = Double.valueOf(tenderMoney.get());
        final Context context = view.getContext();

        if (tenderMoney.get().equals("0") && !expAmount.get().equals("0")) {
            /*只用体验券投资*/

            if (Double.valueOf(expAmount.get()) < model.get().getInvestMin()) {
                Utils.toast(view.getContext(), String.format(context.getString(R.string.investment_less_minamount), DisplayFormat.doubleFormat(model.get()
                        .getInvestMin())));
                return;
            }
        } else {
            /*混合投资判断*/

            //判断是否是最后一笔
            if (model.get().getInvestMin() < model.get().getAmountInvestable()) {
                /*不是最后一笔*/

                //如果开启整数倍投资 判断是否是起投金额的整数倍
                if ((money % model.get().getInvestMin()) != 0 && FeatureUtils.getNeedInvestInterMultiples()) {
                    Utils.toast(view.getContext(), context.getString(R.string.investment_limit));
                    return;
                }

                //判断是否小于最小起投奖金额
                if (money < model.get().getInvestMin()) {
                    Utils.toast(view.getContext(), String.format(context.getString(R.string.investment_less_minamount), DisplayFormat.doubleFormat(model.get
                            ().getInvestMin())));
                    return;
                }

                //判断是否超出最大投标金额
                if (money > model.get().getInvestMax() && model.get().getInvestMax() != 0) {
                    Utils.toast(view.getContext(), String.format(context.getString(R.string.investment_pass_maxamount), DisplayFormat.doubleFormat(model.get
                            ().getInvestMax())));
                    return;
                }
            } else {
                /*最后一笔*/

                //实际投资金额（输入的金额+体验券之和）小于最后一笔
                if ((money + Double.valueOf(expAmount.get())) < model.get().getAmountInvestable()) {
                    Utils.toast(view.getContext(), context.getString(R.string.investment_buyall));
                    return;
                }
            }

            if (money > model.get().getUseableBalanceAvailable()) {
                Call<AccountMo> call = RDClient.getService(AccountService.class).basic();
                NetworkUtil.showCutscenes(call);
                call.enqueue(new RequestCallBack<AccountMo>(true) {
                    @Override
                    public void onSuccess(Call<AccountMo> call, Response<AccountMo> response) {
                        if (response.body().getBankNum() == 0) {
                            createDialog(R.string.account_bank_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_BINDCARD, null, null);
                                }
                            });
                        } else {
                            new CustomDialog.Builder(context).setMessage(context.getString(R.string.investment_torecharge)).setNegativeButton(context.getString(R.string
                                    .cancel), null).setPositiveButton(context.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityUtils.push(RechargeAct.class);
                                    dialog.dismiss();
                                }
                            }).create().show();
                        }
                    }
                });
                return;
            }
        }

        // 判断定向标是否输入
        if (model.get().getIsDirect() == 1) {
            dirpwd = binding.edDirpwd.getText().toString().trim();
            //            dirpwd = Base64.encodeToString(dirpwd.getBytes(), Base64.DEFAULT);
            if (dirpwd.equals("")) {
                Utils.toast(view.getContext(), view.getContext().getString(R.string.investment_dirpwd));
                return;
            }
        }
        req_submit(view);
        //        if (payDialog == null) {
        //            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //            dialogPwdBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_pwd, null, false);
        //            dialogPwdBinding.setVm(new PayPwdDialogVM(redAmount.get(), expAmount.get(), upRate.get(), tenderMoney.get()));
        //            dialogPwdBinding.pwdGridInfo.setVisibility(View.VISIBLE);
        //
        //            payDialog = DialogUtils.paypwdDialog(view.getContext(), dialogPwdBinding.getRoot(), new View.OnClickListener() {
        //                @Override
        //                public void onClick(View v) {
        //
        //                    if (FeatureUtils.getNeedPayPwd()) {
        //
        //                        paypwd = dialogPwdBinding.edPaypwd.getText().toString().trim();
        //
        //                        if (paypwd.equals("")) {
        //                            Utils.toast(view.getContext(), view.getContext().getString(R.string.investment_paypwd));
        //                            return;
        //                        }
        //
        //                        paypwd = Base64.encodeToString(paypwd.getBytes(), Base64.DEFAULT);
        //                    }
        //                    req_submit(view);
        //                    payDialog.dismiss();
        //                }
        //            });
        //        } else {
        //            dialogPwdBinding.getVm().refreshVm(redAmount.get(), expAmount.get(), upRate.get(), tenderMoney.get());
        //        }
        //        payDialog.show();
    }

    /**
     * 投标
     */
    private void req_submit(View view) {
        HashMap<String, Object> treeMap = new HashMap<>();
        treeMap.put(RequestParams.BORROWID, uuid);
        treeMap.put(RequestParams.CAPITAL, tenderMoney.get());
        treeMap.put(RequestParams.PASSWORDDIRECT, dirpwd);
        treeMap.put(RequestParams.REDPACKETIDS, redIdString);
        treeMap.put(RequestParams.UPIDS, upIdString);
        RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_INVEST, treeMap, new PayCallBack() {
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
        Call<InvestmentMo> call = RDClient.getService(ProductService.class).investInitialize(uuid);
        call.enqueue(new InitRequestCallBack<InvestmentMo>() {
            @Override
            public void onSuccess(Call<InvestmentMo> call, Response<InvestmentMo> response) {
                model.set(response.body());
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
            expAmount.set(data.getStringExtra(BundleKeys.EXPAMOUNT));

            String money = Double.valueOf(expAmount.get()) + Double.valueOf(tenderMoney.get()) + "";
            earn.set(CommonMethod.calculate(financingDetailMo.getBorrowVO().getRateYear(), financingDetailMo.getBorrowVO().getTimeLimit(), money, Integer
                    .valueOf
                            (financingDetailMo.getBorrowVO().getRepayWay()), financingDetailMo.getBorrowVO().isDay()) + "");
            return true;
        }
        //加息券
        if (requestCode == BundleKeys.REQUEST_CODE_UPLIST && resultCode == Activity.RESULT_OK) {
            upIdString = data.getStringExtra(BundleKeys.UPID);
            upRate.set(data.getStringExtra(BundleKeys.UPRATE));
            String apr = Double.valueOf(financingDetailMo.getBorrowVO().getRateYear()) + Double.valueOf(upRate.get()) + financingDetailMo.getBorrowVO().getPlatformRateYear() + "";
            earn.set(CommonMethod.calculate(apr, financingDetailMo.getBorrowVO().getTimeLimit(), tenderMoney.get(), Integer.valueOf
                    (financingDetailMo.getBorrowVO().getRepayWay()), financingDetailMo.getBorrowVO().isDay()) + "");
            return true;
        }
        //红包
        if (resultCode == Activity.RESULT_OK) {
            redIds = data.getStringExtra("ids");
            Double money = data.getDoubleExtra(BundleKeys.REDSELECTAMOUNT, 0);
            redIdString = data.getStringExtra(BundleKeys.REDSTRING);
            pay.set(String.valueOf(Double.valueOf(tenderMoney.get()) - money));
            redAmount.set(DisplayFormat.doubleFormat(money));
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 界面判断
    ///////////////////////////////////////////////////////////////////////////

    //判断edittext是否可编辑
    public boolean canEdit() {
        if (model.get().getAmountInvestable() > model.get().getAmountInvestable()) {
            binding.tenderEd.setEnabled(false);
            binding.tenderEd.setText(String.valueOf(model.get().getAmountInvestable()));
            tenderMoney.set(String.valueOf(model.get().getAmountInvestable()));
            pay.set(String.valueOf(model.get().getAmountInvestable()));
            earn.set(CommonMethod.calculate(financingDetailMo.getBorrowVO().getRateYear(), financingDetailMo.getBorrowVO().getTimeLimit(), String.valueOf
                    (model.get().getAmountInvestable()), Integer.valueOf
                    (financingDetailMo.getBorrowVO().getRepayWay()), financingDetailMo.getBorrowVO()
                    .getTimeType().equals("1")) + "");
            return false;
        }

        return true;
    }
}