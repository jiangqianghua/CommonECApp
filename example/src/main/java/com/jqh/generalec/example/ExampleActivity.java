package com.jqh.generalec.example;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jqh.jqh.activites.ProxyActivity;
import com.jqh.jqh.deletegates.JqhDelegate;
import com.jqh.jqh.ec.launcher.LauncherDelegate;
import com.jqh.jqh.ec.launcher.LauncherScrollDelegate;
import com.jqh.jqh.ec.launcher.sign.SignInDelegate;
import com.jqh.jqh.ec.launcher.sign.SignUpDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public JqhDelegate setRootDelegate() {
        return new SignInDelegate();
    }
}
