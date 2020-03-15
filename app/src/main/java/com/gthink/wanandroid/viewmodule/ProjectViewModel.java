package com.gthink.wanandroid.viewmodule;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.INetErrorView;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.gthink.wanandroid.data.bean.ProjectTreeBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentProjectsBinding;
import com.gthink.wanandroid.view.adapter.ProjectVpAdapter;
import com.gthink.wanandroid.view.fragment.ProjectArticleChildFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class ProjectViewModel {
    private FragmentProjectsBinding binding;
    private BaseFragment fragment;



    public ProjectViewModel(FragmentProjectsBinding binding, BaseFragment fragment) {
        this.binding = binding;
        this.fragment = fragment;
        getData();
        setListener();
    }

    private void setListener() {
        binding.projectStateLayout.setOnErrorRetryClickListener(new INetErrorView.OnErrorRetryClickListener() {
            @Override
            public void onErrorRetryClicked() {
                getData();

            }
        });
    }

    private void stateShow(int type) {
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.projectContentView.setVisibility(View.VISIBLE);
        }else if (type==NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.projectContentView.setVisibility(View.GONE);
        }
        binding.projectStateLayout.setContentState(type);
    }
    private void getData() {
        stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
        HttpUtils.obserableUtils(DataService.getService().getProjectsTree(), fragment, new IBaseHttpResultCallBack<List<ProjectTreeBean>>() {
            @Override
            public void onSuccess(List<ProjectTreeBean> data) {
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                initTab(data);
            }

            @Override
            public void onError(Throwable e) {
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR);

            }
        });
    }

    private void initTab(List<ProjectTreeBean> data) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            titles[i] = data.get(i).getName();
            ProjectArticleChildFragment childFragment = new ProjectArticleChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",data.get(i).getId());
            childFragment.setArguments(bundle);
            fragments.add(childFragment);
        }
        ProjectVpAdapter adapter = new ProjectVpAdapter(fragment.getChildFragmentManager(), fragments);
        binding.projectViewpager.setAdapter(adapter);
        binding.projectTablayout.setViewPager(binding.projectViewpager,titles);
//        binding.projectTablayout
    }
}
