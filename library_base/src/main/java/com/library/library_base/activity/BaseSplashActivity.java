package com.library.library_base.activity;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;

import com.base.gyh.baselib.utils.MmkvHelper;

public abstract class BaseSplashActivity extends BaseActivity {
    public Handler mHandler = new Handler();
    public Boolean isFrist = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFrist = MmkvHelper.getInstance().getFrist();
        mHandler.postDelayed(this::startTo,3000);
    }
    private void startTo(){
        if (isFrist){
            startToGuide();
        }else{
            startToMain();
        }
    }

    public abstract void startToMain();

    public abstract void startToGuide();

}
