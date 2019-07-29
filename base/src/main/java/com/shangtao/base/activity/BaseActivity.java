package com.shangtao.base.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.mvvm.architecture.view.MvvmActivity;
import com.shangtao.base.BaseApplication;
import com.shangtao.base.dialog.LoadingDialog;
import com.shangtao.base.model.jump.IFragmentParams;
import com.shangtao.base.model.jump.Static;
import com.shangtao.base.model.language.LocaleManager;
import com.shangtao.base.model.setting.AppSettings;
import com.shangtao.base.model.utils.FixMemLeak;
import com.shangtao.base.model.utils.ImmersionBarUtil;
import com.shangtao.base.viewModel.BaseViewModel;
import com.squareup.leakcanary.RefWatcher;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel>  extends MvvmActivity<V,VM> {
    public MvvmActivity mActivity;
    private Bundle savedInstanceState;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //针对启动页设置android:windowIsTranslucent为true解决黑屏，然而导致8.0崩溃问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && AppSettings.isTranslucentOrFloating(this)) {
            AppSettings.fixOrientation(this);
        }
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState);//处理Activity被杀死重建
        }else{
            init();
        }
    }

    /**
     * 8.0以上语言适配
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LocaleManager.setLocale(context));
    }

    /**
     * 针对启动页设置android:windowIsTranslucent为true解决黑屏，然而导致8.0崩溃问题
     */
    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && AppSettings.isTranslucentOrFloating(this)) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    /**
     * 初始化
     */
    public void init() {
        mActivity = this;

        //全屏的情况下，隐藏导航栏
        if(ScreenUtils.isFullScreen(mActivity)&& BarUtils.isNavBarVisible(mActivity)) {
            BarUtils.setNavBarVisibility(mActivity,false);
        }

        //初始化沉侵式状态栏
        ImmersionBarUtil.initImmersionBar(this);

        //设置页面语言
        LocaleManager.setLocalLanguage(this);

        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();

        //私有的ViewModel与View的契约事件回调逻辑
        registerLiveDataCallBack();

        initParam();

        initData();
    }

    //注册ViewModel与View的契约UI回调事件
    public void registerLiveDataCallBack() {
        //加载对话框显示
        viewModel.getLiveData().getShowDialogEvent().observe(this, this::showDialog);
        //加载对话框消失
        viewModel.getLiveData().getDismissDialogEvent().observe(this, v -> dismissDialog());
        //处理页面跳转事件
        viewModel.getLiveData().getPageJumpEvent().observe(this, transaction -> Static.jumpToFragment(mActivity,transaction));
    }

    public void showDialog(String title) {
        if (loadingDialog != null) {
            loadingDialog.show();
        } else {
            loadingDialog = new LoadingDialog(mActivity,title);
            loadingDialog.show();
        }
    }

    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FixMemLeak.fixLeak(this);
        //监控内存泄露
        if (AppUtils.isAppDebug()) {
            RefWatcher refWatcher = BaseApplication.getRefWatcher(mActivity);
            refWatcher.watch(this);
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

    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

}
