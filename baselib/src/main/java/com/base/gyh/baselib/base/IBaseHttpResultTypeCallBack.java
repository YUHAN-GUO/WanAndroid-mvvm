package com.base.gyh.baselib.base;

/**
 * Created by GUOYH on 2019/5/28.
 */
public interface IBaseHttpResultTypeCallBack<T>  {
    void onSuccess(T data,int type);
    void onError(Throwable e);
}
