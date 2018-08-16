package com.jqh.jqh.app;

import android.app.Activity;
import android.os.Handler;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * Created by jiangqianghua on 18/2/15.
 */

public class Configuration {

    private static final WeakHashMap<String,Object> JQH_CONFIGS = new WeakHashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configuration(){
        JQH_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
        JQH_CONFIGS.put(ConfigType.HANDLER.name(),HANDLER);
    }

    public static Configuration getInstance(){
        return Holder.INSTANCE;
    }
    final WeakHashMap<String,Object> getJqhConfigs(){
        return JQH_CONFIGS;
    }
    private static class Holder{
        private static final Configuration INSTANCE = new Configuration();
    }

    public final void configure(){
        JQH_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    public final Configuration withApiHost(String host){
        JQH_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this ;
    }

    public final Configuration widthInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        JQH_CONFIGS.put(ConfigType.INTERCEPTOR.name(),INTERCEPTORS);
        return this;
    }

    public final Configuration widthInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        JQH_CONFIGS.put(ConfigType.INTERCEPTOR.name(),INTERCEPTORS);
        return this;
    }

    public final Configuration widthWeChatAppId(String appid){
        JQH_CONFIGS.put(ConfigType.WE_CHAT_APP_ID.name(),appid);
        return this;
    }

    public final Configuration widthWeChatAppSecret(String secret){
        JQH_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET.name(),secret);
        return this;
    }
    public final Configuration widthActivity(Activity activity){
        JQH_CONFIGS.put(ConfigType.ACTIVITY.name(),activity);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean)JQH_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not ready,cal configure");
        }
    }

    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T)JQH_CONFIGS.get(key.name());
    }
}
