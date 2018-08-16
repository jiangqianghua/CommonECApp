package com.jqh.jqh.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.jqh.bottom.BottomItemDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.MultipleFields;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.ui.refresh.RefreshHandler;

import java.util.ArrayList;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public class IndexDelegate extends BottomItemDelegate {
    RecyclerView mRecyclerView = null ;
    SwipeRefreshLayout mSwipeRefreshLayout =  null ;
    Toolbar mToolBar = null ;
    IconTextView mIconScan;
    AppCompatEditText mSearchView = null ;
    IconTextView mMessageView = null ;

    private RefreshHandler refreshHandler;
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_index);
        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.srl_index);
        mToolBar = (Toolbar)rootView.findViewById(R.id.tb_index);
        mIconScan = (IconTextView)rootView.findViewById(R.id.icon_index_scan);
        mSearchView = (AppCompatEditText)rootView.findViewById(R.id.et_search_view);
        mMessageView = (IconTextView)rootView.findViewById(R.id.icon_index_message);

        refreshHandler = new RefreshHandler(mSwipeRefreshLayout);

        RestClient.builder()
                .url("http://127.0.0.1/shops")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //  Log.d("RefreshHandler","firstPage:"+response);
                        final IndexDataConverter converter = new IndexDataConverter();
                        converter.setJsonData(response);
                        final ArrayList<MultipleItemEntity> list = converter.convert();
                        final String imageUrl = list.get(1).getField(MultipleFields.IMAGE_URL);
                        Toast.makeText(getContext(),imageUrl,Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();

        refreshHandler.firstPage("http://127.0.0.1/shops");
    }

    /**
     * 设置下拉刷新的球样式
     */
    private void initRefreshLayout(){
        // 设置转圈的几种颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,R.color.holo_orange_light,R.color.holo_red_light);
        // 设置转圈的位置，true是从大到小，120是开始位置，300的结束位置
        mSwipeRefreshLayout.setProgressViewOffset(true,120,300);
    }


}
