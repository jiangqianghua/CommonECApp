package com.jqh.jqh.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.ui.recycler.MultipleViewHolder;
import com.jqh.jqh.ui.recycler.MutipleRecyclerAdapter;

import java.util.List;

public class AddressAdapter extends MutipleRecyclerAdapter {
    public AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()){
            case AddressItemType.ITEM_ADDRESS:
                final String name = item.getField(MultipleFields.NAME);
                final String phone = item.getField(AddressItemFields.PHONE);
                final String address = item.getField(AddressItemFields.ADDRESS);
                final boolean isDefault = item.getField(MultipleFields.TAG);
                final int id = item.getField(MultipleFields.ID);

                final AppCompatTextView nameText = helper.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = helper.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = helper.getView(R.id.tv_address_address);
                final AppCompatTextView deleteTextView = helper.getView(R.id.tv_address_delete);
                deleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RestClient.builder()
                                .url("http://127.0.0.1/deleteaddress")
                                .params("id", id)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(helper.getLayoutPosition());
                                    }
                                })
                                .build()
                                .post();
                    }
                });

                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);
                break;
        }
    }
}
