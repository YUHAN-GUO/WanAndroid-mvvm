package com.base.gyh.baselib.adapter.vpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by GUO_YH on 2019/6/16 19:53
 */
public class MyFragmentVPAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;

    public MyFragmentVPAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public MyFragmentVPAdapter(FragmentManager fm) {
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
