package com.rd.zhongqipiaoetong.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.CommonMethod;
import com.rd.zhongqipiaoetong.databinding.DialogPwdBinding;
import com.rd.zhongqipiaoetong.module.account.activity.ResetPayPwdAct;
import com.rd.zhongqipiaoetong.view.CustomDialog;

import java.text.DecimalFormat;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/28 9:52
 * <p/>
 * Description: 提示dialog工具类
 */
public class DialogUtils {
    /**
     * 提示用的dialog
     */
    public static void showMsg(Context context, String message) {
        new CustomDialog.Builder(context).setMessage(message).setPositiveButton(context.getString(R.string.confirm), null).create().show();
    }

    /**
     * 强制要求确认的dialog
     */
    public static void showMsgTough(Context context, String message, DialogInterface.OnClickListener listener) {
        new CustomDialog.Builder(context).setMessage(message)
                .setCancelable(false).setCanceledOnTouchOutside(false)
                .setPositiveButton(context.getString(R.string.confirm), listener).create().show();
    }

    /**
     * 强制要求确认的dialog，且确定后退出该Activity
     */
    public static void showMsgAndFinish(final Activity activity, String message) {
        new CustomDialog.Builder(activity).setMessage(message)
                .setCancelable(false).setCanceledOnTouchOutside(false)
                .setPositiveButton(activity.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                }).create().show();
    }

    /**
     * 非强制要求确认的dialog
     */
    public static void showMsgNotFinish(final Activity activity, String message, DialogInterface.OnClickListener listener) {
        new CustomDialog.Builder(activity).setMessage(message)
                .setPositiveButton(activity.getString(R.string.confirm), listener).create().show();
    }

    /**
     * 计算器dialog
     */
    public static Dialog calculatorDialog(final Context context, final String vo_apr, final String vo_time, final int vo_repayment_type, final boolean
            vo_isDay, final double amount, final double allInterest) {
        String tender = "0.0";

        LayoutInflater inflater        = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Dialog   calculateDialog = new Dialog(context, R.style.calculate_dialog_style);
        View           layout          = inflater.inflate(R.layout.dialog_calculate, null);
        calculateDialog.addContentView(layout, new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        ((TextView) layout.findViewById(R.id.calculate_tv_time)).setText(vo_time + "");
        layout.findViewById(R.id.msg_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDialog.dismiss();
            }
        });
        TextView       tv_timetype = (TextView) layout.findViewById(R.id.calculate_tv_timetype);
        final TextView tv_money    = (TextView) layout.findViewById(R.id.calculate_tv_money);
        final EditText ed_money    = (EditText) layout.findViewById(R.id.calculate_ed_money);
        //        tv_money.setText(BaseParams.calculate(vo_apr,vo_time,""));
        if (vo_isDay) {
            tv_timetype.setText(context.getResources().getString(R.string.day));
        } else {
            tv_timetype.setText(context.getResources().getString(R.string.month));
        }

        ed_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String etString = ed_money.getText().toString();

                if (etString.equals("") || etString == null) {
                    etString = "0.0";
                }

                if (vo_repayment_type == -1) {

                    tv_money.setText(DisplayFormat.doubleMoney(Double.valueOf(etString) * allInterest / amount));
                } else {
                    DecimalFormat df = new DecimalFormat("0.00");
                    tv_money.setText(df.format(CommonMethod.calculate(vo_apr, vo_time, etString, vo_repayment_type, vo_isDay)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Window window = calculateDialog.getWindow();
        window.setContentView(layout);
        window.setGravity(Gravity.CENTER);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        calculateDialog.setCanceledOnTouchOutside(false);

        return calculateDialog;
    }

    /**
     * 支付密码对话框
     *
     * @return
     */
    public static Dialog paypwdDialog(final Context context, View layout, View.OnClickListener sureClickListener) {
        final Dialog paydialog = new Dialog(context, R.style.calculate_dialog_style);
        paydialog.addContentView(layout, new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        final EditText ed_paypwd = (EditText) layout.findViewById(R.id.ed_paypwd);

        ((TextView) layout.findViewById(R.id.pwd_btn_cancle)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paydialog.dismiss();
            }
        });
        ((TextView) layout.findViewById(R.id.pwd_btn_sure)).setOnClickListener(sureClickListener);
        ((TextView) layout.findViewById(R.id.tv_forgetpwd)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.push(ResetPayPwdAct.class);
                paydialog.dismiss();
            }
        });

        Window window = paydialog.getWindow();
        window.setContentView(layout);
        window.setGravity(Gravity.CENTER);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        paydialog.setCanceledOnTouchOutside(true);
        paydialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ed_paypwd.setText("");
            }
        });
        return paydialog;
    }

    public static Dialog paypwdDialog(final Context context, View.OnClickListener sureClickListener) {
        LayoutInflater   inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DialogPwdBinding binding  = DataBindingUtil.inflate(inflater, R.layout.dialog_pwd, null, false);
        return paypwdDialog(context, binding.getRoot(), sureClickListener);
    }

    /**
     * 蒲公英更新Dialog
     */
    public static Dialog updateDialog(final Context context, final View.OnClickListener clickListener) {
        LayoutInflater inflater     = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Dialog   updateDialog = new Dialog(context, R.style.calculate_dialog_style);
        View           layout       = inflater.inflate(R.layout.pgy_update_dialog, null);
        updateDialog.addContentView(layout, new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        Button btn_ok     = (Button) (layout.findViewById(R.id.umeng_update_id_ok));
        Button btn_cancel = (Button) layout.findViewById(R.id.umeng_update_id_cancel);
        btn_ok.setOnClickListener(clickListener);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });
        Window window = updateDialog.getWindow();
        window.setContentView(layout);
        window.setGravity(Gravity.CENTER);
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        updateDialog.setCanceledOnTouchOutside(true);
        return updateDialog;
    }
}
