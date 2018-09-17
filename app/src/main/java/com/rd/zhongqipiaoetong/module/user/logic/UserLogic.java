package com.rd.zhongqipiaoetong.module.user.logic;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rd.zhongqipiaoetong.MainAct;
import com.rd.zhongqipiaoetong.MyApplication;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.info.SharedInfo;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountAct;
import com.rd.zhongqipiaoetong.module.account.activity.PaymentAccountOCRAct;
import com.rd.zhongqipiaoetong.module.account.activity.PwdManageAct;
import com.rd.zhongqipiaoetong.module.account.model.TransferingMo;
import com.rd.zhongqipiaoetong.module.gesturelock.logic.LockLogic;
import com.rd.zhongqipiaoetong.module.user.model.OauthTokenMo;
import com.rd.zhongqipiaoetong.network.RDClient;
import com.rd.zhongqipiaoetong.network.RequestCallBack;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.network.api.ProductService;
import com.rd.zhongqipiaoetong.network.entity.HttpResult;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.UpDataListener;
import com.rd.zhongqipiaoetong.view.CustomDialog;
import com.rd.zhongqipiaoetong.view.lock.FingerPasswordBean;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/28 16:01
 * <p/>
 * Description: 用户逻辑处理类
 */
public class UserLogic {
    /**
     * 用户登录
     */
    public static void login(OauthTokenMo oauthTokenMo) {
        Activity activity = ActivityUtils.peek();
        // 写入登录信息到内存和SP中
        saveToken(oauthTokenMo);
        /** 登录逻辑处理 */
        Intent intent = new Intent();
        intent.setClass(activity, MainAct.class);
        intent.putExtra(BundleKeys.IS_SHOW, true);
        intent.putExtra(BundleKeys.IS_UPDATA, true);
        ActivityUtils.push(MainAct.class, intent);
        ActivityUtils.pop(activity);
    }

