package com.jqh.jqh.ui.launcher;

import android.support.v7.widget.AppCompatImageView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by jiangqianghua on 18/3/8.
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LaucherHolder> {

    @Override
    public LaucherHolder createHolder() {
        return new LaucherHolder();
    }
}
