package com.jqh.jqh.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.jqh.bottom.BottomItemDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.pay.FastPay;
import com.jqh.jqh.ec.pay.IAlPayResultListener;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class ShopCartDelegate extends BottomItemDelegate implements ICartItemListener,IAlPayResultListener {

    private RecyclerView mRecyclerView ;
    ShopCartAdapter adapter = null ;

    private IconTextView iconSelectAll ;

    private AppCompatTextView removeSelectTv ;

    private AppCompatTextView cleanAllTv ;

    private AppCompatTextView mTotalPriceView ;

    private AppCompatTextView mPlayView ;

    private ViewStub mViewStub ;

    private int mCurrentCount = 0 ;
    private int mTotalCount = 0;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_shop_cart);
        removeSelectTv = (AppCompatTextView)rootView.findViewById(R.id.tv_top_shop_cart_remove_selected);
        cleanAllTv = (AppCompatTextView)rootView.findViewById(R.id.tv_top_shop_cart_clear);
        mViewStub = (ViewStub)rootView.findViewById(R.id.stub_no_item);
        mTotalPriceView = (AppCompatTextView)rootView.findViewById(R.id.tv_shop_cart_total_price);
        iconSelectAll = (IconTextView)rootView.findViewById(R.id.icon_shop_cart_select_all);
        mPlayView = (AppCompatTextView)rootView.findViewById(R.id.tv_shop_cart_pay);

        iconSelectAll.setTag(0);
        iconSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int tag = (int)iconSelectAll.getTag();
                if(tag == 0) {
                    iconSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.holo_orange_dark));
                    iconSelectAll.setTag(1);
                    adapter.setIsSelectedAll(true);
                    // 更新view的现实状态
                   // adapter.notifyDataSetChanged();
                    adapter.notifyItemRangeChanged(0,adapter.getItemCount());
                }else{
                    iconSelectAll.setTextColor(Color.GRAY);
                    iconSelectAll.setTag(0);
                    adapter.setIsSelectedAll(false);
                  //  adapter.notifyDataSetChanged();
                    adapter.notifyItemRangeChanged(0,adapter.getItemCount());
                }
            }
        });

        // 删除被选中的数据
        removeSelectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<MultipleItemEntity> data = adapter.getData();
                //要删除的数据
                final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
                for (MultipleItemEntity entity : data) {
                    final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                    if (isSelected) {
                        deleteEntities.add(entity);
                    }
                }
                for (MultipleItemEntity entity : deleteEntities) {
                    int removePosition;
                    final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
                    if (entityPosition > mCurrentCount - 1) {
                        removePosition = entityPosition - (mTotalCount - mCurrentCount);
                    } else {
                        removePosition = entityPosition;
                    }
                    if (removePosition <= adapter.getItemCount()) {
                        adapter.remove(removePosition);
                        mCurrentCount = adapter.getItemCount();
                        //更新数据
                        adapter.notifyItemRangeChanged(removePosition, adapter.getItemCount());
                    }
                }
                checkItemCount();
            }
        });

        cleanAllTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.getData().clear();
                adapter.notifyDataSetChanged();
                checkItemCount();
            }
        });
        // 结算
        mPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // FastPay.create(ShopCartDelegate.this).beginPayDialog();
                createOrder();
            }
        });

    }

    // 创建订单
    private void createOrder(){
        final  String orderUrl = "http://app.api.zanzuanshi.com/api/v1/peyment";
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid","264392");
        orderParams.put("amount","0.01");
        orderParams.put("type",1);
        orderParams.put("ordertype",0);
        orderParams.put("isanonymous",true);
        orderParams.put("followeduser",0);

        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        // 开始支付
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(ShopCartDelegate.this)
                                .setAlPayResultListener(ShopCartDelegate.this)
                                .setOrderID(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();
    }

    private void checkItemCount(){
        final int count = adapter.getItemCount();
        if(count == 0){
            final View stubView = mViewStub.inflate();
            final AppCompatTextView tvToBuy = (AppCompatTextView)stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"go to buy",Toast.LENGTH_SHORT).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        RestClient.builder()
                .url("http://127.0.0.1/shopcartdata")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final ArrayList<MultipleItemEntity> dataList = new ShopCartDataConverter().setJsonData(response).convert();
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        adapter = new ShopCartAdapter(dataList);
                        adapter.setCartItemListener(ShopCartDelegate.this);
                        mRecyclerView.setLayoutManager(manager);
                        mRecyclerView.setAdapter(adapter);
                        checkItemCount();
                        final double totalPrice = adapter.getTotalPrice();
                        mTotalPriceView.setText(String.valueOf(totalPrice));
                    }
                })
                .build().get();
    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double totalPrice = adapter.getTotalPrice();
        mTotalPriceView.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
