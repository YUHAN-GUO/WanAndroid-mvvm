package com.example.module_home.ui;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_home.R;
import com.example.module_home.databinding.HomeFragmentHomeBinding;
import com.library.library_base.fragment.MvvmBaseFragment;

import com.library.library_base.viewmodel.IMvvmBaseViewModel;
import com.library.library_common.router.RouterPath;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = RouterPath.Home.PAGER_HOME)
public class HomeFragment extends MvvmBaseFragment<HomeFragmentHomeBinding, IMvvmBaseViewModel> {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment_home;
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    protected IMvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onRetryBtnClick() {

    }

}
