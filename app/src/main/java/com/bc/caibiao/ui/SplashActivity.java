package com.bc.caibiao.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.VersionInfo;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.utils.PackageUtil;
import com.bc.caibiao.utils.SP;
import com.google.gson.Gson;

/**
 * @author wangkai
 * @Description: 首页闪图
 * create at 2016/4/17 14:26
 */

public class SplashActivity extends Activity {
    private View view;
    private LinearLayout llSplash;
    private AlphaAnimation aaStartAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        aaStartAnimation = new AlphaAnimation(1.0f, 1.0f);
        aaStartAnimation.setDuration(2000);
        view.startAnimation(aaStartAnimation);
        aaStartAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                enterToMain();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        llSplash = (LinearLayout) findViewById(R.id.llSplash);
        llSplash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaStartAnimation.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void enterToMain() {
        if("1".equals(SP.getInstance().getString(SPTag.TAG_ISFIRST_COME))){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }else{
            startActivity(new Intent(SplashActivity.this, GuideAct.class));
        }
        finish();
    }
}
