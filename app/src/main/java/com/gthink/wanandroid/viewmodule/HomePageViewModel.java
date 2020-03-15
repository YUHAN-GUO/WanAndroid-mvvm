package com.gthink.wanandroid.viewmodule;

import android.Manifest;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableBoolean;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.base.gyh.baselib.annotation.Constant;
import com.base.gyh.baselib.annotation.LoadType;
import com.base.gyh.baselib.base.BaseFragment;
import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.base.IBaseHttpResultTypeCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.utils.PermissUtils;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.app.AppConstant;
import com.gthink.wanandroid.data.bean.ArticleDataBean;
import com.gthink.wanandroid.data.bean.BannerDataBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentHomePage3Binding;
import com.gthink.wanandroid.view.activity.WebActivity;
import com.gthink.wanandroid.view.adapter.HomePageRlvAdapter;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_EMPTY;
import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_HIDE;
import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_SHOW_LOADING;
import static com.base.gyh.baselib.widgets.netstatae.NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR;

/**
 * Created by GUOYH on 2019/5/25.
 */
public class HomePageViewModel extends ViewModel {
    public final ObservableBoolean mBannerIsSuccess = new ObservableBoolean(false);
    private BaseFragment rxFragment;
    private int page = 0;
    private FragmentHomePage3Binding binding;
    private HomePageRlvAdapter rlvAdapter;
    private String n;
    private boolean successAll = true;
    public HomePageViewModel(FragmentHomePage3Binding binding, BaseFragment rxFragment) {
        this.binding = binding;
        this.rxFragment = rxFragment;

        binding.homeRefresh.setRefreshFooter(new ClassicsFooter(rxFragment.getContext()));
        binding.homeRefresh.setEnableHeaderTranslationContent(true);
        binding.homePageState.setNetEmptyImage(ContextCompat.getDrawable(rxFragment.getContext(),R.drawable.navigation_backound));
        getHomeData();
        setListener();
    }

