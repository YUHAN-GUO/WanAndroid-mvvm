package com.gthink.wanandroid.viewmodule;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.View;

import com.base.gyh.baselib.annotation.Constant;
import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultTypeCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.INetErrorView;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gthink.wanandroid.data.bean.KnowledgeBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentKnowledgeBinding;
import com.gthink.wanandroid.view.activity.KnowledgeArticleActivity;
import com.gthink.wanandroid.view.adapter.KnowledgeRlvAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUOYH on 2019/5/30.
 */
public class KnowledgeViewModel implements OnRefreshLoadMoreListener {
    private FragmentKnowledgeBinding binding;
    private BaseFragment rxFragment;
    private List<KnowledgeBean> datas;
    private final KnowledgeRlvAdapter rlvAdapter;

    public KnowledgeViewModel(FragmentKnowledgeBinding binding, BaseFragment fragment) {
        this.binding = binding;
        this.rxFragment = fragment;
        binding.knowledgeRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        rlvAdapter = new KnowledgeRlvAdapter(datas);
        binding.knowledgeRlv.setAdapter(rlvAdapter);
        binding.knowledgeRefresh.setRefreshFooter(new ClassicsFooter(rxFragment.getContext()));
        binding.knowledgeRefresh.setEnableHeaderTranslationContent(true);
        binding.knowledgeRefresh.setOnRefreshLoadMoreListener(this);
        getKnowData(0);
        setListener();
    }

    private void setListener() {
        binding.knowledgeStateLayout.setOnErrorRetryClickListener(new INetErrorView.OnErrorRetryClickListener() {

            @Override
            public void onErrorRetryClicked() {
                getKnowData(0);
            }
        });
        rlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<KnowledgeBean> data = adapter.getData();
                ArrayList<KnowledgeBean.ChildrenBean> children = data.get(position).getChildren();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("tab",children);
                bundle.putString("title",data.get(position).getName());
                rxFragment.startActivity(KnowledgeArticleActivity.class,bundle);
            }
        });
    }
    private void stateShow(int type) {
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.knowledgeContent.setVisibility(View.VISIBLE);
        }else if (type==NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.knowledgeContent.setVisibility(View.GONE);
        }
        binding.knowledgeStateLayout.setContentState(type);
    }
    private void getKnowData(int ty) {
        if (ty!=1){
            stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
        }
        HttpUtils.obserableUtils(DataService.getService().getKnowledgeData(), rxFragment, new IBaseHttpResultTypeCallBack<List<KnowledgeBean>>() {
            @Override
            public void onSuccess(List<KnowledgeBean> data, int type) {
                rlvAdapter.setNewData(data);
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                if (ty ==1){
                    binding.knowledgeRefresh.finishRefresh();
                }else{
                }
            }

            @Override
            public void onError(Throwable e) {
                if (ty ==1){
                    binding.knowledgeRefresh.finishRefresh();
                }else{
                }
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR);

            }
        },Constant.OnLoadType.frist);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        binding.knowledgeRefresh.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getKnowData(1);
    }
}
