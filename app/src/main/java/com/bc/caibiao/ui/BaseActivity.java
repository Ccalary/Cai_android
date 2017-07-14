package com.bc.caibiao.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.base.URLConfig;
import com.bc.caibiao.model.HomePageModel.PublishTaskResultModel;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.ui.qiming.PleaseManNameChildActivity;
import com.bc.caibiao.ui.qiming.SimpleOutLinkAct;
import com.bc.caibiao.utils.ActivityStack;
import com.bc.caibiao.utils.NoDoubleClickUtils;
import com.bc.caibiao.utils.ProgressDialogUtils;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.widget.ProgressHUD;
import com.bc.caibiao.wxapi.AllPayRequestUtils;
import com.bc.caibiao.wxapi.PayResult;

import java.util.List;

/**
 * @author wangkai
 * @create 16/9/1 下午4:51
 * @Description :
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements View.OnClickListener, BaseView {
    public T mPresenter;
    public Context mContext;
    public ProgressDialogUtils mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = initPresenter();
        ActivityStack.getInstance().addActivity(this);

        BaseApplication.getInstance().removeFloatView();

        BaseApplication.getInstance().setActivity(this);

        if (mPresenter != null)
            mPresenter.bindView((V) this);
    }

    /**
     * 顶部沉浸
     */
    private void initTitleBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.bindView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.destroy();
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        if (NoDoubleClickUtils.isFastDoubleClick()) {
            return;
        }
        hideInput();
        OnClick(v);
    }

    /**
     * 隐藏键盘
     */
    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    /**
     * 显示键盘
     */
    public void showInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    public void OnClick(View v){};

    public abstract T initPresenter();

    /**
     * 统一实现progress
     *
     * @param tipMsg 提示文字
     */
    @Override
    public void showLoading(String tipMsg) {
        if (mDialog == null) {
            mDialog = new ProgressDialogUtils(mContext, tipMsg);
        }
        mDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    protected ProgressHUD mProgressHUD;

    public void showProgressHUD(Context context, String showMessage) {
        if (this.isFinishing())
            return;
        mProgressHUD = ProgressHUD.show(context, showMessage, true, null);
    }

    public void dismissProgressHUD() {
        try {
            if (mProgressHUD != null)
                mProgressHUD.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 字体不随系统改变
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void showToast(String tipMsg) {
        ToastUtils.showShort(mContext, tipMsg);
    }

    public void finishX(View view) {
        finish();
    }

    @Override
    public void delayFinish(int time) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, time);
    }

    public Member getMember() {
        try {
            return SP.getInstance().getMemberSP();
        } catch (Exception e) {
            return new Member();
        }
    }


    /****************退出动画*****************/
    protected boolean mIsFirstAnim = false;
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
    private void inFromRightOutToLeft() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    private void inFromLeftOutToRight() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }


    /*************客服*******************/
    protected void forwardTokefuAct(){
        Intent aIntent = new Intent(this, SimpleOutLinkAct.class);
        if(TextUtils.isEmpty(SP.getInstance().getString(SPTag.TAG_KEFU_URL))){
            aIntent.putExtra("openUrl", URLConfig.KEFU);
        }else{
            aIntent.putExtra("openUrl", SP.getInstance().getString(SPTag.TAG_KEFU_URL));
        }
        aIntent.putExtra("title","客服");
        startActivity(aIntent);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            BaseApplication.getInstance().removeFloatView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isAppOnForeground()) {
            BaseApplication.getInstance().removeFloatView();
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    /*********************************支付相关回调**************************************/

    /**判断用户是否安装微信客户端*/
    protected Boolean isInstallWX;

    /**选择微信支付*/
    protected void weiXinPay(PublishTaskResultModel result) {

        AllPayRequestUtils.wechatPay(
                this,
                result.data.appid,
                result.data.partnerid,
                result.data.prepayid,
                result.data.noncestr,
                result.data.timestamp,
                "Sign=WXPay",
                result.data.sign);

//        isInstallWX = AllPayRequestUtils.isWXAppInstalledAndSupported(this);
//
//        if (!isInstallWX) {
//
//        }
//        else
//        {
//            ToastUtils.showShort(this,"您还没有安装微信请安装微信");
//        }
    }

    /**
     * 支付宝 支付 结果 显示
     */
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AllPayRequestUtils.SDK_PAY_FLAG:

                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultStatus = payResult.getResultStatus();

                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showShort(BaseActivity.this, "支付成功");
                        onAlipaySuccess();
                    }
                    else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showShort(BaseActivity.this, "支付结果确认中");
                        }
                        else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showShort(BaseActivity.this, "支付失败");
                        }
                    }
                    break;
            }
        }
    };

    protected void onAlipaySuccess(){

    }




}
