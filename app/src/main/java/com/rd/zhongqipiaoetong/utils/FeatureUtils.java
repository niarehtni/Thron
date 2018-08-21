package com.rd.zhongqipiaoetong.utils;

import android.content.Context;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.BaseParams;
import com.rd.zhongqipiaoetong.common.FeatureConfig;
import com.rd.zhongqipiaoetong.common.ui.BaseActivity;
import com.rd.zhongqipiaoetong.module.account.model.AccountGridMo;
import com.rd.zhongqipiaoetong.module.account.model.TppConfineMo;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Hubert
 * E-mail: hbh@erongdu.com
 * Date: 16/8/10 下午3:56
 * <p/>
 * Description:
 */
public class FeatureUtils {
    /**
     * 获取红包tips
     *
     * @param context
     *
     * @return
     */
    public static String[] getCouponTips(Context context) {
        ArrayList<String> tips = new ArrayList<>();
        if (FeatureConfig.enableRedPacketFeature == 1) {
            tips.add(context.getString(R.string.coupon_redpacke));
        }
        if (FeatureConfig.enableExperienceFeature == 1) {
            tips.add(context.getString(R.string.coupon_experience));
        }
        if (FeatureConfig.enableUpRateFeature == 1) {
            tips.add(context.getString(R.string.coupon_uprate));
        }
        return listToArray(tips);
    }

    /**
     * ArrayList<String>  to  String[]
     *
     * @param list
     *
     * @return
     */
    private static String[] listToArray(ArrayList<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * 是否整数倍购买借款标
     */
    public static boolean getNeedInvestInterMultiples() {
        return FeatureConfig.enableInterMultiplesInvestFeature == 1;
    }

    /**
     * 是否需要交易密码配置
     */
    public static boolean getNeedPayPwd() {
        return FeatureConfig.enablePayPwdFeature == 1;
    }

    /**
     * 是否需要债转
     */
    public static boolean getNeedbond() {
        return FeatureConfig.enableBondFeature == 1;
    }

    /**
     * 是否开启红包功能
     */
    public static boolean getEnableRedPackeModule() {
        return FeatureConfig.enableRedPacketFeature == 1;
    }

    /**
     * 是否开启体验券功能
     */
    public static boolean getEnableExperienceModule() {
        return FeatureConfig.enableExperienceFeature == 1;
    }

    /**
     * 是否开启加息券功能
     */
    public static boolean getEnableUpRateModule() {
        return FeatureConfig.enableUpRateFeature == 1;
    }

    /**
     * 是否开启引导页
     */
    public static boolean getEnableGuideFeature() {
        return FeatureConfig.enableGuideFeature == 1;
    }

    /**
     * 是否开启自动投标
     */
    public static boolean getEnableAutoTenderFeature() {
        return FeatureConfig.enableAutoTenderFeature == 1;
    }

    /**
     * 是否开启积分记录
     */
    public static boolean getEnableScoreLogFeature() {
        return FeatureConfig.enableScoreLogFeature == 1;
    }

    /**
     * 是否开启生利宝
     */
    public static boolean getEnableChinapnrGeneratorFeature() {
        return FeatureConfig.enableChinapnrGeneratorFeature == 1;
    }

    /**
     * 是否开启VIP
     */
    public static boolean getEnableShowVipFeature() {
        return FeatureConfig.enableShowVipFeature == 1;
    }

    /**
     * 是否开启即息理财
     */
    public static boolean getEnableFlowInvestFeature() {
        return FeatureConfig.enableFlowInvestFeature == 1;
    }

    /**
     * 是否开启积分商城
     */
    public static boolean getEnableScoreMallFeature() {
        return FeatureConfig.enableCreditFunctionFeature == 1;
    }

    /**
     * 是否开启我的任务
     */
    public static boolean getEnableMyTaskFeature() {
        return FeatureConfig.enableTaskFunctionFeature == 1;
    }

    /**
     * 是否开启风险评估
     */
    public static boolean getEnableRiskFeature() {
        return FeatureConfig.enableRiskFunctionFeature == 1;
    }

    public static List<AccountGridMo> getAccountBottomList(Context context) {
        List<AccountGridMo> list = new ArrayList<>();
        //资产统计
        list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_asset_statistics), R.string.account_asset_statistics));
        //投资管理
        list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_invest_manage), R.string.account_invest_manage));
        //回款计划
        list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_payment_plan), R.string.account_payment_plan));
        //充提记录
        list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_log), R.string.account_log));
        //债权转让
        if (getNeedbond()) {
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_to_transfer), R.string.account_to_transfer));
        }
        //我的红包
        if (getEnableRedPackeModule() || getEnableUpRateModule() || getEnableExperienceModule()) {
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_my_red_packet), R.string.account_my_red_packet));
        }
        //自动投标
        if (getEnableAutoTenderFeature())
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_my_automatic), R.string.account_to_auto));
        //积分记录
        if (getEnableScoreLogFeature())
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_score_log), R.string.account_score_log));
        //生利宝
        if (getEnableChinapnrGeneratorFeature())
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_slb), R.string.account_slb));
        //积分商城
        if (getEnableScoreMallFeature())
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_mall), R.string.account_mall));
        //我的任务
        if (getEnableMyTaskFeature())
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_task), R.string.account_task));
        //风险评测
        if (getEnableRiskFeature())
            list.add(new AccountGridMo(context.getResources().getDrawable(R.drawable.account_risk), R.string.account_risk));
        return list;
    }

    /**
     * 判断三方是否是联动优势
     */
    public static boolean isUMPayment() {
        return BaseParams.paymentType == 3;
    }

    /**
     * 银行卡列表右上角添加按钮在不同三方下的逻辑
     */
    public static void AddBankCardButtonLogic(int type, TppConfineMo mo, BaseActivity activity) {
        if(mo == null){
            activity.removeRightText();
            return;
        }
        if (mo.isBindCards()) {
            activity.setRightText(activity.getString(R.string.bc_add), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_BINDCARD, null, null);
                }
            });
        } else {
            activity.removeRightText();
        }
    }
}
