package com.jqh.jqh.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class GoodsDetailDelegate extends JqhDelegate {

    public static  GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    //  水平动画进入
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
