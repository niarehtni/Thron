package com.rd.zhongqipiaoetong.payment.moneymoremore;

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
import com.rd.zhongqipiaoetong.utils.Utils;
import com.rd.zhongqipiaoetong.utils.ref.RefException;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 16/8/4 上午1:00
 * <p/>
 * Description:
 */
public class MoneyMoreMoreController extends PayController {
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
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.payment_account_realname));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                toWeb(fragment, intent, REQUEST_CODE_REGISTER);
                //                try {
                //                    MoneyMoreMoreTrigger.controller(fragment, MoneyMoreMoreWrapper.REGISTER, response.body());
                //                } catch (RefException e) {
                //                    e.printStackTrace();
                //                } catch (IllegalAccessException e) {
                //                    e.printStackTrace();
                //                } catch (InvocationTargetException e) {
                //                    e.printStackTrace();
                //                } catch (InstantiationException e) {
                //                    e.printStackTrace();
                //                }
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

    @Override
    public void doRechage(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(PayService.class).doRecharge(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.recharge_title));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                toWeb(fragment, intent, REQUEST_CODE_RECHARGE);
                //                try {
                //                    MoneyMoreMoreTrigger.controller(fragment, MoneyMoreMoreWrapper.RECHARGE, response.body());
                //                } catch (RefException e) {
                //                    e.printStackTrace();
                //                } catch (IllegalAccessException e) {
                //                    e.printStackTrace();
                //                } catch (InvocationTargetException e) {
                //                    e.printStackTrace();
                //                } catch (InstantiationException e) {
                //                    e.printStackTrace();
                //                }
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
        return true;
    }

    @Override
    public void doWithdraw(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(PayService.class).doCash(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.withdraw_title));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                toWeb(fragment, intent, REQUEST_CODE_WITHDRAW);
                //                try {
                //                    MoneyMoreMoreTrigger.controller(fragment, MoneyMoreMoreWrapper.WITHDRAW, response.body());
                //                } catch (RefException e) {
                //                    e.printStackTrace();
                //                } catch (IllegalAccessException e) {
                //                    e.printStackTrace();
                //                } catch (InvocationTargetException e) {
                //                    e.printStackTrace();
                //                } catch (InstantiationException e) {
                //                    e.printStackTrace();
                //                }
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
                    //                    try {
                    //                        AppPaymentMo mo = new Gson().fromJson((new JSONObject(str).getString(ResponseParams.RES_DATA)), AppPaymentMo
                    // .class);
                    //                        MoneyMoreMoreTrigger.controller(fragment, MoneyMoreMoreWrapper.TRANSFER, mo);
                    //                    } catch (RefException e) {
                    //                        e.printStackTrace();
                    //                    } catch (IllegalAccessException e) {
                    //                        e.printStackTrace();
                    //                    } catch (InvocationTargetException e) {
                    //                        e.printStackTrace();
                    //                    } catch (InstantiationException e) {
                    //                        e.printStackTrace();
                    //                    } catch (JSONException e) {
                    //                        e.printStackTrace();
                    //                    }
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
    public void doFlow(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(ProductService.class).doFlowInvest(Utils.removeNull(paramMap));
        call.enqueue(new PayRequestCallBack(payCallBack) {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {

                try {
                    MoneyMoreMoreTrigger.controller(fragment, MoneyMoreMoreWrapper.TRANSFER, response.body());
                } catch (RefException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        });
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
        Call<DoBondMo> call = RDClient.getService(ProductService.class).bondInvest(String.valueOf(paramMap.get(RequestParams.ID)),String.valueOf(paramMap.get(RequestParams.INVESTCAPITAL)));
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
    public void doBindCard(final Fragment fragment, HashMap<String, Object> paramMap, PayCallBack payCallBack) {
        Call<AppPaymentMo> call = RDClient.getService(BankService.class).addBank();
        call.enqueue(new RequestCallBack<AppPaymentMo>() {
            @Override
            public void onSuccess(Call<AppPaymentMo> call, Response<AppPaymentMo> response) {
                Intent intent = new Intent();
                intent.putExtra(BundleKeys.TITLE, ActivityUtils.peek().getString(R.string.bc_add_card));
                intent.putExtra(BundleKeys.URL, response.body().getUrl());
                toWeb(fragment, intent, REQUEST_CODE_BINDCARD);
            }
        });
    }

    @Override
    public void doPageBindCard(Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
//        Call<HttpResult> call = RDClient.getService(BankService.class).addBank(Utils.removeNull(paramMap));
//        NetworkUtil.showCutscenes(call);
//        call.enqueue(new RequestCallBack<HttpResult>() {
//            @Override
//            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
//                Utils.toast(ActivityUtils.peek().getString(R.string.submit_success));
//                ActivityUtils.peek().finish();
//            }
//
//            @Override
//            public void onFailure(Call<HttpResult> call, Throwable t) {
//                payCallBack.callBack(true);
//                super.onFailure(call, t);
//            }
//        });
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
    public void doAutoOn(Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).autoTender(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                if (payCallBack != null) {
                    payCallBack.callBack(true);
                }
            }
        });
    }

    @Override
    public boolean toAutoOn(ToPaymentMo mo, ToPaymentCheck toPaymentCheck, boolean isShow) {
        return false;
    }

    @Override
    public void doAutoOff(Fragment fragment, HashMap<String, Object> paramMap, final PayCallBack payCallBack) {
        Call<HttpResult> call = RDClient.getService(AccountService.class).autoStatus(Utils.removeNull(paramMap));
        NetworkUtil.showCutscenes(call);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                if (payCallBack != null) {
                    payCallBack.callBack(true);
                }
            }
        });
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
        int moremoreType = -1;
        switch (type) {
            case PayController.TPYE_REGISTER:
                moremoreType = MoneyMoreMoreWrapper.REGISTER;
                break;
            case PayController.TPYE_AUTH:
                moremoreType = MoneyMoreMoreWrapper.AUTH;
                break;
            case PayController.TPYE_RECHARGE:
                moremoreType = MoneyMoreMoreWrapper.RECHARGE;
                break;
            case PayController.TPYE_WITHDRAW:
                moremoreType = MoneyMoreMoreWrapper.WITHDRAW;
                break;
            case PayController.TPYE_INVEST:
                moremoreType = MoneyMoreMoreWrapper.TRANSFER;
                break;
            case PayController.TPYE_BOND:
                moremoreType = MoneyMoreMoreWrapper.TRANSFER;
                break;
            case PayController.TPYE_BINDCARD:
                moremoreType = PayController.TPYE_BINDCARD;
                break;
            case PayController.TPYE_AUTO_ON:
                moremoreType = PayController.TPYE_AUTO_ON;
                break;
            case PayController.TPYE_AUTO_OFF:
                moremoreType = PayController.TPYE_AUTO_OFF;
                break;
            case PayController.TPYE_AUTO_MODIFY:
                moremoreType = PayController.TPYE_AUTO_MODIFY;
                break;
            default:
                break;
        }
        return MoneyMoreMoreTrigger.isSuccess(moremoreType, requestCode, resultCode, data, payCallBack);
    }
}
