package com.jqh.jqh.ec.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.net.RestClient;
import com.jqh.jqh.net.calback.ISuccess;
import com.jqh.jqh.ui.recycler.MultipleItemEntity;
import com.jqh.jqh.utils.storage.JqhPreference;

import java.util.ArrayList;
import java.util.List;

public class SearchDelegate extends JqhDelegate {

    RecyclerView mRecyclerView = null;
    AppCompatEditText mSearchEdit = null;

    private AppCompatTextView mTopSearch ;

    private IconTextView onTopSearchBack ;

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

         mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_search);
         mSearchEdit = (AppCompatEditText)rootView.findViewById(R.id.et_search_view);
        mTopSearch = (AppCompatTextView)rootView.findViewById(R.id.tv_top_search);
        onTopSearchBack = (IconTextView)rootView.findViewById(R.id.icon_top_search_back);
        mTopSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestClient.builder()
                        .url("index")
                        .loader(getContext())
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                final String searchItemText = mSearchEdit.getText().toString();
                                saveItem(searchItemText);
                                mSearchEdit.setText("");
                                //展示一些东西
                                //弹出一段话
                            }
                        })
                        .build()
                        .get();
            }
        });

        onTopSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportDelegate().pop();
            }
        });


        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);

        final List<MultipleItemEntity> data = new SearchDataConverter().convert();
        final SearchAdapter adapter = new SearchAdapter(data);
        mRecyclerView.setAdapter(adapter);
        // 设置分割线
        final DividerItemDecoration itemDecoration = new DividerItemDecoration();
        itemDecoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20, 20)
                        .color(Color.GRAY)
                        .build();
            }
        });

        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @SuppressWarnings("unchecked")
    private void saveItem(String item) {
        if (!StringUtils.isEmpty(item) && !StringUtils.isSpace(item)) {
            List<String> history;
            final String historyStr =
                    JqhPreference.getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if (StringUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(item);
            final String json = JSON.toJSONString(history);

            JqhPreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY, json);
        }
    }
}
