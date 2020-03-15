package com.gthink.wanandroid.view.activity;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.base.gyh.baselib.base.BaseActivity;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.data.bean.KnowledgeBean;
import com.gthink.wanandroid.databinding.ActivityKnowledgeArticleBinding;
import com.gthink.wanandroid.viewmodule.KnowledgeArticleViewModel;

import java.util.ArrayList;

public class KnowledgeArticleActivity extends BaseActivity {

    private ActivityKnowledgeArticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<KnowledgeBean.ChildrenBean> tab = getIntent().getParcelableArrayListExtra("tab");
        String title = getIntent().getStringExtra("title");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_knowledge_article);
        KnowledgeArticleViewModel viewModel = new KnowledgeArticleViewModel(binding, this,tab);
        binding.setViewModel(viewModel);
        binding.knowledgeArticleMytoolbar.setTitleText(title);
    }
}
