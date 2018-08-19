package com.jqh.jqh.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.main.personal.list.ListAdapter;
import com.jqh.jqh.ec.main.personal.list.ListBean;
import com.jqh.jqh.ec.main.personal.list.ListItemType;
import com.jqh.jqh.ec.main.personal.settings.NameDeleagte;

import java.util.ArrayList;
import java.util.List;

public class UserProfileDelegate extends JqhDelegate {

    private RecyclerView mRecyclerView ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_user_profile);

        final ListBean image = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("http://i9.qhimg.com/t017d891ca365ef60b5.jpg")
                .build();

        final ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("姓名")
                .setDelegate(new NameDeleagte())
                .setValue("未设置姓名")
                .build();

        final ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("未设置性别")
                .build();

        final ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }
}
