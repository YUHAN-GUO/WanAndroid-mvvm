package com.library.library_common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.gyh.baselib.BuildConfig;
import com.base.gyh.baselib.base.IModuleInit;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.library.library_common.adapter.ScreenAutoAdapter;

/**
 * Creat by GuoYh
 * com.library.library_common
 * 2020/3/30
 */
public class CommonModuleInit implements IModuleInit {
    private Application application;
    @Override
    public boolean onInitAhead(Application application) {
        this.application = application;
        initAroute();
        initScreen();
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }

    private void initScreen() {
        ScreenAutoAdapter.setup(application);
    }

    private void initAroute() {
        Logger.dd("-------");
        if (BuildConfig.DEBUG){
            Logger.dd("-------");
            ARouter.openLog(); // 开启日志
            ARouter.openDebug(); // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
        }
        ARouter.init(application);
    }
}
