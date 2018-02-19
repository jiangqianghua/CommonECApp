package com.jqh.jqh.deletegates;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;
/**
 * Created by jiangqianghua on 18/2/18.
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstanceState,View rootView);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null ;
        if(setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer)setLayout(),container,false);
        } else if(setLayout() instanceof View){
            rootView = (View)setLayout();
        }
        if(rootView != null){
            onBindView(savedInstanceState,rootView);
        }
       return rootView;
    }
}
