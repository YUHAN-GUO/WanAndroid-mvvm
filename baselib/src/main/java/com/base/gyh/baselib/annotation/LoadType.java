package com.base.gyh.baselib.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.base.gyh.baselib.annotation.Constant.OnLoadType.frist;
import static com.base.gyh.baselib.annotation.Constant.OnLoadType.loadMore;
import static com.base.gyh.baselib.annotation.Constant.OnLoadType.refresh;
import static com.base.gyh.baselib.annotation.LoadType.*;

/**
 * Created by GUOYH on 2019/5/28.
 */

@IntDef({FRIST,REFRESH,LOADMORE})
@Retention(RetentionPolicy.SOURCE)
public @interface LoadType {
    int FRIST = 0x10;
    int REFRESH = 0x20;
    int LOADMORE = 0x40;

}