package com.rd.zhongqipiaoetong.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.rd.zhongqipiaoetong.common.BaseParams;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 19:31
 * <p/>
 * Description: 配置广播接收者:
 * <receiver android:name=".SMSBroadcastReceiver">
 * <intent-filter android:priority="1000">
 * <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
 * </intent-filter>
 * </receiver>
 * <p/>
 * 注意:
 * <intent-filter android:priority="1000">表示:
 * 设置此广播接收者的级别为最高
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static MessageListener mMessageListener;

    public SMSBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
            String sender = smsMessage.getDisplayOriginatingAddress();
            String content = smsMessage.getMessageBody();
            // long date = smsMessage.getTimestampMillis();
            // Date timeDate = new Date(date);
            // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // String time = simpleDateFormat.format(timeDate);
            //
            // Logger.i(TAG, "短信来自: " + sender);
            // Logger.i(TAG, "短信内容: " + content);
            // Logger.i(TAG, "短信时间: " + time);

            mMessageListener.OnReceived(content);

            // 如果短信来自1069009558666,不再往下传递,一般此号码可以作为短信平台的号码。
            if (BaseParams.SMS_SENDER.equals(sender)) {
                // Logger.i(TAG, "已经转发");
                abortBroadcast();
            }
        }
    }

    /**
     * @author TinhoXu E-mail:xth@erongdu.com
     * @version 创建时间：2015年9月2日 下午5:37:47
     *          回调接口
     */
    public interface MessageListener {
        public void OnReceived(String message);
    }

    public void setOnReceivedMessageListener(MessageListener messageListener) {
        this.mMessageListener = messageListener;
    }
}