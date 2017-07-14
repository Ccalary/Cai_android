package com.bc.caibiao.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.BaseSubscribe;
import com.bc.caibiao.ui.login.LoginActivity;
import com.bc.caibiao.ui.me.MeFragment;
import com.bc.caibiao.ui.qiming.QiMingFragment;
import com.bc.caibiao.ui.search.SearchHomeFragment;
import com.bc.caibiao.ui.shangbiao.ShangbiaoFragment;
import com.bc.caibiao.utils.BCL;
import com.bc.caibiao.utils.DeviceInfoUtils;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.utils.ToastUtils;


import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


import java.util.List;


import static com.bc.caibiao.ui.search.SearchHomeFragment.REQUEST_CODE_CHOOSE_PHOTO_IDLEFT;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private TabHost mTabHost;

    private QiMingFragment mQiMingFragment;
    private SearchHomeFragment mSearchHomeFragment;
    private ShangbiaoFragment mShangbiaoFragment;
    private MeFragment mMeFragment;

    private SearchHomeFragment mCacheSearchHomeFragment;
    private QiMingFragment mCacheQiMingFragment;
    private ShangbiaoFragment mCacheShangbiaoFragment;
    private MeFragment mCacheMeFragment;

    private TextView searchText, erpText, recoveryText, userCenterText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initTab();
    }

    private void initFragment() {
        this.mSearchHomeFragment = (SearchHomeFragment) Fragment
                .instantiate(this, SearchHomeFragment.class.getName(), null);
        this.mQiMingFragment = (QiMingFragment) Fragment
                .instantiate(this, QiMingFragment.class.getName(), null);
        this.mShangbiaoFragment = (ShangbiaoFragment) Fragment
                .instantiate(this, ShangbiaoFragment.class.getName(), null);
        this.mMeFragment = (MeFragment) Fragment.instantiate(this,
                MeFragment.class.getName(), null);
    }


    private void initTab() {
        mTabHost = (TabHost) this.findViewById(android.R.id.tabhost);
        mTabHost.setup();

        FrameLayout layout = (FrameLayout) this.mTabHost.getChildAt(0);
        TabWidget tw = (TabWidget) layout.getChildAt(2);
        View aSearchView;
        View aQimingView;
        View aShangbiaoView;
        View aMeView;

        mTabHost.setBackgroundResource(R.drawable.tab_bg);
        aSearchView = LayoutInflater.from(this)
                .inflate(R.layout.pxf_tab_indicator_layout_search,
                        tw, false);
        aQimingView = LayoutInflater.from(this)
                .inflate(R.layout.pxf_tab_indicator_layout_home,
                        tw, false);
        aShangbiaoView = LayoutInflater.from(this)
                .inflate(R.layout.pxf_tab_indicator_layout_recovery,
                        tw, false);
        aMeView = LayoutInflater.from(this).inflate(
                R.layout.pxf_tab_indicator_layout_usercenter, tw, false);

        addTab(tw, "查询", aSearchView);
        addTab(tw, "起名", aQimingView);
        addTab(tw, "商标", aShangbiaoView);
        addTab(tw, "我", aMeView);

        searchText = (TextView) aSearchView.findViewById(R.id.search_title);
        erpText = (TextView) aQimingView.findViewById(R.id.erp_title);
        recoveryText = (TextView) aShangbiaoView.findViewById(R.id.recovery_title);
        userCenterText = (TextView) aMeView.findViewById(R.id.usercenter_title);

        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(0);
        onTabChanged("查询");
    }


    private void addTab(TabWidget tw, String strId, View aView) {
        TabHost.TabSpec home = this.mTabHost.newTabSpec(strId);
        home.setIndicator(aView);
        home.setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String tag) {
                View v = new View(MainActivity.this);
                return v;
            }
        });
        mTabHost.addTab(home);
    }


    @Override
    public void onTabChanged(String tabId) {

        if (this.mFragmentManager == null) {
            this.mFragmentManager = this.getSupportFragmentManager();
        }
        this.mFragmentTransaction = this.mFragmentManager.beginTransaction();
        this.getCacheFragement();

        if (this.isEquStr(tabId, "查询")) {
            if(BaseApplication.isAddedFloatView){
                BaseApplication.getInstance().removeFloatView();
            }

            this.setCurrentFragment(mCacheSearchHomeFragment,
                    mSearchHomeFragment, "查询");
            searchText.setTextColor(getResources().getColor(R.color.main_color));
            erpText.setTextColor(getResources().getColor(R.color.base_gray));
            recoveryText.setTextColor(getResources().getColor(R.color.base_gray));
            userCenterText.setTextColor(getResources().getColor(R.color.base_gray));
        } else if (this.isEquStr(tabId, "起名")) {

            if(!BaseApplication.isAddedFloatView){
                BaseApplication.getInstance().addFloatView();
            }

            this.setCurrentFragment(mCacheQiMingFragment,
                    mQiMingFragment, "起名");
            searchText.setTextColor(getResources().getColor(R.color.base_gray));
            erpText.setTextColor(getResources().getColor(R.color.main_color));
            recoveryText.setTextColor(getResources().getColor(R.color.base_gray));
            userCenterText.setTextColor(getResources().getColor(R.color.base_gray));
        } else if (this.isEquStr(tabId, "商标")) {
            if(BaseApplication.isAddedFloatView){
                BaseApplication.getInstance().removeFloatView();
            }

            this.setCurrentFragment(mCacheShangbiaoFragment,
                    mShangbiaoFragment, "商标");
            erpText.setTextColor(getResources().getColor(R.color.base_gray));
            searchText.setTextColor(getResources().getColor(R.color.base_gray));
            recoveryText.setTextColor(getResources().getColor(R.color.main_color));
            userCenterText.setTextColor(getResources().getColor(R.color.base_gray));
        } else if (this.isEquStr(tabId, "我")) {

            if(BaseApplication.isAddedFloatView){
                BaseApplication.getInstance().removeFloatView();
            }

            try {
                if (SP.getInstance().getString(SPTag.TAG_MEMBER_ID) == null || "".equals(SP.getInstance().getString(SPTag.TAG_MEMBER_ID))) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    mTabHost.setCurrentTab(0);
                    onTabChanged("查询");
                    return;
                }
            } catch (Exception e) {

            }

            this.setCurrentFragment(mCacheMeFragment,
                    mMeFragment, "我");
            erpText.setTextColor(getResources().getColor(R.color.base_gray));
            searchText.setTextColor(getResources().getColor(R.color.base_gray));
            recoveryText.setTextColor(getResources().getColor(R.color.base_gray));
            userCenterText.setTextColor(getResources().getColor(R.color.main_color));
        }

        this.mFragmentTransaction.commitAllowingStateLoss();

        MainActivity.this.mFragmentManager.executePendingTransactions();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            if ("logout".equals(intent.getExtras().get(Constant.LOGOUT))) {
                mTabHost.setCurrentTab(0);
            }
        }
    }

    private void getCacheFragement() {
        this.mCacheSearchHomeFragment = (SearchHomeFragment) getCacheFragment("查询");
        this.mCacheQiMingFragment = (QiMingFragment) getCacheFragment("起名");
        this.mCacheShangbiaoFragment = (ShangbiaoFragment) getCacheFragment("商标");
        this.mCacheMeFragment = (MeFragment) getCacheFragment("我");

        this.hideFragment(mCacheSearchHomeFragment);
        this.hideFragment(mCacheQiMingFragment);
        this.hideFragment(mCacheShangbiaoFragment);
        this.hideFragment(mCacheMeFragment);
    }


    private boolean isEquStr(String tabId, String resId) {
        return TextUtils.equals(tabId, resId);
    }

    private void setCurrentFragment(Fragment aCache, Fragment aCurrt, String resId) {
        if (aCache == null) {
            this.mFragmentTransaction.add(R.id.realtabcontent, aCurrt,
                    resId);
        } else {
            mFragmentTransaction.show(aCache);
        }
    }

    private void hideFragment(Fragment aHideFragment) {
        if (aHideFragment != null) {
            this.mFragmentTransaction.hide(aHideFragment);
        }
    }

    private Fragment getCacheFragment(String resId) {
        return this.mFragmentManager.findFragmentByTag(resId);
    }


    /**
     * 退出应用
     */
    private long clickBackTime;

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (clickBackTime == 0 || System.currentTimeMillis() - clickBackTime > 2000) {
                ToastUtils.showShort(BaseApplication.getInstance(), "再按一次退出应用");
                clickBackTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE_PHOTO_IDLEFT) {
                mSearchHomeFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseApplication.getInstance().setActivity(this);
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
}
