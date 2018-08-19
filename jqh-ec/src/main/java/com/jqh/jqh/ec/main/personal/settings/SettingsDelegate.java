package com.jqh.jqh.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.main.personal.PersonalClickListener;
import com.jqh.jqh.ec.main.personal.about.AboutDelegate;
import com.jqh.jqh.ec.main.personal.address.AddressDelegate;
import com.jqh.jqh.ec.main.personal.list.ListAdapter;
import com.jqh.jqh.ec.main.personal.list.ListBean;
import com.jqh.jqh.ec.main.personal.list.ListItemType;

import java.util.ArrayList;
import java.util.List;

public class SettingsDelegate extends JqhDelegate {

    private RecyclerView mRecyclerView ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_settings);

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                })
                .setText("消息推送")
                .build();
        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new AboutDelegate())
                .setText("关于")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));

    }
}
