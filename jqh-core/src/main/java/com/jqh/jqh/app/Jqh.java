package com.jqh.jqh.app;

import android.content.Context;

import java.util.WeakHashMap;

/**
 * Created by jiangqianghua on 18/2/15.
 */

public final class Jqh {

    public static Configuration init(Context context){
        getConfiguration().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configuration.getInstance();
    }

    public static WeakHashMap<String,Object> getConfiguration(){
        return Configuration.getInstance().getJqhConfigs();
    }


    public static Context getApplicationContext(){
        return (Context) getConfiguration().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
