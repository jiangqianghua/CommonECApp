package com.jqh.jqh.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.jqh.jqh.R;
import com.jqh.jqh.deletegates.JqhDelegate;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public abstract class BottomItemDelegate extends JqhDelegate implements View.OnKeyListener{
    private long mExitTime = 0 ;
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        final View rootView = getView();
        if(rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestLayout();
            rootView.setOnKeyListener(this);
        }
    }

    // 判断是否退出
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis() - mExitTime) > EXIT_TIME){
                Toast.makeText(getContext(),"双击退出"+getString(R.string.app_name),Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else{
                _mActivity.finish();
                mExitTime = 0 ;
            }
            return true ;
        }
        return false;
    }
}
