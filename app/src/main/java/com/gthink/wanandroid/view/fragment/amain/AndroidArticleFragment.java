package com.gthink.wanandroid.view.fragment.amain;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.base.gyh.baselib.base.BaseFragment;
import com.gthink.wanandroid.R;
import com.gthink.wanandroid.databinding.FragmentAndroidArticleBinding;
import com.gthink.wanandroid.view.fragment.HomePageFragment;
import com.gthink.wanandroid.view.fragment.KnowledgeFragment;
import com.gthink.wanandroid.view.fragment.NavigationFragment;
import com.gthink.wanandroid.view.fragment.ProjectsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AndroidArticleFragment extends BaseFragment {


    private FragmentAndroidArticleBinding binding;

    public AndroidArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_android_article, container, false);
        binding = FragmentAndroidArticleBinding.bind(view);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addFragment(HomePageFragment.class,R.id.android_article_frameLayout);
       binding.androidArticleBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homgpage:
                        addFragment(HomePageFragment.class, R.id.android_article_frameLayout);
                        return true;
                    case R.id.projets:
                        addFragment(ProjectsFragment.class, R.id.android_article_frameLayout);

                        return true;
                    case R.id.knowledge:
                        addFragment(KnowledgeFragment.class, R.id.android_article_frameLayout);

                        return true;
                    case R.id.navigation:
                        addFragment(NavigationFragment.class, R.id.android_article_frameLayout);

                        return true;
                    default:
                        //nothing to do
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void loadData() {

    }

}
