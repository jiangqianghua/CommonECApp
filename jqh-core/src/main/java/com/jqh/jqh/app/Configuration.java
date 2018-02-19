package com.jqh.jqh.app;

import java.util.WeakHashMap;

/**
 * Created by jiangqianghua on 18/2/15.
 */

public class Configuration {

    private static final WeakHashMap<String,Object> JQH_CONFIGS = new WeakHashMap<>();
    private Configuration(){
        JQH_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
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
