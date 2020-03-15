package com.gthink.wanandroid.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by GUOYH on 2019/5/31.
 */
public class KnowledgeArticleViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public KnowledgeArticleViewPagerAdapter(FragmentManager fm,  ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public KnowledgeArticleViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
