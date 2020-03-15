package com.gthink.wanandroid.viewmodule;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.remote.retrofit.HttpUtils;
import com.base.gyh.baselib.widgets.netstatae.NetStateLayout;
import com.gthink.wanandroid.app.AppConstant;
import com.gthink.wanandroid.data.bean.LoginBean;
import com.gthink.wanandroid.data.retrofit.DataService;
import com.gthink.wanandroid.databinding.FragmentRegisterBinding;
import com.gthink.wanandroid.view.fragment.RegisterFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * Created by GUOYH on 2019/6/5.
 */
public class RegisterViewModel  {
    private RegisterFragment fragment;
    private FragmentRegisterBinding binding;

    public RegisterViewModel(RegisterFragment fragment, FragmentRegisterBinding binding) {
        this.fragment = fragment;
        this.binding = binding;
        initView();
        setListener();
    }

    private void setListener() {
        binding.registerOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        binding.registerTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_TOOLBAR_TITLE,int.class).post(0);
            }
        });
        binding.registerEdUserPaw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<6){
                    binding.registerInputLayoutUserPaw.setErrorEnabled(true);
                    binding.registerInputLayoutUserPaw.setError("密码的长度不能小于6");
                }else{
                    binding.registerInputLayoutUserPaw.setErrorEnabled(false);
                }
            }
        });
        binding.registerEdUserRePaw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()<6){
                    binding.registerInputLayoutUserRePaw.setErrorEnabled(true);
                    binding.registerInputLayoutUserRePaw.setError("密码的长度不能小于6");
                }else{
                    binding.registerInputLayoutUserRePaw.setErrorEnabled(false);
                }
            }
        });
    }


    private void initView() {

    }
    private void submit() {
        // validate
        String userName = binding.registerEdUserName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(fragment.getContext(), "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        String userPaw = binding.registerEdUserPaw.getText().toString().trim();
        if (TextUtils.isEmpty(userPaw)) {
            Toast.makeText(fragment.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String userRePaw = binding.registerEdUserRePaw.getText().toString().trim();
        if (TextUtils.isEmpty(userRePaw)) {
            Toast.makeText(fragment.getContext(), "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPaw.length()<6||userRePaw.length()<6){
            Toast.makeText(fragment.getContext(), "密码长度不能少于6位", Toast.LENGTH_SHORT).show();

        }

        // TODO validate success, do something
        toRegister(userName,userPaw,userRePaw);

    }

    public void stateShow(int type){
        if (type==NetStateLayout.CONTENT_STATE_HIDE){
            binding.registerContent.setVisibility(View.VISIBLE);
        }else if (type==NetStateLayout.CONTENT_STATE_SHOW_NET_ERROR){
            binding.registerContent.setVisibility(View.GONE);
        }
        binding.registerState.setContentState(type);
    }

    private void toRegister(String userName, String userPaw, String userRePaw) {
        stateShow(NetStateLayout.CONTENT_STATE_SHOW_LOADING);
        HttpUtils.obserableUtils(DataService.getService().register(userName, userPaw, userRePaw), fragment, new IBaseHttpResultCallBack<LoginBean>() {
            @Override
            public void onSuccess(LoginBean data) {
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                Toast.makeText(fragment.getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                LiveEventBus.get().with(AppConstant.LiveEventBusKey.UP_TOOLBAR_TITLE,int.class).post(0);
            }

            @Override
            public void onError(Throwable e) {
                stateShow(NetStateLayout.CONTENT_STATE_HIDE);
                Toast.makeText(fragment.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
