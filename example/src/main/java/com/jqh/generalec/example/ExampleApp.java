package com.jqh.generalec.example;

import android.app.Application;

import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.interceptors.DebugInterceptor;

/**
 * Created by jiangqianghua on 18/2/17.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Jqh.init(this)
                .withApiHost("http://127.0.0.1")
                .widthInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();
    }
}
