package com.jqh.generalec.example.event;

import android.webkit.WebView;
import android.widget.Toast;

import com.jqh.jqh.deletegates.web.event.Event;

/**
 * test js call event
 */
public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(),params,Toast.LENGTH_LONG).show();
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();", null);
                }
            });
        }
        return null;
    }
}
