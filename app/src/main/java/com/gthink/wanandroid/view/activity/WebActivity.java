package com.gthink.wanandroid.view.activity;

import com.base.gyh.baselib.base.BaseWebActivity;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class WebActivity extends BaseWebActivity {
    @Override
    public String getUrl() {
        String url = getIntent().getStringExtra("url");
        return url;
    }

}
