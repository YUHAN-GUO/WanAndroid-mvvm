package com.library.library_base.base;

import android.app.Application;

import com.base.gyh.baselib.BuildConfig;
import com.base.gyh.baselib.base.IModuleInit;
import com.tencent.mmkv.MMKV;
import com.xuexiang.xui.XUI;

/**
 * Creat by GuoYh
 * com.library.library_base.base
 * 2020/3/30
 */
public class BaseModuleInit implements IModuleInit {
    private  Application application;
    @Override
    public boolean onInitAhead(Application application) {
        this.application = application;
        init();
        return false;
    }

    private void init() {
        initXUI();
        initMMKV();
    }

    private void initMMKV() {
        MMKV.initialize(application);
    }


    private void initXUI() {
        XUI.init(application); //初始化UI框架
        if (BuildConfig.DEBUG){
            XUI.debug(true);  //开启UI框架调试日志
        }
    }


    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
