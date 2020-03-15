package com.gthink.wanandroid.view.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.FragmentHomePage3Binding;
import com.gthink.wanandroid.viewmodule.HomePageViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {


    private FragmentHomePage3Binding bindView;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page3, container, false);
        //得到当前界面的装饰视图
        bindView = FragmentHomePage3Binding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        PermissUtils.cameraPermiss();

        initData();

    }

    private void initData() {
        HomePageViewModel viewModel = new HomePageViewModel(bindView, this);
        bindView.setViewModel(viewModel);

    }

    @Override
    public void loadData() {

    }
}
