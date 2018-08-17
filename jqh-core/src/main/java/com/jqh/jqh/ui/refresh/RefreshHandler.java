package com.jqh.jqh.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.DataConverter;
import com.jqh.jqh.ui.recycler.MutipleRecyclerAdapter;
import com.jqh.jqh.ui.recycler.PageBean;

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT ;

    private final PageBean BEAN ;
    private  final RecyclerView REYCLERVIEW ;
    private MutipleRecyclerAdapter mAdapter = null ;
    private final DataConverter CONVERTER ;


    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,RecyclerView recyclerView ,DataConverter converter,
                          PageBean bean) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        this.REYCLERVIEW = recyclerView ;
        this.CONVERTER = converter ;
        this.BEAN = bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout, RecyclerView recyclerView,DataConverter converter){
        return new RefreshHandler(swipeRefreshLayout,recyclerView,converter,new PageBean());
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
        BEAN.setmDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                      //  Log.d("RefreshHandler","firstPage:"+response);
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setmTotal(object.getInteger("total"))
                        .setmPageSize(object.getInteger("page_size"))
                        ;
                        // 设置Adapter
                        mAdapter = MutipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,REYCLERVIEW);
                        REYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
