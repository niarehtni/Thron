package com.rd.zhongqipiaoetong.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.rd.zhongqipiaoetong.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chenwei on 16/5/13.
 */
public class Countdown {
    private TextView timeTv;
    private View     view;
    private long     levaeTime;
    private String   agr0;
    private boolean  isDay;
    private Context  context;

    // 设置时间倒计时
    public Countdown(Context context, TextView timeTv, long levaeTime,
                     String agr0, Boolean isDay) {
        this.context = context;
        this.agr0 = agr0;
        this.timeTv = timeTv;
        this.levaeTime = levaeTime;
        this.isDay = isDay;
        this.view = null;
        setLavetime(levaeTime);
        if (!isTimerRun) {
            runTime();
        }
    }

    // 设置时间倒计时
    public Countdown(View view, TextView timeTv, long levaeTime, String agr0,
                     Boolean isDay) {
        this.agr0 = agr0;
        this.timeTv = timeTv;
        this.levaeTime = levaeTime;
        this.isDay = isDay;
        this.view = view;
        setLavetime(levaeTime);
        if (!isTimerRun) {
            runTime();
        }
    }

    Timer timer = null;
    private boolean isTimerRun = false;

    private void runTime() {
        timer = new Timer();
        isTimerRun = true;
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                if (levaeTime > 0) {
                    levaeTime--;
                    iniTime.sendEmptyMessage(0);
                } else {

                }

            }
        }, 1000, 1000);
    }

    Handler iniTime = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setLavetime(levaeTime);
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setLavetime(long mss) {
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / (60);
        long seconds = (mss % (60));
        if (isDay) {
            if (mss == 0) {
                timeTv.setBackground(context.getResources().getDrawable(R.drawable.rectangle_btn));
                timeTv.setText("立即投资");
                timeTv.setClickable(true);
            } else {
                timeTv.setClickable(false);
                timeTv.setBackgroundColor(context.getResources().getColor(
                        R.color.text_grey));
                timeTv.setText(agr0 + days + "天" + hours + "时" + minutes + "分"
                        + seconds + "秒" );
            }

        } else {
            if (mss == 0) {
                timeTv.setText("重新获取");
                // timeTv.setTextColor(0xFFFFFFFF);
                timeTv.setClickable(true);
                if (view != null)
                    view.setClickable(true);
            } else {
                // timeTv.setTextColor(0xFFFFFFFF);
                timeTv.setClickable(false);
                timeTv.setText(mss + agr0);
            }
        }
    }
}
