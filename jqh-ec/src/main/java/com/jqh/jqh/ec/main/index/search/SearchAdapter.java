package com.jqh.jqh.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;

import com.jqh.jqh.ec.R;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.ui.recycler.MultipleViewHolder;
import com.jqh.jqh.ui.recycler.MutipleRecyclerAdapter;

import java.util.List;

public class SearchAdapter extends MutipleRecyclerAdapter{
    public SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);

    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = helper.getView(R.id.tv_search_item);
                final String history = item.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }

    }
}
