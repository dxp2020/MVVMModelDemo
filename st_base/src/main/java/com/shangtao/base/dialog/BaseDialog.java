/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.shangtao.base.dialog;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.AppUtils;
import com.mvvm.architecture.view.MvvmDialog;
import com.shangtao.base.BaseApplication;
import com.shangtao.base.activity.BaseActivity;
import com.shangtao.base.model.utils.FixMemLeak;
import com.shangtao.base.viewModel.BaseViewModel;
import com.squareup.leakcanary.RefWatcher;


public abstract class BaseDialog<V extends ViewDataBinding, VM extends BaseViewModel>  extends MvvmDialog<V,VM> {

    private BaseActivity mActivity;
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
        mActivity = (BaseActivity)getActivity();

        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();

        //私有的ViewModel与View的契约事件回调逻辑
        registerLiveDataCallBack();

        initParam();

        initData();
    }

    //注册ViewModel与View的契约UI回调事件
    public void registerLiveDataCallBack() {}

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public void show(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(TAG);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, TAG);
    }

    public void dismissDialog() {
        dismiss();
        removeFromActivity();
    }

    private void removeFromActivity(){
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
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

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    //处理页面重建
    public void handleRebuild(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        init();
    }

    protected void initParam() {}
    protected void initViewObservable() {}
    protected void initData() {}

}
