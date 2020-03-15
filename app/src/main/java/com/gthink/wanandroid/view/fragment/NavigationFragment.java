package com.gthink.wanandroid.view.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.FragmentNavigationBinding;
import com.gthink.wanandroid.viewmodule.NavigationViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BaseFragment {


    private FragmentNavigationBinding binding;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        binding = FragmentNavigationBinding.bind(view);
        return view;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setNavigationViewModel(new NavigationViewModel(binding,this));
    }
}
