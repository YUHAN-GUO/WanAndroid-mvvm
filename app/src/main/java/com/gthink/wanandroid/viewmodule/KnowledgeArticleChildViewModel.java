package com.gthink.wanandroid.viewmodule;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.base.gyh.baselib.annotation.Constant;
import com.base.gyh.baselib.annotation.LoadType;
import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultTypeCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.INetErrorView;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gthink.wanandroid.data.bean.KnowledgeArticleBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentKnowledgeArticleChildBinding;
import com.gthink.wanandroid.view.activity.WebActivity;
import com.gthink.wanandroid.view.adapter.KnowledgeArticleChildRlvAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleChildViewModel implements OnRefreshLoadMoreListener {
    private FragmentKnowledgeArticleChildBinding binding;
    private BaseFragment fragment;
    private int cid;
    private int page = 0;
    private KnowledgeArticleChildRlvAdapter adapter;

    public KnowledgeArticleChildViewModel(FragmentKnowledgeArticleChildBinding binding, BaseFragment fragment, int cid) {
        this.binding = binding;
        this.fragment = fragment;
        this.cid = cid;
        getData(Constant.OnLoadType.frist);
        setListener();
    }

    private void setListener() {
        binding.fragmentKnowledgeArticleChildSmart.setOnRefreshLoadMoreListener(this);

        binding.fragmentKnowledgeArticleStateLayout.setOnErrorRetryClickListener(new INetErrorView.OnErrorRetryClickListener() {
            @Override
            public void onErrorRetryClicked() {
                getData(Constant.OnLoadType.frist);

            }
        });
    }

    private void getData(@LoadType int type) {
        switch (type) {
            case Constant.OnLoadType.frist:
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
                page = 0;
                break;
            case Constant.OnLoadType.refresh:
                page = 0;
                break;
            case Constant.OnLoadType.loadMore:
                page++;
                break;
            default:
                break;
        }
        HttpUtils.obserableUtils(DataService.getService().getKnowledgeArticles(page, cid), fragment, new IBaseHttpResultTypeCallBack<KnowledgeArticleBean>() {
            @Override
            public void onSuccess(KnowledgeArticleBean data, int type) {
                initRlv(data, type);
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
            }

            @Override
            public void onError(Throwable e) {
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR);

            }
        }, type);
    }

    private void stateShow(int type) {
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.fragmentKnowledgeArticleChildSmart.setVisibility(View.VISIBLE);
        }else if (type==NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.fragmentKnowledgeArticleChildSmart.setVisibility(View.GONE);
        }
        binding.fragmentKnowledgeArticleStateLayout.setContentState(type);
    }

    private void initRlv(KnowledgeArticleBean data, @LoadType int type) {
        if (adapter == null) {
            adapter = new KnowledgeArticleChildRlvAdapter(data.getDatas());
            binding.fragmentKnowledgeArticleChildRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
            binding.fragmentKnowledgeArticleChildRlv.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    List<KnowledgeArticleBean.DatasBean> datas = adapter.getData();
                    String link = datas.get(position).getLink();
                    Bundle bundle = new Bundle();
                    bundle.putString("url",link);
                    fragment.startActivity(WebActivity.class,bundle);
                }
            });
        }
        switch (type) {
            case Constant.OnLoadType.frist:
                adapter.setNewData(data.getDatas());
                break;

            case Constant.OnLoadType.refresh:
                adapter.setNewData(data.getDatas());
                binding.fragmentKnowledgeArticleChildSmart.finishRefresh();
                break;

            case Constant.OnLoadType.loadMore:
                if (data.getCurPage()>=data.getPageCount()){
                    binding.fragmentKnowledgeArticleChildSmart.finishLoadMoreWithNoMoreData();
                }else{
                    binding.fragmentKnowledgeArticleChildSmart.finishLoadMore();
                }
                adapter.addData(data.getDatas());
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
}

