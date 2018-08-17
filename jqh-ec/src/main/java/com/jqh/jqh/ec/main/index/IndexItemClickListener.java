package com.jqh.jqh.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.detail.GoodsDetailDelegate;

public class IndexItemClickListener extends SimpleClickListener {
    private final JqhDelegate DELEGATE ;

    private IndexItemClickListener(JqhDelegate delegate){
        this.DELEGATE = delegate ;
    }
    public static SimpleClickListener create(JqhDelegate delegate){
        return new IndexItemClickListener(delegate);
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
         final GoodsDetailDelegate delegate = GoodsDetailDelegate.create();
         DELEGATE.start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
