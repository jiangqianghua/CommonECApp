package com.jqh.jqh.wechat.templates;


import com.jqh.jqh.activites.ProxyActivity;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.wechat.BaseWXEntryActivity;
import com.jqh.jqh.wechat.JqhWeChat;

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
       // finish();
        // 不用动画消失
       // overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        JqhWeChat.getInstance().getSignInCallBack().onSignInSuccess(userInfo);
    }
}
