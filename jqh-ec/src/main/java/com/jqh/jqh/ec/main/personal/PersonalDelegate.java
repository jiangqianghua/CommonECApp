package com.jqh.jqh.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jqh.jqh.bottom.BottomItemDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.main.index.IndexDelegate;
import com.jqh.jqh.ec.main.personal.address.AddressDelegate;
import com.jqh.jqh.ec.main.personal.list.ListAdapter;
import com.jqh.jqh.ec.main.personal.list.ListBean;
import com.jqh.jqh.ec.main.personal.list.ListItemType;
import com.jqh.jqh.ec.main.personal.list.order.OrderListDelegate;
import com.jqh.jqh.ec.main.personal.profile.UserProfileDelegate;
import com.jqh.jqh.ec.main.personal.settings.SettingsDelegate;
import com.jqh.jqh.ec.main.sort.SortDelegate;

import java.util.ArrayList;
import java.util.List;

public class PersonalDelegate extends BottomItemDelegate {

    private RecyclerView mRvSettings ;

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArsg = null ;
    private TextView allOrderListView ;
    private ImageView mAvaterView ;

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    private void startOrderListByType(){
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArsg);
        getParentDelegate().getSupportDelegate().start(delegate);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArsg = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        allOrderListView = (TextView)rootView.findViewById(R.id.tv_all_order);
        mRvSettings = (RecyclerView)rootView.findViewById(R.id.rv_personal_setting);
        mAvaterView = (ImageView)rootView.findViewById(R.id.img_user_avatar);

        final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setText("收货地址")
                .build();
        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new SettingsDelegate())
                .setText("系统设置")
                .build();
        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));

        initEvent();
    }

    private void initEvent(){
        allOrderListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mArsg.putString(ORDER_TYPE,"all");
                startOrderListByType();
            }
        });

        mAvaterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentDelegate().getSupportDelegate().start(new UserProfileDelegate());
            }
        });
    }
}
