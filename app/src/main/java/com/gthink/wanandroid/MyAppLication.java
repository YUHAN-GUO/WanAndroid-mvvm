package com.gthink.wanandroid;

import com.base.gyh.baselib.base.BaseApplication;
import com.library.library_common.config.ModuleLifecycleConfig;

public class MyAppLication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        setsDebug(BuildConfig.DEBUG);
        // 初始化需要初始化的组件
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
    }
}
