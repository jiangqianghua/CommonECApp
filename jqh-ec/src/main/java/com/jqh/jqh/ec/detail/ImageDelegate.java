package com.jqh.jqh.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ui.recycler.ItemType;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class ImageDelegate extends JqhDelegate {

    RecyclerView mRecyclerView = null;
    private static final String ARG_PICTURES = "ARG_PICTURES";

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_image_container);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImages();
    }


    private void initImages() {
        final ArrayList<String> pictures =
                getArguments().getStringArrayList(ARG_PICTURES);
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final int size;
        if (pictures != null) {
            size = pictures.size();
            for (int i = 0; i < size; i++) {
                final String imageUrl = pictures.get(i);
                final MultipleItemEntity entity = MultipleItemEntity
                        .builder()
                        .setItemType(ItemType.SINGLE_BIG_IMAGE)
                        .setField(MultipleFields.IMAGE_URL, imageUrl)
                        .build();
                entities.add(entity);
            }
            final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
            mRecyclerView.setAdapter(adapter);
        }
    }

    public static ImageDelegate create(ArrayList<String> pictures) {
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_PICTURES, pictures);
        final ImageDelegate delegate = new ImageDelegate();
        delegate.setArguments(args);
        return delegate;
    }
}