    /**
     * 用户登出(到主界面)(弹窗提示)
     */
    public static void signOutToMain() {
        final Activity activity = ActivityUtils.peek();
        createDialog(activity, activity.getString(R.string.person_info_sign_out_prompt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOutToMain(activity, true, false);
            }
        }, null);
    }

    /**
     * 用户登出(到主界面)
     */
    public static void signOutToMain(Activity activity, boolean isShow, boolean isReMove) {
        removeToken(isReMove);
        toMain(activity, isShow);
    }

    /**
     * 保存本地用户信息
     *
     * @param oauthTokenMo
     */
    public static void saveToken(OauthTokenMo oauthTokenMo) {
        SharedInfo.getInstance().setValue(OauthTokenMo.class, oauthTokenMo);
        MyApplication.getInstance().updataLand(true);
        LockLogic.getInstance().setFirstIn(false);
    }

    /**
     * 清除本地用户信息
     *
     * @param isReMove
     *         是否删除手势密码
     */
    private static void removeToken(boolean isReMove) {
        if (isReMove) {
            OauthTokenMo mo = SharedInfo.getInstance().getValue(OauthTokenMo.class);
            if (mo != null) {
                SharedInfo.getInstance().remove(FingerPasswordBean.class, mo.getUserId());
            }
        }
        SharedInfo.getInstance().remove(OauthTokenMo.class);
        MyApplication.getInstance().updataLand(false);
    }

    /**
     * 跳转至 Main
     *
     * @param activity
     * @param isShow
     *         是否显示个人中心
     */
    private static void toMain(Activity activity, boolean isShow) {
        Intent intent = new Intent();
        intent.putExtra(BundleKeys.IS_SHOW, isShow);
        ActivityUtils.push(MainAct.class, intent);
        if(activity instanceof MainAct){
            return;
        }
        ActivityUtils.pop(activity);
    }

    /**
     * 用户登出(到登录界面)
     */
    public static void signOutToLogin(String prompt, final boolean isReMove) {
        final Activity activity = ActivityUtils.peek();
        createDialog(activity, prompt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOutToMain(activity, true, isReMove);
            }
        }, null);
    }

    /**
     * 用户登出(手势密码5次错误)
     */
    public static void signOutPwdTime(String prompt, final boolean isReMove) {
        LockLogic.getInstance().resetErrInputCount();
        removeToken(isReMove);
        final Activity activity = ActivityUtils.peek();
        createDialog(activity, prompt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toMain(activity, true);
            }
        }, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                toMain(activity, false);
            }
        });
    }

    /**
     * 开户
     */
    public static void accountToOpen(final Fragment fragment) {
        createDialog(R.string.account_realname_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                if (fragment == null) {
                    if(FeatureConfig.enableCertOCRFeature == 1){
                        ActivityUtils.push(PaymentAccountOCRAct.class, intent, BundleKeys.REQUEST_CODE_OPEN);
                    }else{
                        ActivityUtils.push(PaymentAccountAct.class, intent, BundleKeys.REQUEST_CODE_OPEN);
                    }
                } else {
                    if(FeatureConfig.enableCertOCRFeature == 1){
                        intent.setClass(fragment.getActivity(), PaymentAccountOCRAct.class);
                        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_OPEN);
                    }else{
                        intent.setClass(fragment.getActivity(), PaymentAccountAct.class);
                        fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_OPEN);
                    }
                }
            }
        });
    }

    /**
     * 双乾授权
     *
     * @param aunthType
     */
    public static void accountToAuthor(final Fragment fragment, final String aunthType) {
        createDialog(R.string.account_authorize_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                res_authorization(fragment, aunthType);
            }
        });
    }

    public static void accountToAuthor(final String aunthType) {
        accountToAuthor(null, aunthType);
    }

    public static void transferRecall(final String id, final UpDataListener<Boolean> upDataListener) {
        createDialog(R.string.transfer_recall_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                res_stopBond(id, upDataListener);
            }
        });
    }

    public static void couponExchang(final String id, final UpDataListener<Boolean> upDataListener) {
        createDialog(R.string.coupon_exchange_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                res_exchang(id, upDataListener);
            }
        });
    }

    public static void bindCard(final Fragment fragment) {
        createDialog(R.string.account_bank_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RDPayment.getInstance().getPayController().doPayment(fragment, PayController.TPYE_BINDCARD, null, null);
            }
        });
    }

    public static void setPayPwd(final Fragment fragment) {
        createDialog(R.string.account_pswd_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (fragment != null) {
                    Intent intent = new Intent(fragment.getActivity(), PwdManageAct.class);
                    fragment.startActivityForResult(intent, BundleKeys.REQUEST_CODE_PWDMANAGE);
                } else {
                    ActivityUtils.push(PwdManageAct.class);
                }
            }
        });
    }

    public static void createDialog(int stringID, DialogInterface.OnClickListener positivelistener) {
        Activity activity = ActivityUtils.peek();
        createDialog(activity, activity.getString(stringID), positivelistener, null);
    }

    public static void createDialog(String msg, DialogInterface.OnClickListener positivelistener) {
        Activity activity = ActivityUtils.peek();
        createDialog(activity, msg, positivelistener, null);
    }

    /**
     * 创建dialog
     *
     * @param activity
     * @param msg
     *         提示信息
     * @param positivelistener
     *         确定事件
     */
    public static void createDialog(Activity activity, String msg, DialogInterface.OnClickListener positivelistener, DialogInterface.OnDismissListener
            dismissListener) {
        Dialog dialog = new CustomDialog.Builder(activity).setMessage(msg)
                .setNegativeButton(activity.getString(R.string.cancel), null)
                .setPositiveButton(activity.getString(R.string.confirm), positivelistener).create();
        dialog.setOnDismissListener(dismissListener);
        dialog.show();
    }

    /**
     * 授权
     *
     * @param type
     *         授权类型
     */
    private static void res_authorization(final Fragment fragment, String type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(RequestParams.AUTHORIZE_TYPE, type);
        map.put(RequestParams.AUTHORIZE_ONOFF, true);
        RDPayment.getInstance().getPayController().doPayment(fragment, PayController.TPYE_AUTH, map, null);
    }

    private static void res_stopBond(String id, final UpDataListener<Boolean> upDataListener) {
        Call<TransferingMo> call = RDClient.getService(ProductService.class).stopBond(id);
        call.enqueue(new RequestCallBack<TransferingMo>() {
            @Override
            public void onSuccess(Call<TransferingMo> call, Response<TransferingMo> response) {
                if (upDataListener == null) {//为空时 为转让详情
                    ActivityUtils.pop(ActivityUtils.peek(), BundleKeys.RESULT_CODE_TRANSFERDETAIL, new Intent());
                    return;
                }
                //不为空时  为转让列表
                upDataListener.updata(0, true);
            }
        });
    }

    private static void res_exchang(String id, final UpDataListener<Boolean> upDataListener) {
        Call<HttpResult> call = RDClient.getService(ProductService.class).redEnvelopeExchange(id);
        call.enqueue(new RequestCallBack<HttpResult>() {
            @Override
            public void onSuccess(Call<HttpResult> call, Response<HttpResult> response) {
                upDataListener.updata(0, true);
            }
        });
    }
}