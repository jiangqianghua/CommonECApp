package com.jqh.jqh.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MutipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
implements BaseQuickAdapter.SpanSizeLookup{

    protected MutipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
    }

    public static  MutipleRecyclerAdapter create(List<MultipleItemEntity> data){
        return new MutipleRecyclerAdapter(data);
    }

    public static  MutipleRecyclerAdapter create(DataConverter converter){
        return new MutipleRecyclerAdapter(converter.convert());
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {

    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return 0;
    }
}
