package com.gthink.wanandroid.view.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.FragmentProjectArticleChildBinding;
import com.gthink.wanandroid.viewmodule.ProjectChildViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectArticleChildFragment extends BaseFragment {


    private int id;
    private FragmentProjectArticleChildBinding binding;

    public ProjectArticleChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_article_child, container, false);
        binding = FragmentProjectArticleChildBinding.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            id = bundle.getInt("id");
            binding.setProjectChildViewModel(new ProjectChildViewModel(binding,this,id));
        }
    }

    @Override
    protected void loadData() {

    }
}
