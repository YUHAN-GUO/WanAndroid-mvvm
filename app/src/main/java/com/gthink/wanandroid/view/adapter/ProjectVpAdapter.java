package com.gthink.wanandroid.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class ProjectVpAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public ProjectVpAdapter(FragmentManager fm,  ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public ProjectVpAdapter(FragmentManager fm) {
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
