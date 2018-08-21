package com.rd.zhongqipiaoetong.module.account.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.rd.zhongqipiaoetong.R;
import com.rd.zhongqipiaoetong.network.RequestParams;
import com.rd.zhongqipiaoetong.payment.PayController;
import com.rd.zhongqipiaoetong.payment.RDPayment;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DisplayFormat;
import com.rd.zhongqipiaoetong.utils.FeatureUtils;
import com.rd.zhongqipiaoetong.view.CustomDialog;

import java.util.HashMap;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/23/16.
 */
public class BankCardMo implements Parcelable {
    /**
     * 银行名称
     */
    private String  bank;
    /**
     * 银行ID
     */
    private String  id;
    /**
     * 银行卡号
     */
    private String  bankNo;
    /**
     * 是否能解绑
     */
    private int canDisable;
    /**
     * 银行卡状态
     */
    private String bindingStatus;
    /**
     * 银行LOGO URL
     */
    private String  logoPicUrl;
    /**
     * 是否开通快捷支付（联动优势独有）
     */
    private String  isopenfastPayment;
    /**
     * 充值银行卡编号（联动优势独有）
     */
    private String  bankCode;
    /**
     * 授权类型(联动优势开通快捷充值需要用到)
     */
    private String authorizeType;


    //--------------------------华丽分割线----------------------------------

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getLogoPicUrl() {
        return logoPicUrl;
    }

    public void setLogoPicUrl(String logoPicUrl) {
        this.logoPicUrl = logoPicUrl;
    }

    public int getCanDisable() {
        return canDisable;
    }

    public void setCanDisable(int canDisable) {
        this.canDisable = canDisable;
    }

    public String getBindingStatus() {
        return bindingStatus;
    }

    public void setBindingStatus(String bindingStatus) {
        this.bindingStatus = bindingStatus;
    }

    public String getIsopenfastPayment() {
        return isopenfastPayment;
    }

    public void setIsopenfastPayment(String isopenfastPayment) {
        this.isopenfastPayment = isopenfastPayment;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAuthorizeType() {
        return authorizeType;
    }

    public void setAuthorizeType(String authorizeType) {
        this.authorizeType = authorizeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bank);
        dest.writeString(this.id);
        dest.writeString(this.bankNo);
        dest.writeInt(this.canDisable);
        dest.writeString(this.logoPicUrl);
    }

    public BankCardMo() {
    }

    protected BankCardMo(Parcel in) {
        this.bank = in.readString();
        this.id = in.readString();
        this.bankNo = in.readString();
        this.canDisable = in.readInt();
        this.logoPicUrl = in.readString();
    }

    public static final Parcelable.Creator<BankCardMo> CREATOR = new Parcelable.Creator<BankCardMo>() {
        @Override
        public BankCardMo createFromParcel(Parcel source) {
            return new BankCardMo(source);
        }

        @Override
        public BankCardMo[] newArray(int size) {
            return new BankCardMo[size];
        }
    };

    //这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
    public String getPickerViewText() {
        //这里还可以判断文字超长截断再提供显示
        return ActivityUtils.peek().getString(R.string.recharge_bank_title, bank, DisplayFormat.getShortBankNO(bankNo));
    }

    /**
     * 是否显示开通快捷支付
     */
    public boolean isShow(){
        return FeatureUtils.isUMPayment();
    }

    /**
     * 是否开通快捷支付
     */
    public boolean isFastPayment(){
        return "1".equals(isopenfastPayment);
    }

    /**
     * 开通快捷支付
     */
    public void toFast(View view){
        if(isFastPayment()){
            return;
        } else {
            Activity activity = ActivityUtils.peek();
            Dialog dialog = new CustomDialog.Builder(activity).setMessage(activity.getString(R.string.bc_notfast))
                    .setNegativeButton(activity.getString(R.string.cancel), null)
                    .setPositiveButton(activity.getString(R.string.authorize), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put(RequestParams.AUTHORIZE_TYPE, authorizeType);
                            map.put(RequestParams.AUTHORIZE_ONOFF, true);
                            RDPayment.getInstance().getPayController().doPayment(null, PayController.TPYE_AUTH, map, null);
                        }
                    }).create();
            dialog.show();

        }
    }
}
