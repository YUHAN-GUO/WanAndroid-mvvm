package com.gthink.wanandroid.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.gthink.wanandroid.BR;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.data.bean.NavigationArticleBean;
import com.gthink.wanandroid.databinding.ItemNavigationRightRlvBinding;

import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class NavigationRightRlvAdapter extends BaseQuickAdapter<NavigationArticleBean, NavigationRightRlvAdapter.MyHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private SetOnRrightChildTvOnClickListener onRrightChildTvOnClickListener;

    public void SetOnRrightChildTvOnClickListener(SetOnRrightChildTvOnClickListener onRrightChildTvOnClickListener) {
        this.onRrightChildTvOnClickListener = onRrightChildTvOnClickListener;
    }

    public interface SetOnRrightChildTvOnClickListener{
        void onClick(NavigationArticleBean.ArticlesBean bean);
    }

    public NavigationRightRlvAdapter(@Nullable List<NavigationArticleBean> data) {
        super(R.layout.item_navigation_right_rlv, data);
    }


    @Override
    protected void convert(MyHolder helper, NavigationArticleBean item) {
        TextView tv = helper.getView(R.id.item_navigation_left_title);
        ItemNavigationRightRlvBinding bind = (ItemNavigationRightRlvBinding) helper.getBind();
        bind.setRightDatas(item);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(mContext);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.STRETCH);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        NavigationRightChildRlvAdapter childRlvAdapter = new NavigationRightChildRlvAdapter(item.getArticles());
        bind.itemNavigationRightRlv.setLayoutManager(flexboxLayoutManager);
        bind.itemNavigationRightRlv.setAdapter(childRlvAdapter);
        childRlvAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<NavigationArticleBean.ArticlesBean> data = adapter.getData();
                onRrightChildTvOnClickListener.onClick(data.get(position));
            }
        });
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ItemNavigationRightRlvBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
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

        public ViewDataBinding getBind() {
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }

    class NavigationRightChildRlvAdapter extends BaseQuickAdapter<NavigationArticleBean.ArticlesBean, NavigationRightChildRlvAdapter.MyHolder> {

        public NavigationRightChildRlvAdapter( @Nullable List<NavigationArticleBean.ArticlesBean> data) {
            super(R.layout.item_navigation_right_child_rlv, data);
        }

        @Override
        protected void convert(MyHolder helper, NavigationArticleBean.ArticlesBean item) {
            ViewDataBinding bind = helper.getBind();
            bind.setVariable(BR.rightChildDatas,item);

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

            public ViewDataBinding getBind() {
                return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
            }
        }
    }
}
