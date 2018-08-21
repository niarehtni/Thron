package com.rd.zhongqipiaoetong.module.account.viewmodel;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.account.activity.CreditorListAct;
import com.rd.zhongqipiaoetong.module.account.activity.FinancialRecordsAct;
import com.rd.zhongqipiaoetong.module.account.activity.HuanKuanAct;
import com.rd.zhongqipiaoetong.module.account.activity.InvestmentRecordAct;
import com.rd.zhongqipiaoetong.module.account.activity.JieKuanManageAct;
import com.rd.zhongqipiaoetong.module.account.activity.LoanManageAct;
import com.rd.zhongqipiaoetong.module.account.activity.MyAssetsAct;
import com.rd.zhongqipiaoetong.module.account.activity.MyCouponAct;
import com.rd.zhongqipiaoetong.module.account.activity.MyExpAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentRecordAct;
import com.rd.zhongqipiaoetong.module.account.activity.PersonInfoAct;
import com.rd.zhongqipiaoetong.module.account.activity.RechargeAct;
import com.rd.zhongqipiaoetong.module.account.activity.ScoreLogAct;
import com.rd.zhongqipiaoetong.module.account.activity.WithdrawAct;
import com.rd.zhongqipiaoetong.module.account.fragment.MyAccountFrag;
import com.rd.zhongqipiaoetong.module.account.model.AccountMo;
import com.rd.zhongqipiaoetong.module.account.model.PersonInfoMo;
import com.rd.zhongqipiaoetong.module.account.model.SummaryMo;
import com.rd.zhongqipiaoetong.module.more.activity.MsgManageAct;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.user.logic.UserLogic;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.Html5Util;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.UrlUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/2/26 下午1:51
 * <p/>
 * Description: 账户中心VM({@link MyAccountFrag})
 */
public class AccountVM {
    public ObservableField<AccountMo> account = new ObservableField<>();
    private Fragment        fragment;
    private AccountBottomVM bottomVM;
    private boolean isAddExp = false;
    private boolean isAddRepay = false;

    public AccountVM(Fragment fragment, AccountBottomVM bottomVM) {
        this.fragment = fragment;
        this.bottomVM = bottomVM;
        req_data();
    }

