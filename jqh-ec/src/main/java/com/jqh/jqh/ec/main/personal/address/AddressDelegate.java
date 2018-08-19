package com.jqh.jqh.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;

import java.util.List;

public class AddressDelegate extends JqhDelegate implements ISuccess {

    private RecyclerView mRecyclerView ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_address);

        RestClient.builder()
                .url("http://127.0.0.1/address")
                .loader(getContext())
                .success(this)
                .build()
                .get();

    }

    @Override
    public void onSuccess(String response) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data = new AddressDataConverter().setJsonData(response).convert();
        AddressAdapter adapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
