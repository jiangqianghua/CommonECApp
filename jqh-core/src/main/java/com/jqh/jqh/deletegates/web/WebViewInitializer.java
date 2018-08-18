package com.jqh.jqh.deletegates.web;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * webview 初始化参数
 */
public class WebViewInitializer {

    public WebView createWebView(WebView webView){
        // 可以调试，最低19
        WebView.setWebContentsDebuggingEnabled(true);
        // 不允许横向滚动
        webView.setHorizontalScrollBarEnabled(false);
        // 不允许纵向滚动
        webView.setVerticalScrollBarEnabled(false);

        //  允许截图
        webView.setDrawingCacheEnabled(true);
        //  屏蔽长安事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        //  初始化settings
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        final String ua = settings.getUserAgentString();
        settings.setUserAgentString(ua+"Jqh");//  方便判断是我们自己打开的网页
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        // 禁止缩放
        settings.setSupportZoom(false);
        // 文件权限
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowContentAccess(true);
        // 缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webView;
    }
}
