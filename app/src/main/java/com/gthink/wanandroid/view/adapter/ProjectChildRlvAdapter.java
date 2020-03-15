package com.gthink.wanandroid.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gthink.wanandroid.BR;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.data.bean.ProjectArticleBean;

import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class ProjectChildRlvAdapter extends BaseQuickAdapter<ProjectArticleBean.DatasBean, ProjectChildRlvAdapter.MyHolder> {


    public ProjectChildRlvAdapter(@Nullable List<ProjectArticleBean.DatasBean> data) {
        super(R.layout.item_project_child_fragment_rlv, data);
    }

    @Override
    protected void convert(MyHolder helper, ProjectArticleBean.DatasBean item) {
        ViewDataBinding bind = helper.getBind();
        bind.setVariable(BR.projectDatsBean,item);
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
