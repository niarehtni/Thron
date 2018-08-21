package com.rd.zhongqipiaoetong.payment.china_pnr;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.account.model.AppPaymentMo;
import com.rd.zhongqipiaoetong.module.more.activity.RDWebViewAct;
import com.rd.zhongqipiaoetong.module.product.model.DoBondMo;
import com.rd.zhongqipiaoetong.network.NetworkUtil;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.ResponseParams;
import com.rd.zhongqipiaoetong.network.api.AccountService;
import com.rd.zhongqipiaoetong.network.api.BankService;
import com.rd.zhongqipiaoetong.network.api.PayService;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.network.exception.AppResultCode;
import com.rd.zhongqipiaoetong.payment.PayCallBack;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.PayRequestCallBack;
import com.rd.zhongqipiaoetong.payment.ToPaymentCheck;
import com.rd.zhongqipiaoetong.payment.ToPaymentMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.utils.UrlUtils;
import com.rd.zhongqipiaoetong.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/8/3 下午11:39
 * <p/>
 * Description:
 */
public class PNRController extends PayController {
    private void toWeb(Fragment fragment, Intent intent, int requestCode) {
        if (fragment == null) {
            ActivityUtils.push(RDWebViewAct.class, intent, requestCode);
        } else {
            intent.setClass(fragment.getActivity(), RDWebViewAct.class);
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void doRegister(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(AccountService.class).realnameIdentify(Utils.removeNull(paramMap));
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.payment_account_realname));
                    intent.putExtra(BundleKeys.URL, response.body().getUrl());
                    toWeb(fragment, intent, REQUEST_CODE_REGISTER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean toRegister(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (0 == mo.getRealNameStatus()) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.REALNAME, null);
            return false;
        }
        return true;
    }

