package com.gthink.wanandroid.view.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.base.gyh.baselib.base.BaseActivity;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.ActivityLoginAndRegisterBinding;
import com.gthink.wanandroid.viewmodule.LoginRegisterViewModel;

public class LoginAndRegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginAndRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login_and_register);
        binding.setLoginRegisterModel(new LoginRegisterViewModel(this,binding));
    }

}
