package com.jqh.jqh.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT ;

    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Jqh.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void firstPage(String url){
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                      //  Log.d("RefreshHandler","firstPage:"+response);

                    }
                })
                .build()
                .get();
    }
}
