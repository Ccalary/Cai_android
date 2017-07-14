package com.bc.caibiao;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by chengyanfang on 2017/4/24.
 */

public class BaseFragmentActivity  extends FragmentActivity {

    protected Handler mHandler;

    public Handler mThreadHandler;

    protected boolean mIsFirstAnim = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initUI() {
    }

    protected void initTitle() {

    }

    protected void initData() {

    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        if (mIsFirstAnim) {
            inFromRightOutToLeft();
            mIsFirstAnim = false;
        } else {
            inFromLeftOutToRight();
        }
    }

    /**
     * 设置切换动画，从右边进入，左边退出
     */
    protected void inFromRightOutToLeft() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 设置切换动画，从右边进入，左边退出
     */
    private void inFromLeftOutToRight() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }


}
