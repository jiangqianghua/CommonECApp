package com.jqh.jqh.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.jqh.jqh.ec.R;
import com.jqh.jqh.ui.recycler.RgbValue;

/**
 * 改变toolbar透明样式，可以随着recycler变化而变化
 */
public class TransluncentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    // 顶部距离
    private int mDistanceY = 0 ;

    // 颜色变化速速
    private static final int SHOW_SPEED = 3 ;
    private final RgbValue REG_VALUE = RgbValue.create(255,124,2);
    // 一定要有构造方法
    public TransluncentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true ;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        mDistanceY += dy;
        final int targetHeight = child.getBottom();
        // 当滑动时，并且距离小于toolbar高度时候，调整渐变色
        if(mDistanceY > 0 && mDistanceY <= targetHeight){
            final float scale = (float)mDistanceY / targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int)alpha,REG_VALUE.red(),REG_VALUE.green(),REG_VALUE.blue()));
        }else if(mDistanceY > targetHeight){
            //  设置不透明
            child.setBackgroundColor(Color.rgb(REG_VALUE.red(),REG_VALUE.green(),REG_VALUE.blue()));
        }else if(mDistanceY < 0){
            mDistanceY = 0 ;
            child.setBackgroundColor(Color.argb((int)0,REG_VALUE.red(),REG_VALUE.green(),REG_VALUE.blue()));
        }
    }
}
