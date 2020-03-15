package com.gthink.wanandroid.viewmodule;

import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;

import com.base.gyh.baselib.widgets.view.MyToolbar;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.app.AppConstant;
import com.gthink.wanandroid.databinding.ActivityLoginAndRegisterBinding;
import com.gthink.wanandroid.view.activity.LoginAndRegisterActivity;
import com.gthink.wanandroid.view.fragment.LoginFragment;
import com.gthink.wanandroid.view.fragment.RegisterFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * Created by GUOYH on 2019/6/5.
 */
public class LoginRegisterViewModel {
    private LoginAndRegisterActivity activity;
    private ActivityLoginAndRegisterBinding binding;

    public LoginRegisterViewModel(LoginAndRegisterActivity activity, ActivityLoginAndRegisterBinding binding) {
        this.activity = activity;
        this.binding = binding;
        init();
        setListener();
    }

    private void setListener() {
        binding.loginRegisterToolbar.setOnBreakOrMenuClickListener(new MyToolbar.OnBreakOrMenuClickListener() {
            @Override
            public void onClick(int type) {
                if (type==MyToolbar.BACK){
                    activity.finish();
                }
            }
        });
        LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_TOOLBAR_TITLE,int.class).observe(activity, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer type) {
                if (type==0){
                    binding.loginRegisterToolbar.setTitleText("登录");
                    activity.addFragment(LoginFragment.class,R.id.loginRegisterFragment);
                }else{
                    binding.loginRegisterToolbar.setTitleText("注册");
                    activity.addFragment(RegisterFragment.class,R.id.loginRegisterFragment);

                }
            }
        });
    }

    private void init() {
        activity.addFragment(LoginFragment.class,R.id.loginRegisterFragment);
        binding.loginRegisterToolbar.setTitleText("登录");

    }
}
