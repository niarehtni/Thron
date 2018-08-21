package com.rd.zhongqipiaoetong.module.account.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.rd.zhongqipiaoetong.BR;
import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.common.Constant;
import com.rd.zhongqipiaoetong.utils.ContextHolder;
import com.rd.zhongqipiaoetong.utils.DateUtil;
import com.rd.zhongqipiaoetong.utils.StringFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Author: Chenming
 * E-mail: cm1@erongdu.com
 * Date: 16/11/15 下午2:26
 * <p/>
 * Description:资金明细
 */
public class CashRecordItem extends BaseObservable {
    /** 账户类别 */
    private String  accountType;
    /** 状态说明 */
    private String  accountTypeStr;
    /** 添加时间 */
    private String  createTime;
    /** 获取资金交易名称 */
    private String  funName;
    /** 交易金额 */
    private String  money;
    /** 交易金额表示符 */
    private String  moneyStr;
    /** 备注 */
    private String  remark;
    /** 是否显示备注 */
    private boolean visible;
    private String type;
    private String addTime;
    private String amount;

    public Spannable getAmount() {
        if(moneyStr == null)
            moneyStr = "";
        Spannable span      = new SpannableString(moneyStr + StringFormat.doubleFormat(amount) + "元");
        String    moneyTemp = moneyStr + StringFormat.doubleFormat(amount);
        Context   context   = ContextHolder.getContext();
        if (TextUtils.isEmpty(moneyStr)) {
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_grey)), 0, moneyTemp.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (Constant.SYMBOL_PLUS.equals(moneyStr)) {
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.app_color_principal)), 0, moneyTemp.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.app_color_secondary)), 0, moneyTemp.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddTime() {
        return DateUtil.formatter(DateUtil.Format.SECOND, addTime);
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountTypeStr() {
        return accountTypeStr;
    }

    public void setAccountTypeStr(String accountTypeStr) {
        this.accountTypeStr = accountTypeStr;
    }

    public String getCreateTime() {
        return DateUtil.formatter(DateUtil.Format.SECOND, createTime);
    }

    public String getHeadValue() {
        return DateUtil.formatter(DateUtil.Format.MONTH_CHINA, addTime);
    }

    public long getHeadId(int position) {
        if (position == -1) {
            return -1;
        }
        long headId = -1;
        try {
            headId = new SimpleDateFormat(DateUtil.Format.MONTH.getValue(), Locale.CHINESE).parse(getAddTime()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return headId;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFunName() {
        return funName;
    }

    public void setFunName(String funName) {
        this.funName = funName;
    }

    public Spannable getMoney() {
        Spannable span      = new SpannableString(moneyStr + StringFormat.doubleFormat(amount) + "元");
        String    moneyTemp = moneyStr + StringFormat.doubleFormat(amount);
        Context   context   = ContextHolder.getContext();
        if (TextUtils.isEmpty(moneyStr)) {
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.text_grey)), 0, moneyTemp.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (Constant.SYMBOL_PLUS.equals(moneyStr)) {
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.app_color_principal)), 0, moneyTemp.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.app_color_secondary)), 0, moneyTemp.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return span;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoneyStr() {
        return moneyStr;
    }

    public void setMoneyStr(String moneyStr) {
        this.moneyStr = moneyStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Bindable
    public boolean isVisible() {
        return visible && !TextUtils.isEmpty(remark);
    }

    public void setVisible() {
        visible = !visible;
        notifyPropertyChanged(BR.visible);
    }

    public void itemClick(View view) {
        setVisible();
    }
}