package com.shangtao.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.mvvm.architecture.view.MvvmFragment;
import com.shangtao.base.BaseApplication;
import com.shangtao.base.R;
import com.shangtao.base.dialog.LoadingDialog;
import com.shangtao.base.model.jump.Static;
import com.shangtao.base.model.utils.FixMemLeak;
import com.shangtao.base.model.utils.ImmersionBarUtil;
import com.shangtao.base.viewModel.BaseViewModel;
import com.squareup.leakcanary.RefWatcher;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel>  extends MvvmFragment<V,VM> implements View.OnClickListener{

    public BaseActivity mActivity;
    private Bundle savedInstanceState;
    private LoadingDialog loadingDialog;

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
        mActivity = (BaseActivity)getActivity();

        //初始化沉侵式状态栏
        ImmersionBarUtil.initImmersionBar(mActivity,mRootView);

        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();

        //私有的ViewModel与View的契约事件回调逻辑
        registerLiveDataCallBack();

        //默认添加标题栏返回键监听
        View backView = mRootView.findViewById(R.id.iv_back);
        if (backView != null)
            backView.setOnClickListener(this);

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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            if (mActivity.getSupportFragmentManager().getBackStackEntryCount() == 0) {
                mActivity.finish();
            } else {
                mActivity.getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FixMemLeak.fixLeak(mActivity);
        //监控fragment泄露
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

    //返回键处理
    public boolean onBackPressed() {
        return false;
    }

    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

}
