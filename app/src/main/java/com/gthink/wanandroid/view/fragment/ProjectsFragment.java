package com.gthink.wanandroid.view.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.FragmentProjectsBinding;
import com.gthink.wanandroid.viewmodule.ProjectViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends BaseFragment {


    private FragmentProjectsBinding binding;

    public ProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);
        binding = FragmentProjectsBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setProjectViewModel(new ProjectViewModel(binding,this));
    }

    @Override
    public void loadData() {

    }

}
