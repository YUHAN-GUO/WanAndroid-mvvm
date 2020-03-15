package com.gthink.wanandroid.view.fragment.amain;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gthink.wanandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySummaryFragment extends BaseFragment {


    public MySummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_summary, container, false);
    }

    @Override
    protected void loadData() {

    }

}
