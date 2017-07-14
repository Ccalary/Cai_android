package com.bc.caibiao.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bc.caibiao.ui.me.MessageActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/4/27.
 */

public class MyReceiver extends BroadcastReceiver{
    private static final String TAG = "JPush";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] 用户点击打开了通知  action"+intent.getAction());
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            Intent i = new Intent(context, MessageActivity.class);
            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            context.startActivity(i);

        }
    }
}
