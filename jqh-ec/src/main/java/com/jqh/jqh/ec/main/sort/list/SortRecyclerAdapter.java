package com.jqh.jqh.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.main.sort.SortDelegate;
import com.jqh.jqh.ec.main.sort.content.ContentDeleagte;
import com.jqh.jqh.ui.recycler.ItemType;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.ui.recycler.MultipleViewHolder;
import com.jqh.jqh.ui.recycler.MutipleRecyclerAdapter;

import java.util.List;

public class SortRecyclerAdapter extends MutipleRecyclerAdapter {

    private final SortDelegate DELEGATE ;
    private int mPrePosition = 0 ;

    public SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        // 添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_meun_list);
    }

    @Override
    protected void convert(final MultipleViewHolder helper, final MultipleItemEntity item) {
        //super.convert(helper, item);
        switch (helper.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text = item.getField(MultipleFields.TEXT);
                final boolean isClicked = item.getField(MultipleFields.TAG);
                final AppCompatTextView name = helper.getView(R.id.tv_vertical_item_name);
                final View line = helper.getView(R.id.view_line);
                final View itemView = helper.itemView;
                name.setText(text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  int currentPosition = helper.getAdapterPosition();
                        if(mPrePosition != currentPosition){
                            //  还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG,false);
                            notifyItemChanged(mPrePosition);
                            // 更新选中的
                            item.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;
                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if(!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.item_backgroud));
                } else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.holo_orange_dark));
                    line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.holo_orange_dark));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                break;
        }
    }

    private void showContent(int contentId){
        final ContentDeleagte deleagte = ContentDeleagte.newInstance(contentId);
        switchContent(deleagte);
    }

    private void switchContent(ContentDeleagte deleagte){
        final JqhDelegate contentDelegate = DELEGATE.findChildFragment(ContentDeleagte.class);
        if(contentDelegate != null) {
            //  当前的delegate被替换
            contentDelegate.replaceFragment(deleagte, false);
        }
    }
}
