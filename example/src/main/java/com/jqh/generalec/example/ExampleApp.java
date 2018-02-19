package com.jqh.generalec.example;

import android.app.Application;

import com.jqh.jqh.app.Jqh;

/**
 * Created by jiangqianghua on 18/2/17.
 */

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Jqh.init(this)
                .withApiHost("http://127.0.0.1")
                .configure();
    }
}
