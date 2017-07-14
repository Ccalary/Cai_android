package com.bc.caibiao.ui.qiming;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.bc.caibiao.R;
import com.bc.caibiao.base.BaseApplication;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.view.TopBarLayout;


/**
 * Created by chengyanfang on 2017/4/20.
 */

public class SimpleOutLinkAct extends BaseActivity{

    private WebView mWebView;

    private ProgressBar mLoaderBar;

    private String mUrl;

    TopBarLayout mTopLayout;

    public WebChromeClient mWebChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simpleoutlink_layout);
        initUI();
        initData();

        BaseApplication.getInstance().removeFloatView();
    }

    protected void initUI() {
        mWebView = (WebView) findViewById(R.id.webview);
        mLoaderBar = (ProgressBar) findViewById(R.id.loadbar);
        mTopLayout = (TopBarLayout)findViewById(R.id.tablayout);
        mTopLayout.showLeft();
    }

    protected void initData() {
        mUrl = getIntent().getStringExtra("openUrl");
        mTopLayout.setTitle(getIntent().getStringExtra("title"));
        try {
            initWebView();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() throws Exception {
        mWebView.requestFocusFromTouch();
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAllowFileAccess(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.requestFocus();
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.handler.show('<head>'+" + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // 接受所有证书
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                // TODO Auto-generated method stub
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });

        // 设置WebChromeClient
        mWebChromeClient = new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mLoaderBar.setProgress(0);
                    mLoaderBar.setVisibility(View.GONE);
                } else {
                    mLoaderBar.setVisibility(View.VISIBLE);
                    mLoaderBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                // TODO Auto-generated method stub
                super.onReceivedTitle(view, title);
            }

        };

        try {
            mWebView.setWebChromeClient(mWebChromeClient);
        } catch (Exception e) {
            // TODO: handle exception
        }
        loadWebView();

    }

    private void loadWebView() {
        try {
            // 让网页自适应屏幕宽度
            WebSettings webSettings = mWebView.getSettings(); // webView:
            webSettings.setDomStorageEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            mWebView.loadUrl(mUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                super.finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mWebView.clearCache(true);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

}
