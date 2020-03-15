package com.gthink.wanandroid.viewmodule;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.base.gyh.baselib.annotation.Constant;
import com.base.gyh.baselib.annotation.LoadType;
import com.base.gyh.baselib.base.IBaseHttpResultTypeCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gthink.wanandroid.data.bean.ProjectArticleBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentProjectArticleChildBinding;
import com.gthink.wanandroid.view.activity.WebActivity;
import com.gthink.wanandroid.view.adapter.ProjectChildRlvAdapter;
import com.gthink.wanandroid.view.fragment.ProjectArticleChildFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class ProjectChildViewModel implements OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    private FragmentProjectArticleChildBinding binding;
    private ProjectArticleChildFragment fragment;
    private int id;
    private int page = 0;
    private ProjectChildRlvAdapter adapter;

    public ProjectChildViewModel(FragmentProjectArticleChildBinding binding, ProjectArticleChildFragment fragment, int id) {
        this.binding = binding;
        this.fragment = fragment;
        this.id = id;
        binding.projectChildSmart.setOnRefreshLoadMoreListener(this);
        getData(Constant.OnLoadType.frist);
    }
    private void stateShow(int type) {
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.projectChildContent.setVisibility(View.VISIBLE);
        }else if (type==NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.projectChildContent.setVisibility(View.GONE);
        }
        binding.projectChildStateLayout.setContentState(type);
    }
    private void getData(@LoadType int type) {
        switch (type) {
            case Constant.OnLoadType.frist:
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
                page = 0;
                break;
            case Constant.OnLoadType.loadMore:
                page++;
                break;
            case Constant.OnLoadType.refresh:
                page = 0;
                break;
            default:
                break;
        }
        HttpUtils.obserableUtils(DataService.getService().getProjectArticles(page, id), fragment, new IBaseHttpResultTypeCallBack<ProjectArticleBean>() {
            @Override
            public void onSuccess(ProjectArticleBean data, int type) {
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                initRlv(data,type);

            }

            @Override
            public void onError(Throwable e) {
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR);

            }
        },type);
    }

    private void initRlv(ProjectArticleBean data, int type) {
        if (adapter==null){
            adapter = new ProjectChildRlvAdapter(data.getDatas());
            binding.projectChildRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
            binding.projectChildRlv.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
        }
        switch (type) {
            case Constant.OnLoadType.frist:
                break;
            case Constant.OnLoadType.loadMore:
                if (data.getCurPage()>=data.getPageCount()){
                    binding.projectChildSmart.finishLoadMoreWithNoMoreData();
                }else{
                    binding.projectChildSmart.finishLoadMore();
                    adapter.addData(data.getDatas());
                }
                break;
            case Constant.OnLoadType.refresh:
                binding.projectChildSmart.finishRefresh();
                adapter.setNewData(data.getDatas());
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getData(Constant.OnLoadType.loadMore);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getData(Constant.OnLoadType.refresh);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        List<ProjectArticleBean.DatasBean> data = adapter.getData();
        bundle.putString("url",data.get(position).getLink());
        fragment.startActivity(WebActivity.class,bundle);
    }
}
