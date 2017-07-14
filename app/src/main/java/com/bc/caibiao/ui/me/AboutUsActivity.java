package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.SystemOption;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ToastUtils;
import com.bc.caibiao.view.TopBarLayout;

import rx.Observer;

/**
 * Created  on 2017/4/19.
 * 关于我们
 */

public class AboutUsActivity extends BaseActivity {
    /**
     * UI元素
     * */
    private TopBarLayout mHelpTitle;
    private TopBarLayout mAboutUsTitle;
    private WebView mWebView;
    private String mEnName;//传参
    private SystemOption mSystemOption;//返回结果
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about_us);
        mWebView = (WebView)findViewById(R.id.webview);
        mHelpTitle = (TopBarLayout)findViewById(R.id.help_title);
        mAboutUsTitle = (TopBarLayout)findViewById(R.id.about_us_title);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mEnName = getIntent().getExtras().getString("CFGAPP");
        if("cfg_app_help".equals(mEnName)){
            mHelpTitle.setVisibility(View.VISIBLE);
            mAboutUsTitle.setVisibility(View.GONE);
        } else {
            mHelpTitle.setVisibility(View.GONE);
            mAboutUsTitle.setVisibility(View.VISIBLE);
        }
        requestData();
    }

    private void requestData() {
        BCHttpRequest.getOtherInterface().getAboutUsApi(mEnName)
                .compose(HttpResponseHelper.<SystemOption>getAllData())
                .subscribe(new Observer<SystemOption>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                       
                    }

                    @Override
                    public void onNext(SystemOption systemOption) {
                        mSystemOption = systemOption;
                        refreshUI();
                    }
                });
    }

    /**
     * 刷新页面
     * */
    private void refreshUI(){
        mWebView.loadData(mSystemOption.getContent(), "text/html; charset=UTF-8", null);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
