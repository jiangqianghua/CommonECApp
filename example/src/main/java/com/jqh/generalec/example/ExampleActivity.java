package com.jqh.generalec.example;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jqh.jqh.activites.ProxyActivity;
import com.jqh.jqh.app.Jqh;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.launcher.LauncherDelegate;
import com.jqh.jqh.ec.launcher.LauncherScrollDelegate;
import com.jqh.jqh.ec.launcher.sign.ISignListener;
import com.jqh.jqh.ec.launcher.sign.SignInDelegate;
import com.jqh.jqh.ec.launcher.sign.SignUpDelegate;
import com.jqh.jqh.ec.main.EcBottomDelegate;
import com.jqh.jqh.ui.launcher.ILauncherListener;
import com.jqh.jqh.ui.launcher.OnLauncherFinishTag;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements ISignListener,
        ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        // 设置全局的activity到配置中
        Jqh.getConfigurator().widthActivity(this);
        StatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public JqhDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"sign in success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"sign up success",Toast.LENGTH_SHORT).show();
        // 可以做一些统计信息，记时等等
    }

    /**
     * 可以做轮播图的一些判断
     * @param tag
     */
    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                                                                                                                                                                                                                                                                                                                                                                                                             Toast.makeText(this,"用户登录拉",Toast.LENGTH_SHORT).show();
              //  startWithPop(new ExampleDelegate());
                startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"用户没登录",Toast.LENGTH_SHORT).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
