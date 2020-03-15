package com.gthink.wanandroid.view.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.base.gyh.baselib.base.BaseActivity;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {

    private ActivitySplashBinding dataBinding;
    private boolean isTo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        dataBinding.splashJump.postDelayed(new Runnable() {
            @Override
            public void run() {
                startToMain();
            }
        },5000);
        dataBinding.splashJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToMain();
            }
        });
    }
    private void startToMain(){
        if (isTo){
            startActivity(MainActivity.class);
            isTo = false;
        }
    }
}
