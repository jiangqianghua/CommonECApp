package com.jqh.jqh.ec.main.personal.list.order;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.ui.recycler.MultipleViewHolder;
import com.jqh.jqh.ui.recycler.MutipleRecyclerAdapter;

import java.util.List;

public class OrderListAdapter extends MutipleRecyclerAdapter {


    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();


    public OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()){
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = helper.getView(R.id.image_order_list);
                final AppCompatTextView title = helper.getView(R.id.tv_order_list_title);
                final AppCompatTextView price = helper.getView(R.id.tv_order_list_price);
                final AppCompatTextView time = helper.getView(R.id.tv_order_list_time);

                final String titleVal = item.getField(OrderItemFields.TITLE);
                final String timeVal = item.getField(OrderItemFields.TIME);
                final double priceVal = item.getField(OrderItemFields.PRICE);
                final String imageUrl = item.getField(MultipleFields.IMAGE_URL);

                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);

                title.setText(titleVal);
                price.setText("价格：" + String.valueOf(priceVal));
                time.setText("时间：" + timeVal);
                break;
        }
    }
}
