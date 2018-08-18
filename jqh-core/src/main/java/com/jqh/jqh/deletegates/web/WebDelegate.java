package com.jqh.jqh.deletegates.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.jqh.jqh.app.ConfigType;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.deletegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public abstract  class WebDelegate extends JqhDelegate implements IWebViewInitializer {
   private WebView mWebView = null ;
   private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
   private String mUrl ;
   private boolean mIsWebViewAbailable = false ;

   private JqhDelegate mTopDelegate ;

    public WebDelegate() {
    }
    public String getUrl(){
        if(mUrl == null)
            throw new NullPointerException("url is null");
        return mUrl ;
    }

    public abstract  IWebViewInitializer setInitializer();

    // 设置最底层的delegate
    public void setTopDelegate(JqhDelegate delegate){
        mTopDelegate = delegate;
    }

    public JqhDelegate getTopDelegate() {
        if(mTopDelegate == null)
            return this ;
        return mTopDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        if(mWebView != null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        else {
            final IWebViewInitializer initializer = setInitializer();
            if(initializer != null){
                final WeakReference<WebView> webViewWeakReference = new WeakReference<WebView>(new WebView(getContext()),WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebChromeClient(initializer.initWebChromeClient());
                mWebView.setWebViewClient(initializer.initWebViewClient());
                String name = (String) Jqh.getConfiguration(ConfigType.JAVASCRIPT_INTERFACE);
                mWebView.addJavascriptInterface(JqhWebInterface.create(this),name);
                mIsWebViewAbailable = true ;
            }else{
                throw  new NullPointerException("Initializer is null");
            }
        }
    }

    public WebView getWebView(){
        if(mWebView == null){
            throw  new NullPointerException("webView is null");
        }
        return mIsWebViewAbailable?mWebView:null ;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWebView != null)
            mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWebView != null)
            mWebView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAbailable = false ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null ;
        }
    }
}
