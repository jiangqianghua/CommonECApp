package com.jqh.jqh.ec.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jqh.jqh.app.AccountManager;
import com.jqh.jqh.app.IUserChecker;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.R;
import com.jqh.jqh.ui.launcher.ILauncherListener;
import com.jqh.jqh.ui.launcher.LauncherHolderCreator;
import com.jqh.jqh.ui.launcher.OnLauncherFinishTag;
import com.jqh.jqh.ui.launcher.ScrollLaucherTag;
import com.jqh.jqh.utils.storage.JqhPreference;

import java.util.ArrayList;

/**
 * Created by jiangqianghua on 18/3/8.
 */

public class LauncherScrollDelegate extends JqhDelegate implements OnItemClickListener {
    private ConvenientBanner<Integer> mConvenientBanner = null ;
    private ILauncherListener mILauncherListener ;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner.setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                    .setOnItemClickListener(this)
                    .setCanLoop(false);
    }
    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<Integer>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        if(position == INTEGERS.size() - 1){
            JqhPreference.setAppFlag(ScrollLaucherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //检查是否已经登录
            // 检查是否登录app
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if(mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void noNotSignIn() {
                    if(mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }

    }
}
