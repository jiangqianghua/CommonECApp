package com.jqh.jqh.app;

import android.content.Context;
import android.os.Handler;

import java.util.WeakHashMap;

/**
 * Created by jiangqianghua on 18/2/15.
 */

public final class Jqh {

    public static Configuration init(Context context){
        getConfiguration().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configuration.getInstance();
    }

    public static Configuration getConfigurator(){
        return Configuration.getInstance();
    }

    public static WeakHashMap<String,Object> getConfiguration(){
        return Configuration.getInstance().getJqhConfigs();
    }

    public static Object getConfiguration(String key){
        return Configuration.getInstance().getJqhConfigs().get(key);
    }

    public static Object getConfiguration(ConfigType type){
        return Configuration.getInstance().getJqhConfigs().get(type.name());
    }

    public static Context getApplicationContext(){
        return (Context) getConfiguration().get(ConfigType.APPLICATION_CONTEXT.name());
    }

    public static Handler getHandler() {
        return (Handler) getConfiguration(ConfigType.HANDLER);
    }
}
