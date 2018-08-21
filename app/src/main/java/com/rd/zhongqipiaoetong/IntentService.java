package com.rd.zhongqipiaoetong;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.rd.zhongqipiaoetong.common.BundleKeys;

import java.io.UnsupportedEncodingException;

/**
 * Created by pzw on 2017/9/12.
 * <p>
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class IntentService extends GTIntentService {
    NotificationManager manager;
    int num = 0;

    public IntentService() {
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String message = "";
        try {
            message = new String(msg.getPayload(), "UTF-8");
            Log.e(TAG, "onReceiveMessageData -> " + "msg = " + message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(context, GetuiNotifactionReciever.class);
        resultIntent.putExtra(BundleKeys.GETUI, true);
        PendingIntent              resultPendingIntent = PendingIntent.getBroadcast(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder nb                  = new NotificationCompat.Builder(this);
        nb.setContentTitle(context.getString(R.string.app_name));
        nb.setContentText(message);
        nb.setAutoCancel(true);
        nb.setSmallIcon(R.drawable.app_icon);
        nb.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon));
        nb.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        nb.setWhen(System.currentTimeMillis());
        nb.setPriority(NotificationCompat.PRIORITY_MAX);
        nb.setContentIntent(resultPendingIntent);
        manager.notify(num++, nb.build());
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        MyApplication.getInstance().setCLIENTID(clientid);
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }
}
