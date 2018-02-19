package com.jqh.generalec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public class ExampleDelegate extends JqhDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