    @Override
    public void doAuth(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(AccountService.class).doAuthorization(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.authorize));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                toWeb(fragment, intent, REQUEST_CODE_AUTH);
            }
        });
    }

    @Override
    public boolean toAuth(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (mo.getRealNameStatus() == 3) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.AUTO, mo.getAuthorizeType());
            return false;
        }
        return true;
    }

    public void doRechage(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(PayService.class).doRecharge(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.recharge_title));
                    intent.putExtra(BundleKeys.URL, response.body().getUrl());
                    toWeb(fragment, intent, REQUEST_CODE_RECHARGE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean toRechage(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (!toRegister(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (mo.getBankNum() < 1) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.BANKCARD, null);
            return false;
        }
        if (mo.getRealNameStatus() == 6) {
            toPaymentCheck.check(ToPaymentCheck.BANKCARD, null);
            return false;
        }
        return true;
    }

    public void doWithdraw(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(PayService.class).doCash(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                try {
                    Intent intent = new Intent();
                    intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.withdraw_title));
                    intent.putExtra(BundleKeys.URL, response.body().getUrl());
                    toWeb(fragment, intent, REQUEST_CODE_WITHDRAW);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean toWithdraw(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (!toRegister(mo, toPaymentCheck, isShow)) {
            return false;
        }

        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }

        if (mo.getBankNum() < 1) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.BANKCARD, null);
            return false;
        }

        if (mo.getRealNameStatus() == 6) {
            toPaymentCheck.check(ToPaymentCheck.BANKCARD, null);
            return false;
        }

        if (!mo.isHas_set_paypwd() && FeatureUtils.getNeedPayPwd()) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.SETPAYPWD, null);
            return false;
        }
        return true;
    }

    @Override
    public void doInvest(final Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Call<HttpResult> call = RDClient.getService(ProductService.class).doInvest(Utils.removeNull(paramMap));
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {

                if (response.body().getResCode() == AppResultCode.SUCCESS && response.body().getResData().toString().equals("{}")) {
                  /*处理仅用体验券投资的回调*/
                    Utils.toast(response.body().getResMsg());
                    if (payCallBack != null) {
                        payCallBack.callBack(true);
                    }
                } else {
                    String str = response.body().getBody();
                    try {
                        AppPaymentMo mo     = new Gson().fromJson((new JSONObject(str).getString(ResponseParams.RES_DATA)), AppPaymentMo.class);
                        Intent       intent = new Intent();
                        intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.investment_title));
                        intent.putExtra(BundleKeys.URL, mo.getUrl());
                        toWeb(fragment, intent, REQUEST_CODE_INVEST);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {
                super.onFailure(call, t);
                //有错误就重新获取下标信息 主要是刷新session值
                if (payCallBack != null) {
                    payCallBack.callBack(false);
                }
            }
        });
    }

    @Override
    public boolean toInvest(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (!toRegister(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!mo.isHas_set_paypwd() && FeatureUtils.getNeedPayPwd()) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.SETPAYPWD, null);
            return false;
        }
        return true;
    }

    @Override
    public void doFlow(final Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.investment_title));
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrl(UrlUtils.getDefaultAPI(FLOW)));
        intent.putExtra(BundleKeys.PARAMS, UrlUtils.getUrlParams(Utils.removeNull(paramMap)));
        toWeb(fragment, intent, REQUEST_CODE_INVEST);
    }

    @Override
    public boolean toFlow(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {

        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!mo.isHas_set_paypwd() && FeatureUtils.getNeedPayPwd()) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.SETPAYPWD, null);
            return false;
        }

        return true;
    }

    @Override
    public void doBond(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<DoBondMo> call = RDClient.getService(ProductService.class).bondInvest(String.valueOf(paramMap.get(RequestParams.ID)), String.valueOf(paramMap
                .get(RequestParams.INVESTCAPITAL)));
        call.enqueue(new RequestCallBack<DoBondMo>() {
            @Override
            public void onSuccess(Call<DoBondMo> call, Response<DoBondMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.investment_title));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                toWeb(fragment, intent, REQUEST_CODE_BOND);
            }
        });
    }

    @Override
    public boolean toBond(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!mo.isHas_set_paypwd() && FeatureUtils.getNeedPayPwd()) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.SETPAYPWD, null);
            return false;
        }
        return true;
    }

    @Override
    public void doBindCard(final Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Call<HttpResult> call = RDClient.getService(BankService.class).addBank(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                String str = response.body().getBody();
                try {
                    AppPaymentMo mo     = new Gson().fromJson((new JSONObject(str).getString(ResponseParams.RES_DATA)), AppPaymentMo.class);
                    Intent       intent = new Intent();
                    intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.bc_add_card));
                    intent.putExtra(BundleKeys.URL, mo.getUrl());
                    toWeb(fragment, intent, PayController.REQUEST_CODE_PAGEBINDCARD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {
                payCallBack.callBack(true);
                super.onFailure(call, t);
            }
        });
    }

    /**
     * 添加银行卡
     *
     * @param fragment
     * @param paramMap
     * @param payCallBack
     */
    @Override
    public void doPageBindCard(final Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Call<HttpResult> call = RDClient.getService(BankService.class).addBank(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                String str = response.body().getBody();
                try {
                    AppPaymentMo mo     = new Gson().fromJson((new JSONObject(str).getString(ResponseParams.RES_DATA)), AppPaymentMo.class);
                    Intent       intent = new Intent();
                    intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.bc_add_card));
                    intent.putExtra(BundleKeys.URL, mo.getUrl());
                    toWeb(fragment, intent, PayController.REQUEST_CODE_PAGEBINDCARD);
                    //                Utils.toast(ActivityUtils.peek().getString(R.string.submit_success));
                    ActivityUtils.peek().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HttpResult> call, Throwable t) {
                payCallBack.callBack(true);
                super.onFailure(call, t);
            }
        });
    }

    @Override
    public boolean toBindCard(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (!toRegister(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (mo.getBankNum() > 0) {
            if (isShow)
                toPaymentCheck.check(ToPaymentCheck.TOBANKLIST, null);
            return false;
        }
        return true;
    }

    @Override
    public boolean toAuto(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        if (!toRegister(mo, toPaymentCheck, isShow)) {
            return false;
        }
        if (!toAuth(mo, toPaymentCheck, isShow)) {
            return false;
        }
        return true;
    }

    @Override
    public void doAutoOn(Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.account_to_auto));
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrl(UrlUtils.getDefaultAPI(AUTO_ON)));
        intent.putExtra(BundleKeys.PARAMS, UrlUtils.getUrlParams(Utils.removeNull(paramMap)));
        toWeb(fragment, intent, REQUEST_CODE_AUTO_ON);
    }

    @Override
    public boolean toAutoOn(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        return false;
    }

    @Override
    public void doAutoOff(Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.account_to_auto));
        intent.putExtra(BundleKeys.URL, UrlUtils.getSignaWebUrl(UrlUtils.getDefaultAPI(AUTO_OFF)));
        intent.putExtra(BundleKeys.PARAMS, UrlUtils.getUrlParams(Utils.removeNull(paramMap)));
        toWeb(fragment, intent, REQUEST_CODE_AUTO_OFF);
    }

    @Override
    public boolean toAutoOff(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        return false;
    }

    @Override
    public void doAutoModify(Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).autoTender(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                if (payCallBack != null) {
                    payCallBack.callBack(true);
                    Utils.toast(R.string.auto_invest_modifysuccess);
                }
            }
        });
    }

    @Override
    public boolean toAutoModify(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        return false;
    }

    @Override
    public boolean resultCheck(int type, int requestCode, int resultCode, Intent data, PayCallBack payCallBack) {
        if (requestCode >= 0x101 && requestCode <= 0x112) {
            if (payCallBack != null) {
                payCallBack.callBack(resultCode == Activity.RESULT_OK);
            }
            return resultCode == Activity.RESULT_OK;
        }
        return false;
    }
}
