package com.jqh.jqh.deletegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.jqh.jqh.deletegates.web.event.Event;
import com.jqh.jqh.deletegates.web.event.EventManager;

//  和原生交互
public class JqhWebInterface {
    private final WebDelegate DELEGATE ;

    public JqhWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    static JqhWebInterface create(WebDelegate delegate){
        return new JqhWebInterface(delegate);
    }

    /**
     * js返回的参数
     * @param params
     * @return
     */
    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if(event != null){
            event.setAction(action);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            event.setDelegate(DELEGATE);
            return event.execute(params);
        }
        return null ;
    }
}
