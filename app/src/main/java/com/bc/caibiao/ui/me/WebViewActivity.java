package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bc.caibiao.R;
import com.bc.caibiao.model.SystemOption;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.TopBarLayout;

/**
 * Created  on 2017/4/19.
 * 关于我们
 */

public class WebViewActivity extends BaseActivity {
    /**
     * UI元素
     */
    private TopBarLayout mTitleBar;
    private WebView mWebView;
    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webview);
        mTitleBar = (TopBarLayout) findViewById(R.id.help_title);

        if (getIntent() != null) {
            mUrl = getIntent().getStringExtra("URL");
            mTitle = getIntent().getStringExtra("TITLE");
        }


        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mTitleBar.setTitle(mTitle);
        mWebView.loadUrl(mUrl);
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
