package com.gthink.wanandroid.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.data.bean.KnowledgeArticleBean;

import java.util.List;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleChildRlvAdapter extends BaseQuickAdapter<KnowledgeArticleBean.DatasBean,KnowledgeArticleChildRlvAdapter.MyHolder> {
    public KnowledgeArticleChildRlvAdapter(@Nullable List<KnowledgeArticleBean.DatasBean> data) {
        super(R.layout.item_knowledge_child_fragment_rlv, data);
    }
    @Override
    protected void convert(MyHolder helper, KnowledgeArticleBean.DatasBean item) {
        ViewDataBinding bind = helper.getBind();
        bind.setVariable(BR.knowledgeDatasBean,item);
    }
    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }



    public class MyHolder extends BaseViewHolder {
        public MyHolder(View view) {
            super(view);
        }
        public ViewDataBinding getBind(){
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);

        }
    }
}
