package com.gthink.wanandroid.data.retrofit;

import com.base.gyh.baselib.data.remote.retrofit.BaseDataService;

/**
 * Created by GUOYH on 2019/5/24.
 */
public class DataService {

    public static WanAndroidService getService(){
        return  (WanAndroidService) BaseDataService.getService(WanAndroidService.class);
    }
}
