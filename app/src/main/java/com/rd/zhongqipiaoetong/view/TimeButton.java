package com.rd.zhongqipiaoetong.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;

import com.rd.zhongqipiaoetong.MyApplication;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 19:27
 * <p/>
 * Description: 获取验证码倒计时按钮
 * PS: 由于发现timer每次cancel()之后不能重新schedule方法,所以计时完毕只恐timer.每次开始计时的时候重新设置timer,没想到好办法出次下策.
 * PPS: 如果需要记住上次倒计时，则请注意把该类的onCreate()、onDestroy() 和 activity的onCreate()、onDestroy()同步处理
 */
public class TimeButton extends NoDoubleClickButton {

    private boolean isRun = false;
    /**
     * 初始化时间
     */
    private long   initTime   = 0;
    /**
     * 倒计时长，默认60秒
     */
    private long   length     = 60 * 1000;
    /**
     * enable时显示的内容
     */
    private String textBefore = "获取验证码";
    /**
     * disable时显示的内容
     */
    private String textAfter  = "秒";
    /**
     * TimeButton的点击监听
     */
    private OnClickListener mOnclickListener;
    private Timer           timer;
    private TimerTask       timerTask;
    /**
     * 倒计时时间
     */
    private long            time;
    /**
     * onDestroy()时，倒计时剩余时间
     */
    private String          timeStr;
    /**
     * onDestroy()时，系统时间
     */
    private String          currentTimeStr;

    public TimeButton(Context context) {
        super(context);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            long currentTime = System.currentTimeMillis();
            long reMainTime = time - currentTime + initTime;
            TimeButton.this.setText(reMainTime / 1000 + textAfter);
            if (reMainTime <= 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setClickable(true);
                TimeButton.this.setFocusable(true);
                TimeButton.this.setText(textBefore);
                clearTimer();
            }
        }
    };

    /**
     * 初始化定时器
     */
    private void initTimer(long length) {
        initTime = System.currentTimeMillis();
        time = length;
        timer = new Timer();
        isRun = true;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
        this.setText(time / 1000 + textAfter);
        this.setEnabled(false);
        this.setClickable(false);
        this.setFocusable(false);
        timer.schedule(timerTask, 0, 1000);
    }

    /**
     * 清除计时器
     */
    public void clearTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        time = 0;
        isRun = false;
    }

    public void runTime() {
        initTimer(length);
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Activity activity) {
        timeStr = activity.getClass().getSimpleName() + "_time";
        currentTimeStr = activity.getClass().getSimpleName() + "_ctime";
        // 这里表示没有上次未完成的计时
        if (MyApplication.map == null || !MyApplication.map.containsKey(timeStr) || !MyApplication.map.containsKey(currentTimeStr))
            return;
        long time = System.currentTimeMillis() - MyApplication.map.get(currentTimeStr) - MyApplication.map.get(timeStr);
        MyApplication.map.remove(timeStr);
        MyApplication.map.remove(currentTimeStr);
        Log.d("TimeButton", "time:" + time);

        if (time > 0) {
            // TODO
        } else {
            initTimer(Math.abs(time));
        }
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (MyApplication.map == null)
            MyApplication.map = new HashMap<>();
        MyApplication.map.put(timeStr, time);
        MyApplication.map.put(currentTimeStr, System.currentTimeMillis());
        clearTimer();
    }

    /**
     * 设置点击之前的文本
     *
     * @param textBefore
     *         点击之前的文本
     *
     * @return TimeButton
     */
    public TimeButton setTextBefore(String textBefore) {
        this.textBefore = textBefore;
        return this;
    }

    /**
     * 设置计时时显示的文本
     *
     * @param textAfter
     *         计时时显示的文本
     *
     * @return TimeButton
     */
    public TimeButton setTextAfter(String textAfter) {
        this.textAfter = textAfter;
        return this;
    }

    /**
     * 设置倒计时时长
     *
     * @param length
     *         时间 默认毫秒
     *
     * @return TimeButton
     */
    public TimeButton setLength(long length) {
        this.length = length;
        return this;
    }

    /**
     * 获得倒计时时间
     *
     * @return TimeButton
     */
    public long getTime() {
        return time / 1000;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }
}