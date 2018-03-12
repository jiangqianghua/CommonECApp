package com.jqh.jqh.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ui.launcher.ScrollLaucherTag;
import com.jqh.jqh.utils.storage.JqhPreference;
import com.jqh.jqh.utils.timer.BaseTimerTask;
import com.jqh.jqh.utils.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

/**
 * Created by jiangqianghua on 18/3/7.
 */

public class LauncherDelegate extends JqhDelegate implements ITimerListener {

    private AppCompatTextView mTvTimer = null ;
    private Timer mTimer ;
    private int mCount = 5 ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mTvTimer = (AppCompatTextView)rootView.findViewById(R.id.tv_launcher_timer);
        mTvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimer != null) {
                    mTimer.cancel();
                    mTimer =null ;
                    checkIsShowScroll();
                }
            }
        });
        initTimer();
    }

    private void checkIsShowScroll(){
        if(!JqhPreference.getAppFlag(ScrollLaucherTag.HAS_FIRST_LAUNCHER_APP.name())){
            start(new LauncherScrollDelegate(),SINGLETASK);
        }else{
            // 检查是否登录app
        }
    }
    @Override
    public void onTimer() {

        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer != null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount-- ;
                    if(mCount < 0)
                        if(mTimer != null) {
                            mTimer.cancel();
                            mTimer =null ;
                            checkIsShowScroll();
                        }
                }
            }
        });
    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);
    }
}
