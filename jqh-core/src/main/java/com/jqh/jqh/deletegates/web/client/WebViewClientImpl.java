package com.jqh.jqh.deletegates.web.client;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jqh.jqh.deletegates.web.WebDelegate;
import com.jqh.jqh.deletegates.web.route.Router;
import com.jqh.jqh.utils.log.JqhLogger;

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate DELEGATE ;

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        JqhLogger.d("shouldOverrideUrlLoading",url);
        return Router.getInstance().handlerWebUrl(DELEGATE,url);
    }
}
