package com.gthink.wanandroid.viewmodule;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.gthink.wanandroid.app.AppConstant;
import com.gthink.wanandroid.data.bean.LoginBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentLoginBinding;
import com.gthink.wanandroid.view.fragment.LoginFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

import me.linkaipeng.autosp.AppConfigSpSP;

/**
 * Created by GUOYH on 2019/6/5.
 */
public class LoginViewModel {
    private LoginFragment fragment;
    private FragmentLoginBinding binding;
    private String userName;
    private String userPaw;

    public LoginViewModel(LoginFragment fragment, FragmentLoginBinding binding) {
        this.fragment = fragment;
        this.binding = binding;
        setListener();
    }

    private void setListener() {
        binding.loginOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        binding.loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_TOOLBAR_TITLE,int.class).post(1);
            }
        });
    }
    private void submit() {
        // validate
        userName = binding.loginEdUserName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(fragment.getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        userPaw = binding.loginEdUserPaw.getText().toString().trim();
        if (TextUtils.isEmpty(userPaw)) {
            Toast.makeText(fragment.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        toLogin(userName, userPaw);


    }


    public void stateShow(int type) {
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.loginContent.setVisibility(View.VISIBLE);
        }else if (type == NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.loginContent.setVisibility(View.GONE);
        }
        binding.loginState.setContentState(type);
    }

    private void toLogin(String userName, String userPaw) {
        stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
        HttpUtils.obserableUtils(DataService.getService().login(userName, userPaw), fragment, new IBaseHttpResultCallBack<LoginBean>() {
            @Override
            public void onSuccess(LoginBean data) {
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                loginSuccess(data);
            }

            @Override
            public void onError(Throwable e) {
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                Toast.makeText(fragment.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginSuccess(LoginBean data) {
        Toast.makeText(fragment.getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        AppConfigSpSP.getInstance().setIsLogin(true);
        data.setPassword(userPaw);
        AppConfigSpSP.getInstance().setNickName(data.getUsername());
        LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_USER_MSG,LoginBean.class).post(data);
        fragment.closeActivity();
    }
}
