package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.MainActivity;
import com.bc.caibiao.utils.LogoutAlertDialog;
import com.bc.caibiao.utils.PhoneDialog;
import com.bc.caibiao.utils.SP;
import com.google.gson.Gson;

import cn.jpush.android.api.JPushInterface;

/**
 * Created  on 2017/4/19.
 * 个人资料
 */

public class SettingActivity extends BaseActivity {
    private Member mMember;
    /**
     * UI元素
     */
    private RelativeLayout mRLMyInfo;//个人资料
    private RelativeLayout mRLChangePwd;//修改密码
    private RelativeLayout mRLHelp;//帮组
    private RelativeLayout mRLWaiter;//联系客服
    private RelativeLayout mRLCall;//联系我们
    private RelativeLayout mRLAbout;//关于我们
    private TextView mTvLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mRLMyInfo = (RelativeLayout) findViewById(R.id.rl_myinfo);
        mRLMyInfo.setOnClickListener(this);
        mRLChangePwd = (RelativeLayout) findViewById(R.id.rl_change_pwd);
        mRLChangePwd.setOnClickListener(this);
        mRLHelp = (RelativeLayout) findViewById(R.id.rl_help);
        mRLHelp.setOnClickListener(this);
        mRLWaiter = (RelativeLayout) findViewById(R.id.rl_waiter);
        mRLWaiter.setOnClickListener(this);
        mRLCall = (RelativeLayout) findViewById(R.id.rl_call);
        mRLCall.setOnClickListener(this);
        mRLAbout = (RelativeLayout) findViewById(R.id.rl_about);
        mRLAbout.setOnClickListener(this);
        mTvLogout = (TextView) findViewById(R.id.tv_logout);
        mTvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.rl_myinfo:
                intent.setClass(this, MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_change_pwd:
                intent.setClass(this, ChangePwdActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_help:
                intent.setClass(this, AboutUsActivity.class);
                intent.putExtra("CFGAPP", "cfg_app_help");
                startActivity(intent);
                break;
            case R.id.rl_waiter:
                intent.setClass(this, WaiterActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_call:
                new PhoneDialog(SettingActivity.this, new PhoneDialog.onClickCall() {
                    @Override
                    public void onResult() {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + "400-838-0082");
                        callIntent.setData(data);
                        startActivity(callIntent);
                    }
                }).ShowDialog();
                break;
            case R.id.rl_about:
                intent.setClass(this, AboutUsActivity.class);
                intent.putExtra("CFGAPP", "cfg_app_about_us");
                startActivity(intent);
                break;
            case R.id.tv_logout:
                new LogoutAlertDialog(SettingActivity.this, new LogoutAlertDialog.onClickConfirm() {
                    @Override
                    public void onResult() {
                        JPushInterface.setAlias(mContext, null, null);
                        SP.getInstance().saveString(SPTag.TAG_MEMBER, "");
                        SP.getInstance().saveString(SPTag.TAG_MEMBER_ID, "");
                        SP.getInstance().saveString(SPTag.TAG_MEMBER_NAME, "");
                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                        intent.putExtra(Constant.LOGOUT, "logout");
                        startActivity(intent);
                        finish();
                    }
                }).ShowDialog();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
