package com.jqh.jqh.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.ui.recycler.MultipleViewHolder;
import com.jqh.jqh.ui.recycler.MutipleRecyclerAdapter;

import java.util.List;

public class ShopCartAdapter extends MutipleRecyclerAdapter {

    private boolean mIsSelectedAll = false ;

    private ICartItemListener mCartItemListener;

    private double mTotalPrice = 0.00 ;

    public void setCartItemListener(ICartItemListener cartItemListener) {
        this.mCartItemListener = cartItemListener;
    }

    public static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
        updateTotalPrice();
    }

    public void setIsSelectedAll(boolean isSelectedall){
        this.mIsSelectedAll = isSelectedall;
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()){
            case ShopCartItemType.SHOP_CART_ITEM:
                final int id = item.getField(MultipleFields.ID);
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final String title = item.getField(ShopCartItemFields.TITLE);
                final String desc = item.getField(ShopCartItemFields.DESC);
                final int count = item.getField(ShopCartItemFields.COUNT);
                final double price = item.getField(ShopCartItemFields.PRICE);

                final AppCompatImageView imgThumb = helper.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = helper.getView(R.id.item_shop_cart_title);
                final AppCompatTextView tvDesc = helper.getView(R.id.item_shop_cart_desc);
                final AppCompatTextView tvPrice = helper.getView(R.id.item_shop_cart_price);
                final IconTextView iconPlus = helper.getView(R.id.icon_item_shop_cart_plus);
                final IconTextView iconMinus = helper.getView(R.id.icon_item_shop_cart_minus);
                final AppCompatTextView tvCount = helper.getView(R.id.iv_item_shop_cart_count);
                final IconTextView iconIsSelected = helper.getView(R.id.icon_item_shop_cart_select);

                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvPrice.setText(String.valueOf(price));
                tvCount.setText(String.valueOf(count));

                Glide.with(mContext)
                        .load(thumb)
                        .apply(RECYCLER_OPTIONS)
                        .into(imgThumb);

                item.setField(ShopCartItemFields.IS_SELECTED,mIsSelectedAll);

                final boolean isSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                if(isSelected){
                    iconIsSelected.setTextColor(ContextCompat.getColor(Jqh.getApplicationContext(),R.color.holo_orange_dark));
                }else{
                    iconIsSelected.setTextColor(Color.GRAY);
                }

                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean curisSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                        if(curisSelected){
                            iconIsSelected.setTextColor(Color.GRAY);
                            item.setField(ShopCartItemFields.IS_SELECTED,false);
                        }else{
                            iconIsSelected.setTextColor(ContextCompat.getColor(Jqh.getApplicationContext(),R.color.holo_orange_dark));
                            item.setField(ShopCartItemFields.IS_SELECTED,true);
                        }
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        RestClient.builder()
                                .url("shopcartcount")
                                .params("count",currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tvCount.getText().toString());
                                        countNum++ ;
                                        tvCount.setText(String.valueOf(countNum));

                                        if(mCartItemListener != null){
                                            mTotalPrice = mTotalPrice + price;
                                            final double itemTotal = countNum * price;
                                            mCartItemListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .post();
                    }
                });

                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        if(Integer.parseInt(tvCount.getText().toString()) > 1){
                            RestClient.builder()
                                    .url("shopcartcount")
                                    .params("count",currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum-- ;
                                            tvCount.setText(String.valueOf(countNum));
                                            if(mCartItemListener != null){
                                                mTotalPrice = mTotalPrice - price;
                                                final double itemTotal = countNum * price;
                                                mCartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });
                break;

        }
    }

    private void updateTotalPrice(){
        mTotalPrice = 0 ;
        for(MultipleItemEntity entity:getData()){
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count ;
            mTotalPrice += total ;
        }
    }
}
