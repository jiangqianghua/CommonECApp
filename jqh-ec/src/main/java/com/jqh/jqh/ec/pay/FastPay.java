package com.jqh.jqh.ec.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jqh.jqh.app.ConfigType;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.JqhLoader;
import com.jqh.jqh.utils.log.JqhLogger;
import com.jqh.jqh.wechat.JqhWeChat;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class FastPay implements View.OnClickListener{

    private IAlPayResultListener mIAlPayResultListener = null ;

    private Activity mActivity ;

    private AlertDialog mDialog = null ;

    private int mOrderID = -1 ;

    private FastPay(JqhDelegate delegate){
        this.mActivity = delegate.getProxyActivity();

        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();

    }

    public FastPay setAlPayResultListener(IAlPayResultListener mIAlPayResultListener) {
        this.mIAlPayResultListener = mIAlPayResultListener;
        return this ;
    }

    public FastPay setOrderID(int mOrderID) {
        this.mOrderID = mOrderID;
        return this ;
    }

    public static FastPay create(JqhDelegate delegate){
        return new FastPay(delegate);
    }

    public void beginPayDialog(){
        mDialog.show();
        final Window window = mDialog.getWindow();
        if(window != null){
            window.setContentView(R.layout.dialog_pay_pencel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_alpay).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
        }

    }

    public final void alPay(int orderId){
        //final String singUrl = "你的服务端支付地址" + orderId;
        final String singUrl = "";
        //获取签名
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final String paySign = JSON.parseObject(response).getString("result");
                        JqhLogger.d("PAY_SIGN",paySign);
                        //  必须要在异步里面支付
                        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity,mIAlPayResultListener);
                        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,paySign);
                    }
                })
                .build().post();
    }

    private final void weChatPay(int orderId){
        JqhLoader.stopLoading();
        final String webChatPrePayUrl = "";

        final IWXAPI iwxapi = JqhWeChat.getInstance().getWXAPI();
        final String appId = (String)Jqh.getConfiguration(ConfigType.WE_CHAT_APP_ID);
        iwxapi.registerApp(appId);
        RestClient.builder()
                .url(webChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result = JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id == R.id.btn_dialog_pay_alpay){
            alPay(mOrderID);
            mDialog.cancel();
        }else if(id == R.id.btn_dialog_pay_wechat){
            mDialog.cancel();
        }else if(id == R.id.btn_dialog_pay_cancel){
            mDialog.cancel();
        }
    }


}
