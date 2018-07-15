package com.jqh.jqh.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.IError;
import com.jqh.jqh.net.calback.IFailure;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.utils.log.JqhLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by jiangqianghua on 18/7/15.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    protected abstract void onSignInSuccess(String userInfo);

    //微信发送请求到第三方后的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方运用发送到请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(JqhWeChat.APP_ID)
                .append("&secret=")
                .append(JqhWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        JqhLogger.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());

    }

    private void getAuth(String authUrl){
        // 获取openid
        RestClient
                .builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        JqhLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl){
        // 获取用户一些真实信息，比如名称，头像，地理位置
        RestClient.builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
