package com.gthink.wanandroid.viewmodule;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.base.gyh.baselib.base.BaseActivity;
import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.gthink.wanandroid.data.bean.KnowledgeBean;
import com.gthink.wanandroid.databinding.ActivityKnowledgeArticleBinding;
import com.gthink.wanandroid.view.adapter.KnowledgeArticleViewPagerAdapter;
import com.gthink.wanandroid.view.fragment.KnowledgeArticleChildFragment;

import java.util.ArrayList;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleViewModel {
    private ActivityKnowledgeArticleBinding binding;
    private BaseActivity activity;
    private ArrayList<KnowledgeBean.ChildrenBean> tab;
    private int cid;
    private int page = 0;
    public KnowledgeArticleViewModel(ActivityKnowledgeArticleBinding binding, BaseActivity activity,ArrayList<KnowledgeBean.ChildrenBean> tab) {
        this.binding = binding;
        this.activity = activity;
        this.tab = tab;
        initView();
    }

    private void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] titles = new String[tab.size()];
        for (int i = 0; i < tab.size(); i++) {
            titles[i] = tab.get(i).getName();
            KnowledgeArticleChildFragment childFragment = new KnowledgeArticleChildFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("cid",tab.get(i).getId());
            childFragment.setArguments(bundle);
            fragments.add(childFragment);
        }
        KnowledgeArticleViewPagerAdapter pagerAdapter = new KnowledgeArticleViewPagerAdapter(activity.getSupportFragmentManager(), fragments);
        binding.knowledgeArticleViewpager.setAdapter(pagerAdapter);
        binding.knowledgeArticleTablayout.setViewPager(binding.knowledgeArticleViewpager,titles);
        binding.knowledgeArticleMytoolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
            @Override
            public void onClick(int type) {
                if (type==MyToolbar.BACK){
                    activity.finish();
                }
            }
        });
    }

}
