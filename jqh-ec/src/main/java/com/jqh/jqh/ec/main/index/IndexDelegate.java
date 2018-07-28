package com.jqh.jqh.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.jqh.bottom.BottomItemDelegate;
import com.jqh.jqh.ec.R;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public class IndexDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
