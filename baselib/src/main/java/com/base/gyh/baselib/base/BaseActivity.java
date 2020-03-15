package com.base.gyh.baselib.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.view.KeyEvent;

import com.base.gyh.baselib.R;
import com.base.gyh.baselib.broadcast.NetBroadcastReceiver;
import com.base.gyh.baselib.utils.ActivityUtil;
import com.base.gyh.baselib.utils.NetworkUtil;
import com.base.gyh.baselib.utils.StatusBarUtilTextColor;
import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

/*
 * created by taofu on 2018/11/28
 **/
public abstract class BaseActivity extends RxAppCompatActivity implements NetBroadcastReceiver.NetChangeListener {

    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    private FragmentManager mFragmentManager;//添加fragment 事务管理器
    private boolean isView =false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);

        // 初始化netEvent
        netEvent = this;
        // 执行初始化方法
        NetBroadcastReceiver.registerReceiver(this);//网络变化广播注册
        ActivityUtil.getInstance().addActivity(this);
        mFragmentManager = getSupportFragmentManager();
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarUtil.setColorNoTranslucent(this,ContextCompat.getColor(this,R.color.color_blue));
//        StatusBarUtil.setTransparent(this);
        StatusBarUtilTextColor.setStatusBarMode(this,true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        NetBroadcastReceiver.unregisterReceiver(this);//网络变化
        super.onDestroy();
    }

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
            } catch (InstantiationException e) {
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
    private void hideOtherPage(FragmentTransaction transaction,Fragment willShowFragment){

        List<Fragment> fragments =  mFragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != willShowFragment){
                transaction.hide(fragment);
            }
        }

    }

    protected void startActivity(Class<? extends Activity> zClass){
        startActivity(new Intent(this,zClass));
    }
    protected void startActivity(Class<? extends Activity> zClass, Bundle bundle){
        startActivity(new Intent(this,zClass).putExtras(bundle));
    }
    protected void startActivityForResult(Class<? extends Activity> zClass,int coode){
        startActivityForResult(new Intent(this,zClass),coode);
    }
    protected void startActivityForResult(Class<? extends Activity> zClass, Bundle bundle,int coode){
        startActivityForResult(new Intent(this,zClass).putExtras(bundle),coode);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
        if (netWorkState){
//            Toast.makeText(this, "有网toast在BaseActivity", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(this, "没网toast在BaseActivity", Toast.LENGTH_SHORT).show();

        }
    }

    public void finishAll(){
        ActivityUtil.getInstance().exitSystem();
    }

    /**
     * 获取网络类型
     * @return
     */
    public int getNetType(){
        return NetworkUtil.getNetworkType(this);
    }

    /**
     * 判断是否有网
     * @return
     */
    public boolean getNetIs(){
        return NetworkUtil.isNetworkConnected(this);
    }
}
