package com.bc.caibiao.ui.login;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangkai
 * @Description: 倒计时线程
 * create at 2015/11/13 16:57
 */
public class CountDownThread extends TimerTask {
    private Handler handler;
    private int number = 60;
    private int type = 0;

    private Timer timer;
    private boolean isRunning;

    public CountDownThread(Handler handler, int number) {
        this.handler = handler;
        this.number = number;
    }

    @Override
    public void run() {
        if (number < 0) {
            return;
        }
        Message message = new Message();
        message.what = 1;
        message.arg1 = number;
        handler.sendMessage(message);
        number -= 1;
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
        this.cancel();
        isRunning = false;
    }

    public void startTimerTask() {
        if (isRunning) {
            cancelTimer();
        }
        timer = new Timer();
        timer.schedule(this, 0, 1000);
        isRunning = true;
    }
}
