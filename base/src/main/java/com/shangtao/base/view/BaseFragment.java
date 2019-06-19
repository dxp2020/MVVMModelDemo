package com.shangtao.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.mvvm.architecture.view.MvvmActivity;
import com.mvvm.architecture.view.MvvmFragment;
import com.mvvm.architecture.viewModel.MvvmViewModel;
import com.shangtao.base.BaseApplication;
import com.squareup.leakcanary.RefWatcher;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends MvvmViewModel>  extends MvvmFragment<V,VM> {
    public MvvmActivity mActivity;
    private Bundle savedInstanceState;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState);//处理Activity被杀死重建
        }else{
            init();
        }
    }

    public void init() {
        mActivity = (MvvmActivity)getActivity();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        initParam();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        initData();
    }

    /**
     * 反初始化
     */
    private void uninit() {
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反初始化
        uninit();
        //监控fragment泄露
        if (AppUtils.isAppDebug()) {
            try {
                RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
                if(refWatcher!=null){
                    refWatcher.watch(this);
                }
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
    }

    protected void initParam() {}
    protected void initViewObservable() {}
    protected void initData() {}

    //处理页面重建
    public void handleRebuild(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        init();
    }

    //返回键处理
    public boolean onBackPressed() {
        return false;
    }

    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    //event bus 事件处理，必须重写
    @Subscribe
    public void onEventMainThread(Intent pIntent) {}

}
