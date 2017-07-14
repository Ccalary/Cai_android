package com.bc.caibiao.ui.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bc.caibiao.R;
import com.bc.caibiao.model.SystemOption;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.ProgressDialogUtils;
import com.bc.caibiao.utils.ToastUtils;

import retrofit2.http.Url;
import rx.Observer;

/**
 * Created  on 2017/4/19.
 * 联系客服
 */

public class WaiterActivity extends BaseActivity {
    /**
     * UI元素
     */
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_waiter);
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("http://chat.talk99.cn/chat/chat/p.do?c=10036632&f=10054106&g=10068720");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
