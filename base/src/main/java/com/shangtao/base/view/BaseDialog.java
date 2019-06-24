package com.shangtao.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.mvvm.architecture.view.MvvmDialog;
import com.shangtao.base.BaseApplication;
import com.shangtao.base.dialog.LoadingDialog;
import com.shangtao.base.viewModel.BaseViewModel;
import com.squareup.leakcanary.RefWatcher;

import de.greenrobot.event.Subscribe;

public abstract class BaseDialog<V extends ViewDataBinding, VM extends BaseViewModel>  extends MvvmDialog<V,VM> {

    public boolean isFullScreen;//是否全屏
    private boolean isInitView;//是否初始化dialog的view
    private Bundle savedInstanceState;
    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(isFullScreen){
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        isInitView = mRootView==null;
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            //处理Activity被杀死重建
            handleRebuild(savedInstanceState);
        } else {
            init();
        }
    }

    /**
     * 初始化
     */
    public void init() {
        //此种情况，无需重新初始化，避免dismiss之后，重新初始化的问题
        if(!isInitView&&savedInstanceState==null){
            return;
        }
        initParam();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //私有的ViewModel与View的契约事件回调逻辑
        registorLiveDataCallBack();
        initData();
    }

    //注册ViewModel与View的契约UI回调事件
    private void registorLiveDataCallBack() {
        //加载对话框显示
        viewModel.getLiveData().getShowDialogEvent().observe(this, this::showDialog);
        //加载对话框消失
        viewModel.getLiveData().getDismissDialogEvent().observe(this, v -> dismissDialog());
    }

    public void showDialog(String title) {
        if (loadingDialog != null) {
            loadingDialog.show();
        } else {
            loadingDialog = new LoadingDialog(getContext(),title);
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

    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

}
