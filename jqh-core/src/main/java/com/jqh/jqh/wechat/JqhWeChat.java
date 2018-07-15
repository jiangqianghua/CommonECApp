package com.jqh.jqh.wechat;

import android.app.Activity;

import com.jqh.jqh.app.ConfigType;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.wechat.callbacks.IWeChatSigninCallBack;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by jiangqianghua on 18/7/15.
 */

public class JqhWeChat {

    public static final String APP_ID = (String)Jqh.getConfiguration(ConfigType.WE_CHAT_APP_ID);
    public static final String APP_SECRET = (String)Jqh.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);

    private final IWXAPI WXAPI;

    private IWeChatSigninCallBack mSignInCallBack ;

    private static final class Holder{
        private static final JqhWeChat INSTANCE = new JqhWeChat();
    }

    public static JqhWeChat getInstance(){
        return Holder.INSTANCE;
    }

    private JqhWeChat(){
        final Activity activity = (Activity) Jqh.getConfiguration(ConfigType.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI(){
        return WXAPI;
    }

    public JqhWeChat onSignSuccess(IWeChatSigninCallBack callBack)
    {
        this.mSignInCallBack = callBack ;
        return this ;
    }

    public IWeChatSigninCallBack getSignInCallBack(){
        return mSignInCallBack;
    }
    public final void signIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }

}
