package com.example.module_main.ui;

import android.os.Bundle;

import com.example.module_main.R;
import com.library.library_base.activity.BaseSplashActivity;
import com.library.library_common.adapter.ScreenAutoAdapter;

public class SplashActivity extends BaseSplashActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenAutoAdapter.match(this, 375.0f);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_splash);
    }

    //不是第一次进入
    @Override
    public void startToMain() {
        MainActivity.start(this);
    }

    //第一次进入
    @Override
    public void startToGuide() {
        GuideActivity.start(this);
    }

}
