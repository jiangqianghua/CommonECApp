package com.jqh.generalec.example;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.jqh.generalec.example.event.TestEvent;
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
                .widthInterceptor(new DebugInterceptor("sortlist",R.raw.sort_list))
                .widthInterceptor(new DebugInterceptor("sortcontent",R.raw.sort_content))
                .widthInterceptor(new DebugInterceptor("shopcartdata",R.raw.shop_cart_data))
                .widthInterceptor(new DebugInterceptor("shopcartcount",R.raw.shop_cart_count))
                .widthInterceptor(new DebugInterceptor("orderlist",R.raw.order_list))
                .widthInterceptor(new DebugInterceptor("address",R.raw.address))
                .widthInterceptor(new DebugInterceptor("deleteaddress",R.raw.delete_address))
                .widthInterceptor(new DebugInterceptor("goodsdetail",R.raw.goods_detial))
                .widthInterceptor(new DebugInterceptor("addshopcart",R.raw.add_shop_cart))
                .withJavascriptInterface("Jqh")
                // 注册js调用的事件
                .widthWebEvent("test", new TestEvent())
                .widthWeChatAppId("wx7d10f050b6bdbda3")
                .widthWeChatAppSecret("222")
                .configure();
        initSteeho();
        DataBaseManager.getInstance().init(this);

        Iconify.with(new FontAwesomeModule())
                .with(new IoniconsModule())
                .with(new FontEcModule());
        // 初始化工具包，第三方的
        Utils.init(getApplicationContext());
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
