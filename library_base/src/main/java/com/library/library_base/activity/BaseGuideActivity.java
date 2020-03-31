package com.library.library_base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.library.library_base.R;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.widget.banner.anim.select.ZoomInEnter;
import com.xuexiang.xui.widget.banner.transform.DepthTransformer;
import com.xuexiang.xui.widget.banner.transform.FadeSlideTransformer;
import com.xuexiang.xui.widget.banner.transform.FlowTransformer;
import com.xuexiang.xui.widget.banner.transform.RotateDownTransformer;
import com.xuexiang.xui.widget.banner.transform.RotateUpTransformer;
import com.xuexiang.xui.widget.banner.transform.ZoomOutSlideTransformer;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleGuideBanner;

import java.util.List;

/**
 * Creat by GuoYh
 * com.library.library_base.activity
 * 2020/3/22
 */
public abstract class BaseGuideActivity extends BaseActivity {
    private SimpleGuideBanner baseGuideBanner;
    private Class<? extends ViewPager.PageTransformer>[] transformers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        XUI.initTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_guide);
        initView();
        setGuideBanner();
    }

    private void initView() {
        transformers = new Class[]{
                  DepthTransformer.class,
                  FadeSlideTransformer.class,
                  FlowTransformer.class,
                  RotateDownTransformer.class,
                  RotateUpTransformer.class,
                  ZoomOutSlideTransformer.class,
          };
        baseGuideBanner = (SimpleGuideBanner) findViewById(R.id.base_guide_banner);

    }

    private void setGuideBanner() {
        baseGuideBanner
                .setIndicatorWidth(6)
                .setIndicatorHeight(6)
                .setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(transformers[0])
                .barPadding(0, 10, 0, 10)
                .setSource(getGuideSource())
                .startScroll();

        baseGuideBanner.setOnJumpClickListener(new SimpleGuideBanner.OnJumpClickListener() {
            @Override
            public void onJumpClick() {
                popToBack();
            }
        });
    }

    public abstract List<Object> getGuideSource() ;
    public abstract void popToBack() ;


}
