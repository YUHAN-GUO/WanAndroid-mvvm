package com.base.gyh.baselib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;

/*
 * created by taofu on 2018/11/28
 **/
public abstract class BaseFragment extends RxFragment {

    private View view;
    private BaseActivity mBaseActivity;
    private FragmentManager mFragmentManager;
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentManager = getChildFragmentManager();
        if (activity instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) activity;

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = super.onCreateView(inflater, container, savedInstanceState);;
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        //加载数据
        prepareFetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.view = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData();
            isDataInitiated = true;
        }
    }
    /**
     * 懒加载
     */
    protected abstract void loadData();

    // 封装公共的添加Fragment的方法
    public void addFragment(Class<? extends BaseFragment> zClass,int layoutId){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        String tag =  zClass.getName();

        // 从 fragmentManager中查找这个fragment是否存在，
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);

        if(fragment != null){ // 如果存在就不用重新创建
            if(fragment.isAdded()){ // 如果 fragment 已经被添加
                if(fragment.isHidden()){ // 如果fragment 已经被添加，并且处于隐藏状态，那么显示
                    transaction.show(fragment); // 显示 fragment
                    hideOtherPage(transaction,fragment); // 隐藏其他fragment
                }
            }else{ // 如果 fragment存在，但是没有被添加到activity，那么执行下面添加，（这种一般发生在activity 横竖屏切换）
                transaction.add(layoutId, fragment);
                hideOtherPage(transaction, fragment);
            }
        }else{
            // 如果没有从fragmentManager 中通过tag 找到fragment,那么创建一个新的fragment 实例
            try {
                fragment = zClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if(fragment != null){
                transaction.add(layoutId, fragment,tag);
                hideOtherPage(transaction, fragment);
            }
        }

        transaction.commit();
    }

    // 隐藏除了将要显示的fragment 以外的其他所有fragment
    private void hideOtherPage(FragmentTransaction transaction, Fragment willShowFragment){

        List<Fragment> fragments =  mFragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != willShowFragment){
                transaction.hide(fragment);
            }
        }

    }

    public void startActivity(Class<? extends Activity> zClass,Bundle bundle){
        if (mBaseActivity!=null){
            mBaseActivity.startActivity(zClass,bundle);
        }
    }
    public void startActivity(Class<? extends Activity> zClass){
        if (mBaseActivity!=null){
            mBaseActivity.startActivity(zClass);
        }
    }
    public void startActivityForResult(Class<? extends Activity> zClass,int code){
        startActivityForResult(new Intent(getContext(),zClass),code);

    }

    public void closeActivity() {

        if (mBaseActivity != null) {
            mBaseActivity.finish();
        }
    }

}
