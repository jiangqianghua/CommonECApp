package com.jqh.generalec.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.ec.icon.FontEcModule;
import com.jqh.jqh.ec.launcher.database.DataBaseManager;
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
                // 设置拦截器，模拟http访问数据
                .widthInterceptor(new DebugInterceptor("index",R.raw.signin))
                .widthInterceptor(new DebugInterceptor("shops",R.raw.shop))
                .widthWeChatAppId("wx7d10f050b6bdbda3")
                .widthWeChatAppSecret("222")
                .configure();
        initSteeho();
        DataBaseManager.getInstance().init(this);

        Iconify.with(new FontAwesomeModule())
                .with(new IoniconsModule())
                .with(new FontEcModule());
    }

    private void initSteeho(){
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}
