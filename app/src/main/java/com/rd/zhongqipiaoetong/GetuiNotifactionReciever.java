package com.rd.zhongqipiaoetong;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rd.zhongqipiaoetong.common.BundleKeys;
import com.rd.zhongqipiaoetong.module.homepage.activity.SplashAct;


import java.util.List;

/**
 * Created by pzw on 2017/9/12.
 */
public class GetuiNotifactionReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Tag","Notifaction get");
        boolean isGetui = intent.getBooleanExtra(BundleKeys.GETUI,false);
        if (isGetui) {
            //处理点击事件
            if(getAppSatus(context) == 3){
                Intent intentStart = new Intent(context, SplashAct.class);
                intentStart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentStart);
            }else if(getAppSatus(context) == 2){
                Intent intentRestart = new Intent(context, MainAct.class);
                intentRestart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intentRestart);
            }
        }
    }

    public int getAppSatus(Context context) {

        ActivityManager                       am   = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals("com.rd.thron")) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals("com.rd.thron")) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }
    }
}
