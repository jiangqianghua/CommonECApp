package com.jqh.jqh.ec.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.jqh.jqh.ui.JqhLoader;

public class PayAsyncTask extends AsyncTask<String,Void,String>{
    private final Activity ACTIVITY ;
    private final IAlPayResultListener LISTENER ;

    // 订单支付成功
    private static final String AL_PAY_STATUS_SUCCESS = "9000";
    private static final String AL_PAY_STATUS_PAYING = "8000";
    private static final String Al_PAY_STATUS_FAIL = "4000";
    private static final String AL_PAY_STATUS_CANCEL = "6001";
    private static final String AL_PAY_STATUS_CONNECT_ERROR = "6002";
    public PayAsyncTask(Activity activity, IAlPayResultListener listener) {
        this.ACTIVITY = activity;
        this.LISTENER = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        final String alPaySign = params[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        return payTask.pay(alPaySign,true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        JqhLoader.stopLoading();
        final PayResult payResult = new PayResult(s);
        // 支付宝返回此次支付结构及加签，建议对支付宝签名信息拿签约是支付宝提供的公钥做验签
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        switch (resultStatus){
            case AL_PAY_STATUS_SUCCESS:
                if(LISTENER != null)
                    LISTENER.onPaySuccess();
                break;
            case AL_PAY_STATUS_PAYING:
                if(LISTENER != null)
                    LISTENER.onPaying();
                break;
            case AL_PAY_STATUS_CANCEL:
                if(LISTENER != null)
                    LISTENER.onPayCancel();
                break;
            case Al_PAY_STATUS_FAIL:
                if(LISTENER != null)
                    LISTENER.onPayFail();
                break;
            case AL_PAY_STATUS_CONNECT_ERROR:
                if(LISTENER != null)
                    LISTENER.onPayConnectError();
                break;
        }

    }
}
