package com.gthink.wanandroid.viewmodule;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.INetErrorView;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gthink.wanandroid.data.bean.NavigationArticleBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentNavigationBinding;
import com.gthink.wanandroid.view.adapter.NavigationLeftRlvAdapter;
import com.gthink.wanandroid.view.adapter.NavigationRightRlvAdapter;

import java.util.List;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class NavigationViewModel {
    private FragmentNavigationBinding binding;
    private BaseFragment fragment;
    private NavigationLeftRlvAdapter leftRlvAdapter;
    private boolean mIsNeedScrollLeft;
    private boolean mIsNeedScrollRight;

    public NavigationViewModel(FragmentNavigationBinding binding, BaseFragment fragment) {
        this.binding = binding;
        this.fragment = fragment;
        getData();
        setListener();
    }

    private void setListener() {
        binding.navigationStateLayout.setOnErrorRetryClickListener(new INetErrorView.OnErrorRetryClickListener() {
            @Override
            public void onErrorRetryClicked() {
                getData();

            }
        });
    }

    private void stateShow(int type) {
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.navigationContent.setVisibility(View.VISIBLE);
        }else if (type==NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.navigationContent.setVisibility(View.GONE);
        }
        binding.navigationStateLayout.setContentState(type);
    }
    private void getData() {
        stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
        HttpUtils.obserableUtils(DataService.getService().getNavigationData(), fragment, new IBaseHttpResultCallBack<List<NavigationArticleBean>>() {
            @Override
            public void onSuccess(List<NavigationArticleBean> data) {
                initRlv(data);
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
            }

            @Override
            public void onError(Throwable e) {
                stateShow(NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR);

            }
        });
    }

    private void initRlv(List<NavigationArticleBean> data) {
        initLeftRlv(data);
        initRightRlv(data);
    }

    private void initRightRlv(List<NavigationArticleBean> data) {


        NavigationRightRlvAdapter rightRlvAdapter = new NavigationRightRlvAdapter(data);

        binding.navigationRightRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        binding.navigationRightRlv.setAdapter(rightRlvAdapter);

        binding.navigationRightRlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 只有当用户通过手势滑动右边的时候，才会让左边滚动
                if (newState == RecyclerView.SCROLL_STATE_IDLE ) {
                    if(mIsNeedScrollLeft){
                        mIsNeedScrollLeft = false;
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) binding.navigationRightRlv.getLayoutManager();
                        int position = linearLayoutManager.findFirstVisibleItemPosition();
                        View targetView = linearLayoutManager.findViewByPosition(position);

                        float y = targetView.getY();
                        int h = targetView.getHeight();

                        if(Math.abs(y) > h/2){
                            position++;
                        }
                        scrollLeftToPosition((LinearLayoutManager) binding.navigationLeftRlv.getLayoutManager(), position);
                    }

                } else if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    mIsNeedScrollLeft = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    private void initLeftRlv(List<NavigationArticleBean> data) {
        leftRlvAdapter = new NavigationLeftRlvAdapter(data);
        binding.navigationLeftRlv.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        binding.navigationLeftRlv.setAdapter(leftRlvAdapter);
        leftRlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leftRlvAdapter.setSelect(position);
                scrollRightToPosition(position);
            }
        });
    }

    private void scrollRightToPosition(int position){
        binding.navigationRightRlv.stopScroll();
        LinearLayoutManager manager = (LinearLayoutManager)binding.navigationRightRlv.getLayoutManager();
        int fist = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();
        if(position <  fist){
            binding.navigationRightRlv.smoothScrollToPosition(position);
        }else if(position < last){
            View rightTargetView = binding.navigationRightRlv.getLayoutManager().findViewByPosition(position);
            if(rightTargetView != null){
                float y = rightTargetView.getTop();
                if(y > 0){
                    binding.navigationRightRlv.smoothScrollBy(0, (int)y);
                }
            }
        }else{
            position = Math.min(position+1, leftRlvAdapter.getItemCount()-1);
            mIsNeedScrollRight = true;
            binding.navigationRightRlv.smoothScrollToPosition(position);
        }
    }

    private void scrollLeftToPosition(final LinearLayoutManager manager, final int position) {
        View targetView = manager.findViewByPosition(position);
        int fist = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();

        if (targetView == null) {
            binding.navigationLeftRlv.smoothScrollToPosition(position);
            binding.navigationLeftRlv.post(new Runnable() {
                @Override
                public void run() {
                    scrollLeftToPosition(manager, position);
                }
            });
            return;
        }


        int middlePosition = (last + fist) / 2;

        View middleView = manager.findViewByPosition(middlePosition);
        if (middleView == null) {
            return;
        }

        float middleViewY = middleView.getY();

        float targetViewY = targetView.getY();

        float distance = targetViewY - middleViewY;

        if (distance > 0) {
            binding.navigationLeftRlv.smoothScrollBy(0, (int) distance);

        }
        leftRlvAdapter.setSelect(position);
    }
}
