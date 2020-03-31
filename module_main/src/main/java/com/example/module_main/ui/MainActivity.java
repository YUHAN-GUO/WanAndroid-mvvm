package com.example.module_main.ui;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module_main.R;
import com.example.module_main.adapter.MainPageAdapter;
import com.example.module_main.databinding.MainActivityMainBinding;
import com.library.library_base.activity.MvvmBaseActivity;
import com.base.gyh.baselib.utils.MmkvHelper;
import com.library.library_base.viewmodel.IMvvmBaseViewModel;
import com.library.library_common.router.RouterPath;

import java.util.ArrayList;

import me.majiajie.pagerbottomtabstrip.NavigationController;

public class MainActivity extends MvvmBaseActivity<MainActivityMainBinding, IMvvmBaseViewModel> {


    private MainPageAdapter adapter;
    private NavigationController mNavigationController;
    private ArrayList fragments;

    public static void start(Context context) {
        MmkvHelper.getInstance().putFrist(false);
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        ScreenAutoAdapter.match(this, 375.0f);
        super.onCreate(savedInstanceState);
        initControllerView();
        initViewPager();
        initFragment();
    }



    private void initFragment() {
        fragments = new ArrayList<>();
        //通过ARouter 获取其他组件提供的fragment
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterPath.Home.PAGER_HOME).navigation();

        //        Fragment communityFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Community.PAGER_COMMUNITY).navigation();
//        Fragment moreFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.More.PAGER_MORE).navigation();
        Fragment userFragment = (Fragment) ARouter.getInstance().build(RouterPath.User.PAGER_USER).navigation();
        fragments.add(homeFragment);
//        fragments.add(communityFragment);
//        fragments.add(moreFragment);
        fragments.add(userFragment);
        adapter.setData(fragments);
    }

    private void initControllerView() {
         mNavigationController =  viewDataBinding.MainNavigationView.material()
                .addItem(R.drawable.main_home, "首页",
                        ContextCompat.getColor(MainActivity.this,R.color.main_bottom_check_color)
                       )
                .addItem(R.drawable.main_user, "我的",
                        ContextCompat.getColor(MainActivity.this,R.color.main_bottom_check_color))
                .setDefaultColor(ContextCompat.getColor(MainActivity.this,R.color.main_bottom_default_color))
                .enableAnimateLayoutChanges()
                .build();

    }
    private void initViewPager() {
        adapter = new MainPageAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        viewDataBinding.MainViewPager.setOffscreenPageLimit(1);
        viewDataBinding.MainViewPager.setAdapter(adapter);
        mNavigationController.setupWithViewPager(viewDataBinding.MainViewPager);
    }
    @Override
    protected IMvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void onRetryBtnClick() {

    }
}
