package com.example.module_user.ui;


import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module_user.R;
import com.example.module_user.databinding.UserFragmentUserBinding;
import com.library.library_base.fragment.MvvmBaseFragment;
import com.library.library_base.utils.ToastUtil;
import com.library.library_base.viewmodel.IMvvmBaseViewModel;
import com.library.library_common.router.RouterPath;

/**
 * A simple {@link Fragment} subclass.
 */
@Route(path = RouterPath.User.PAGER_USER)
public class UserFragment extends MvvmBaseFragment<UserFragmentUserBinding , IMvvmBaseViewModel> {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_user;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewDataBinding.userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(getContext(),"123");
            }
        });
        meath3();
    }
    private void meath3() {
        Path path = new Path();
        path.moveTo(0,0);
        path.lineTo(300,300);
        path.quadTo(50,500,300,700);
        path.cubicTo(600,600,500,250,50,800);
        path.quadTo(500,0,0,0);
        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(viewDataBinding.userView, View.X, View.Y, path);
        mAnimator.setDuration(15000);
        mAnimator.start();
    }

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    protected IMvvmBaseViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onRetryBtnClick() {

    }

}
