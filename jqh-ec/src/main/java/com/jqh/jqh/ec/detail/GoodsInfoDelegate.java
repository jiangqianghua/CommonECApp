package com.jqh.jqh.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;

public class GoodsInfoDelegate extends JqhDelegate {

    AppCompatTextView mGoodsInfoTitle = null;
    AppCompatTextView mGoodsInfoDesc = null;
    AppCompatTextView mGoodsInfoPrice = null;

    private static final String ARG_GOODS_DATA = "ARG_GOODS_DATA";
    private JSONObject mData = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_info;
    }

    public static GoodsInfoDelegate create(String goodsInfo) {
        final Bundle args = new Bundle();
        args.putString(ARG_GOODS_DATA, goodsInfo);
        final GoodsInfoDelegate delegate = new GoodsInfoDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        final String goodsData = args.getString(ARG_GOODS_DATA);
        mData = JSON.parseObject(goodsData);
    }


    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        AppCompatTextView mGoodsInfoTitle = (AppCompatTextView)rootView.findViewById(R.id.tv_goods_info_title);
        AppCompatTextView mGoodsInfoDesc = (AppCompatTextView)rootView.findViewById(R.id.tv_goods_info_desc);
        AppCompatTextView mGoodsInfoPrice = (AppCompatTextView)rootView.findViewById(R.id.tv_goods_info_price);

        final String name = mData.getString("name");
        final String desc = mData.getString("description");
        final double price = mData.getDouble("price");
        mGoodsInfoTitle.setText(name);
        mGoodsInfoDesc.setText(desc);
        mGoodsInfoPrice.setText(String.valueOf(price));

    }
}