    private void setListener() {
        binding.homePageState.setOnEmptyAndErrorRetryClickListener(new NetStateLayout.OnEmptyAndErrorRetryClickListener() {
            @Override
            public void onErrorRetryClicked() {
                getHomeData();
                Logger.d("%s++++++++%s","guoyh","ErrorClick");
            }

            @Override
            public void onEmptyRetryClicked() {
//                getHomeData();
                Logger.d("%s++++++++%s","guoyh","EmptyClick123");

            }

        });
        binding.homeToolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //home页toolbar返回键的点击
                LiveEventBus.get().with(AppConstant.MAIN_FRAGMENT_BACK, int.class).post(0);
            }
        });

    }

    private void isCollect(boolean isChecked,ArticleDataBean.DatasBean item,int position) {
        if (isChecked){
            HttpUtils.obserableUtils(DataService.getService().collectArticle(item.getId()), rxFragment, new IBaseHttpResultCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {
                    rlvAdapter.setCollect(position, true);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(rxFragment.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    rlvAdapter.setCollect(position, false);
                }
            });
        }else{
            HttpUtils.obserableUtils(DataService.getService().unCollectArticle(item.getId()), rxFragment, new IBaseHttpResultCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {
                    rlvAdapter.setCollect(position, false);

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(rxFragment.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getHomeData() {
        stateShow(CONTENT_STATE_SHOW_LOADING);
        getArticel(Constant.OnLoadType.frist);
        getBanner();
    }

    private void getArticel(@LoadType int type) {
        switch (type) {
            case Constant.OnLoadType.frist:
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
        HttpUtils.obserableUtils(DataService.getService().getArticlesData(page), rxFragment, new IBaseHttpResultTypeCallBack<ArticleDataBean>() {
            @Override
            public void onSuccess(ArticleDataBean data, int type) {
                List<ArticleDataBean.DatasBean> datas = data.getDatas();
                Logger.d("%s+++++++++%s","guoyhArticleDataBean",datas.size()+"-----------");
                if (datas!=null&&datas.size()>0){
                    stateShow(CONTENT_STATE_HIDE);
                    initRlv(data, type);
                }else{
                    successAll=false;
                    stateShow(CONTENT_STATE_EMPTY);

                }

            }

            @Override
            public void onError(Throwable e) {
                stateShow(CONTENT_STATE_SHOW_NET_ERROR);
            }
        }, type);
    }

    private void getBanner() {
        HttpUtils.obserableUtils(DataService.getService().getHomeBannerData(), rxFragment, new IBaseHttpResultCallBack<List<BannerDataBean>>() {
            @Override
            public void onSuccess(List<BannerDataBean> data) {
                mBannerIsSuccess.set(true);
                stateShow(CONTENT_STATE_HIDE);
                Logger.d("%s+++++++++%s","guoyhBannerDataBean","-----------");
                initBanner(data);

            }

            @Override
            public void onError(Throwable e) {
                stateShow(CONTENT_STATE_SHOW_NET_ERROR);

            }
        });
    }

    private void stateShow(int type) {
        if (type==CONTENT_STATE_EMPTY||type==CONTENT_STATE_SHOW_NET_ERROR){
            binding.homeContentView.setVisibility(View.GONE);
        }else if(successAll){
            binding.homeContentView.setVisibility(View.VISIBLE);
        }

        binding.homePageState.setContentState(type);
    }

    private void initRlv(ArticleDataBean data, int type) {
        List<ArticleDataBean.DatasBean> datas = data.getDatas();
        if (rlvAdapter==null){
            rlvAdapter = new HomePageRlvAdapter(datas);
            binding.homeContent.setLayoutManager(new LinearLayoutManager(rxFragment.getContext()));
            binding.homeContent.setAdapter(rlvAdapter);

        }
        switch (type) {
            case Constant.OnLoadType.frist:
                rlvAdapter.setNewData(datas);
                break;
            case Constant.OnLoadType.refresh:
                binding.homeRefresh.finishRefresh();
                rlvAdapter.setNewData(datas);

                break;
            case Constant.OnLoadType.loadMore:
                binding.homeRefresh.finishLoadMore();
                rlvAdapter.addData(datas);
                break;
            default:
                break;
        }
        refrashAndLoadMoreListener();
    }

    private void refrashAndLoadMoreListener() {
        binding.homePageBanner.setDelegate(new BGABanner.Delegate<ImageView, BannerDataBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, @Nullable BannerDataBean model, int position) {
                String url = model.getUrl();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                rxFragment.startActivity(WebActivity.class, bundle);
            }

        });
        rlvAdapter.setOnCollectListener(new HomePageRlvAdapter.OnCollectListener() {
            @Override
            public void onCollectClick(ArticleDataBean.DatasBean item, int position, boolean isChecked) {
                isCollect(isChecked,item,position);
            }
        });
        rlvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PermissUtils.calendarPermiss(new IBaseHttpResultCallBack<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Logger.d("%s++++++++++%s","guoyh",s);
                        List<ArticleDataBean.DatasBean> data = adapter.getData();
                        String link = data.get(position).getLink();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", link);
                        rxFragment.startActivity(WebActivity.class, bundle);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("%s++++++++++%s","guoyh",e.getMessage());
                    }
                });
                SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                        //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                        new CheckRequestPermissionListener() {
                            @Override
                            public void onPermissionOk(Permission permission) {


//                        Utils.showMessage(view, permission.toString() + "\n is ok , you can do your operations");
                            }

                            @Override
                            public void onPermissionDenied(Permission permission) {
                                Logger.d("%s+++++++++++++%s", "guoyh", "失败");

//                        Utils.showMessage(view, permission.toString() + " \n is refused you can not do next things");
                            }
                        });

            }
        });
        binding.homeRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getArticel(Constant.OnLoadType.loadMore);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getArticel(Constant.OnLoadType.refresh);
            }
        });
    }

    private void initBanner(List<BannerDataBean> data) {
        binding.homePageBanner.setAdapter(new BGABanner.Adapter<ImageView, BannerDataBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, @Nullable BannerDataBean model, int position) {
                Glide.with(binding.getRoot()).load(model.getImagePath()).into(itemView);
            }
        });
        binding.homePageBanner.setData(data, null);
    }

}
