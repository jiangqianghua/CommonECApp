package com.jqh.jqh.wechat.templates;

import android.widget.Toast;

import com.jqh.jqh.activites.ProxyActivity;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ui.JqhLoader;
import com.jqh.jqh.wechat.BaseWXPayEntryActivity;

/**
 * Created by 傅令杰 on 2017/1/2
 */

public class WXPayEntryTemplate extends BaseWXPayEntryActivity {

    @Override
    protected void onPaySuccess() {
        Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayFailt() {
        Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayCalcel() {
        Toast.makeText(this,"支付取消",Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }
}