    public void req_data() {
        Call<AccountMo> call = RDClient.getService(AccountService.class).basic();
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<AccountMo>(true) {
            @Override
            public void onSuccess(Call<AccountMo> call, Response<AccountMo> response) {
                AccountMo mo = response.body();
                if(account.get() == null){
                    mo.setShow(true);
                }else{
                    mo.setShow(account.get().isShow());
                }
                if (null != mo.getUsername()) {
                    OauthTokenMo oauthTokenMo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
                    oauthTokenMo.setHideUserName(mo.getUsername());
                    SharedInfo.getInstance().setValue(OauthTokenMo.class, oauthTokenMo);
                } else {
                    mo.setUsername(SharedInfo.getInstance().getValue(OauthTokenMo.class).getHideUserName());
                }
                if (FeatureConfig.enableRepaymentFeature == 1 && !isAddRepay && response.body().isCanBorrow()) {
                    bottomVM.addRepay();
                    isAddRepay = true;
                }
                account.set(mo);
                req_securityInfo();
                req_getExpOpen();
            }
        });
    }

    /**
     * 网络请求
     */
    private void req_securityInfo() {
        Call<PersonInfoMo> call = RDClient.getService(AccountService.class).securityInfo();
        call.enqueue(new RequestCallBack<PersonInfoMo>() {
            @Override
            public void onSuccess(Call<PersonInfoMo> call, Response<PersonInfoMo> response) {
                SharedInfo.getInstance().setValue(PersonInfoMo.class, response.body());
            }
        });
    }

    /**
     * 获取体验金开关
     */
    private void req_getExpOpen() {
        Call<SummaryMo> call = RDClient.getService(AccountService.class).isExpOpen();
        call.enqueue(new RequestCallBack<SummaryMo>() {
            @Override
            public void onSuccess(Call<SummaryMo> call, Response<SummaryMo> response) {
                if (!isAddExp && response.body().isHasExpBorrow()) {
                    bottomVM.addExp();
                    isAddExp = true;
                }
            }
        });
    }

    /**
     * 资产统计点击事件
     */
    public void assestClick(View view) {

        Intent intent = new Intent(fragment.getActivity(), MyAssetsAct.class);
        intent.putExtra(BundleKeys.ACCOUNTMO, account.get());
        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_ASSESTS);
    }

    /**
     * 我的红包点击事件
     */
    public void myCouponClick(View view) {
        //        Intent intent = new Intent();
        //        intent.putExtra(BundleKeys.IS_SHOW, false); 
        ActivityUtils.push(MyCouponAct.class);
    }

    /**
     * 体验金点击事件
     */
    public void myExpClick(View view) {
        ActivityUtils.push(MyExpAct.class);
    }

    /**
     * 投资管理点击事件
     */
    public void investManageClick(View view) {
        ActivityUtils.push(InvestmentRecordAct.class);
    }

    /**
     * 回款计划点击事件
     */
    public void paymentClick(View view) {
        ActivityUtils.push(PaymentRecordAct.class);
    }

    /**
     * 我要转让点击事件
     */
    public void toTransferClick(View view) {
        ActivityUtils.push(CreditorListAct.class);
    }

    /**
     * 借款管理点击事件
     */
    public void loanManageClick(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), LoanManageAct.class));
    }

    /**
     * 充值点击事件
     * 已实名 to  RechargeAct
     * 未实名 to  PaymentAccountAct
     */
    public void toRechargeClick(View view) {
        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(account.get().getRealNameStatus());
        //        toPaymentMo.setAuthorize(account.get().isAuthorize());
        //        toPaymentMo.setAuthorizeType(account.get().getAuthorizeType());
        toPaymentMo.setBankNum(account.get().getBankNum());
        if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_RECHARGE, toPaymentMo, new ToPaymentCheck(fragment), true)) {
            return;
        }

        Intent intent = new Intent(fragment.getActivity(), RechargeAct.class);
        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_RECHARGE);
    }

    /**
     * 提现点击事件
     * 已实名 to  WithdrawAct
     * 未实名 to  PaymentAccountAct
     */
    public void toWithdrawClick(View view) {

        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(account.get().getRealNameStatus());
        //        toPaymentMo.setAuthorize(account.get().isAuthorize());
        //        toPaymentMo.setAuthorizeType(account.get().getAuthorizeType());
        toPaymentMo.setBankNum(account.get().getBankNum());
        toPaymentMo.setHas_set_paypwd(account.get().isHasSetPayPwd());
        if (!RDPayment.getInstance().getPayController().toPayment(PayController.TPYE_WITHDRAW, toPaymentMo, new ToPaymentCheck(fragment), true)) {
            return;
        }

        Intent intent = new Intent(fragment.getActivity(), WithdrawAct.class);
        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_WITHDRAW);
    }

    /**
     * 我的账户消息点击事件
     */
    public void myMsgManageClick(View view) {
        Intent intent = new Intent(fragment.getActivity(), MsgManageAct.class);
        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_MSGMANAGE);
    }

    /**
     * 上传头像点击事件
     */
    public void upLoadHeadImgClick(View view) {
        Intent intent = new Intent(fragment.getActivity(), PersonInfoAct.class);
        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_INFO);
    }

    /**
     * 自动投标点击事件
     */
    public void toAutoClick(View view) {

        ToPaymentMo toPaymentMo = new ToPaymentMo();
        toPaymentMo.setRealNameStatus(account.get().getRealNameStatus());
        toPaymentMo.setAuthorize(account.get().isAuthorize());
        toPaymentMo.setAuthorizeType(account.get().getAuthorizeType());

        if (!RDPayment.getInstance().getPayController().toAuto(toPaymentMo, new ToPaymentCheck(fragment), true)) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, fragment.getActivity().getString(R.string.account_to_auto));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.ISCLOSE,true);
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrlOther(UrlUtils.getDefaultAPI(Html5Util.API_AUTOINVEST)));
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    /**
     * 积分记录点击事件
     */
    public void scoreLogClick(View view) {
        ActivityUtils.push(ScoreLogAct.class);
    }

    /**
     * 生利宝点击事件
     *
     * @param view
     */
    public void slbClick(View view) {
        if ("0".equals(account.get().getRealNameStatus())) {
            UserLogic.accountToOpen(fragment);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, fragment.getActivity().getString(R.string.account_slb));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrl(UrlUtils.getDefaultAPI(Html5Util.SHENGLIBAO)));
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    /**
     * 充提记录
     *
     * @param view
     */
    public void toLogClick(View view) {
        ActivityUtils.push(FinancialRecordsAct.class);
    }

    public void mallClick(View view){
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, fragment.getActivity().getString(R.string.account_mall));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrlOther(Html5Util.SCOREMALL));
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    public void taskClick(View view){
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, fragment.getActivity().getString(R.string.account_task));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrlOther(Html5Util.MYTASK));
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    public void riskClick(View view){
        if(null == account.get()){
            return;
        }
        if ("0".equals(account.get().getRealNameStatus())) {
            UserLogic.accountToOpen(fragment);
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, fragment.getActivity().getString(R.string.account_risk));
        intent.putExtra(BundleKeys.NEED_GOBACK, true);
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrlOther(Html5Util.RISK));
        ActivityUtils.push(RDWebViewAct.class, intent);
    }

    public void onClick(View view, int type) {
        switch (type) {
            case R.string.account_asset_statistics:
                assestClick(view);
                break;
            case R.string.account_invest_manage:
                investManageClick(view);
                break;
            case R.string.account_payment_plan:
                paymentClick(view);
                break;
            case R.string.account_log:
                toLogClick(view);
                break;
            case R.string.account_to_transfer:
                toTransferClick(view);
                break;
            case R.string.account_my_red_packet:
                myCouponClick(view);
                break;
            case R.string.account_to_auto:
                toAutoClick(view);
                break;
            case R.string.account_score_log:
                scoreLogClick(view);
                break;
            case R.string.account_slb:
                slbClick(view);
                break;
            case R.string.account_exp:
                myExpClick(view);
                break;
            case R.string.account_mall:
                mallClick(view);
                break;
            case R.string.account_task:
                taskClick(view);
                break;
            case R.string.account_risk:
                riskClick(view);
                break;
            case R.string.account_loan_manage:
                loanClick(view);
                break;
            case R.string.account_repayment_manage:
                repayClick(view);
                break;
            default:
        }
    }

    /**
     * 借款管理
     */
    public void loanClick(View view) {
        ActivityUtils.push(JieKuanManageAct.class);
    }

    /**
     * 还款管理
     */
    public void repayClick(View view) {
        ActivityUtils.push(HuanKuanAct.class);
    }

    public void showMoney(View view) {
        if (account.get().isShow()) {
            account.get().setShow(false);
            ((ImageView) view).setImageResource(R.drawable.asset_icon_hide);
        } else {
            account.get().setShow(true);
            ((ImageView) view).setImageResource(R.drawable.asset_icon_show);
        }
    }
}