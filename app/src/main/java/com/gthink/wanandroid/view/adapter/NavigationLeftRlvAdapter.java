package com.gthink.wanandroid.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gthink.wanandroid.BR;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.data.bean.NavigationArticleBean;

import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class NavigationLeftRlvAdapter extends BaseQuickAdapter<NavigationArticleBean,NavigationLeftRlvAdapter.MyHolder> {

    private int select=0;

    public NavigationLeftRlvAdapter(@Nullable List<NavigationArticleBean> data) {
        super(R.layout.item_navigation_left_rlv, data);
    }
    public void setSelect(int select) {

//        notifyDataSetChanged();
        if (this.select!=select){
            notifyItemChanged(this.select);//刷新之前选中那个为 不选中的状态
        }
        this.select = select;
        notifyItemChanged(select);//刷新当前选择的那个 为选中状态

    }

    @Override
    protected void convert(MyHolder helper, NavigationArticleBean item) {
        TextView tv = helper.getView(R.id.item_navigation_left_title);
        ViewDataBinding bind = helper.getBind();
        bind.setVariable(BR.leftDatas,item);
        if (helper.getLayoutPosition()==select){
            tv.setTextColor(ContextCompat.getColor(mContext,R.color.black));
            tv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_bg_navigation_letf_tv));
        }else{
            tv.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            tv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_pg_navigation_letf_tv));
        }
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
