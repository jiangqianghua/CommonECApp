package com.jqh.jqh.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.jqh.bottom.BottomItemDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ec.main.sort.content.ContentDeleagte;
import com.jqh.jqh.ec.main.sort.list.VerticalListDelegate;

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container,listDelegate);
        // 设置右侧第一个显示分类1
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDeleagte.newInstance(1),false,false);

    }
}
