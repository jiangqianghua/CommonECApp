package com.jqh.jqh.ec.main.personal.list.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jqh.jqh.ui.recycler.DataConverter;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class OrderListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size= array.size();
        for(int i = 0 ; i < size ; i++){
            final JSONObject data = array.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");
            final String time = data.getString("time");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setField(OrderItemFields.TITLE,title)
                    .setField(OrderItemFields.TIME,time)
                    .setField(OrderItemFields.PRICE,price)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(MultipleFields.ID,id)
                    .build();
            ENTITES.add(entity);
        }
        return ENTITES;
    }
}
