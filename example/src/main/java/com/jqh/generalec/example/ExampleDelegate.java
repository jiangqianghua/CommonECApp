package com.jqh.generalec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.IError;
import com.jqh.jqh.net.calback.IFailure;
import com.jqh.jqh.net.calback.ISuccess;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public class ExampleDelegate extends JqhDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        testRestClient();
    }

    private void testRestClient(){
        RestClient.builder()
                .url("http://news.baidu.com/")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                    }
                }).failure(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(getContext(),"onFailure",Toast.LENGTH_LONG).show();
            }
        }).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getContext(),"error:"+code + ":"+msg,Toast.LENGTH_LONG).show();
            }
        }).build().get();
    }
}
