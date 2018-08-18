package com.jqh.jqh.deletegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.deletegates.web.WebDelegate;

public abstract  class Event implements IEvent {

    private Context mContext ;

    private String mAction = null ;

    private WebDelegate mDelegate = null ;

    private String mUrl ;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }

    public JqhDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public WebView getWebView(){
        return mDelegate.getWebView();
    }
}
