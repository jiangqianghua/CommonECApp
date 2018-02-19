package com.jqh.generalec.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jqh.jqh.activites.ProxyActivity;
import com.jqh.jqh.deletegates.JqhDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public JqhDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
