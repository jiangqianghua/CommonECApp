package com.jqh.jqh.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.jqh.jqh.ui.recycler.DataConverter;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.utils.storage.JqhPreference;

import java.util.ArrayList;

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final String jsonStr =
                JqhPreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!jsonStr.equals("")) {
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(MultipleFields.TEXT, historyItemText)
                        .build();
                ENTITES.add(entity);
            }
        }

        return ENTITES;
    }
}
