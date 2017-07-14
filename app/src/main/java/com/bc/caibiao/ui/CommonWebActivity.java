package com.bc.caibiao.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bc.caibiao.R;
import com.bc.caibiao.view.TopBarLayout;

/**
 * @author liyang
 * @Description: 协议
 * create at 2016/5/18 9:55
 */
public class CommonWebActivity extends BaseActivity {
    private WebView wvAgreement;
    private String title = "", url = "";

    public static final String INTENT_TITLE = "INTENT_TITLE";
    public static final String INTENT_URL = "INTENT_URL";
    private TopBarLayout tblTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_webview);

        if (getIntent() != null) {
            title = getIntent().getStringExtra(INTENT_TITLE);
            url = getIntent().getStringExtra(INTENT_URL);
        }
        initView();

    }

    private void initView() {
        this.tblTitle = (TopBarLayout) findViewById(R.id.tblTitle);
        tblTitle.getTvTitle().setText(title);
        this.wvAgreement = (WebView) findViewById(R.id.wvCommon);
        // WebSettings webSettings = this.wvAgreement.getSettings();
        this.wvAgreement.getSettings().setJavaScriptEnabled(true);// 开启jacascript
        this.wvAgreement.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 支持通过JS打开新窗口
        this.wvAgreement.getSettings().setSupportZoom(true);
        this.wvAgreement.getSettings().setLoadsImagesAutomatically(true);// 支持自动加载图片
        // 默认不加载缓存
        this.wvAgreement.getSettings().setCacheMode(this.wvAgreement.getSettings().LOAD_NO_CACHE);
        // this.wvAgreement.requestFocus();// 使WebView内的输入框等获得焦点
        this.wvAgreement.requestFocusFromTouch();
        this.wvAgreement.getSettings().setBuiltInZoomControls(true);// 设置支持缩放
        this.wvAgreement.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页，如果没有这个在低分辨率手机上显示会异常
        if (!url.contains("http:") && !url.contains("https:")) {
            url = "http://" + url;
        }
        wvAgreement.loadUrl(url);
        wvAgreement.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public void OnClick(View v) {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
