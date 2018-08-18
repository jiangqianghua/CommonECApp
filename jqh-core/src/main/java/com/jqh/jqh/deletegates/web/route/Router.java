package com.jqh.jqh.deletegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.deletegates.web.WebDelegate;
import com.jqh.jqh.deletegates.web.WebDelegateImpl;

/**
 * 跳转的路由类
 */
public class Router {

    private Router(){

    }

    private static class Holder{
        private static final Router INSTAHNCE = new Router();
    }

    public static Router getInstance(){
        return Holder.INSTAHNCE ;
    }

    public final boolean handlerWebUrl(WebDelegate delegate,String url){
        if(url.contains("tel:")){
            callPhone(delegate.getContext(),url);
            return true;
        }
        final JqhDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);

//        if(parentDelegate == null){
//            delegate.start(webDelegate);
//        }else{
//            parentDelegate.start(webDelegate);
//        }
        return true ; // java接管连接
    }

    private void loadWebPage(WebView webView , String url){
        if(webView != null){
            webView.loadUrl(url);
        }else {
            throw new NullPointerException("webview is null");
        }
    }

    /**
     * 加载本地的html文件
     * @param webView
     * @param url
     */
    private void loadLocalPage(WebView webView , String url){
        loadWebPage(webView,"file:///android_asset/"+url);
    }

    private  void loadPage(WebView webView , String url){
        if(URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)){
            loadWebPage(webView,url);
        }else{
            loadLocalPage(webView,url);
        }
    }

    // 跳转url
    public void loadPage(WebDelegate delegate , String url){
        loadPage(delegate.getWebView(), url);
    }
    /**
     * 调用拨打电话
     * @param context
     * @param uri
     */
    private void callPhone(Context context, String uri){
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context,intent,null);
    }
}
