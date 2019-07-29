package com.shangtao.base.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.shangtao.base.R;
import com.shangtao.base.BR;
import com.shangtao.base.databinding.ActivityCommonBinding;
import com.shangtao.base.model.jump.ActivityParams;
import com.shangtao.base.model.jump.IFragmentParams;
import com.shangtao.base.viewModel.BaseViewModel;

import java.lang.reflect.InvocationTargetException;

public class CommonActivity extends BaseActivity<ActivityCommonBinding, BaseViewModel> {
    public static final String PARAMS = "params";
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_common;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected void initParam() {
        ActivityParams mParams = (ActivityParams) getIntent().getSerializableExtra(PARAMS);
        try {
            Fragment fragment;
            Class targetClass = mParams.getFragmentClazz();
            IFragmentParams mIFragmentParams = mParams.getFragmentParams();
            if (mIFragmentParams != null) {
                //通过fragment的静态方法newInstance(IFragmentParams)构造Fragment
                fragment = (Fragment) targetClass.getMethod("newInstance", IFragmentParams.class).invoke(null, mParams.getFragmentParams());
            } else {
                //通过fragment的静态方法newInstance()构造Fragment
                fragment = (Fragment) targetClass.getMethod("newInstance").invoke(null);
            }
            if (fragment == null) {
                finish();
                return;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment, targetClass.getSimpleName()).commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        // 横屏转竖屏
        int state = getResources().getConfiguration().orientation;
        if (state == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        // 在Fragment中拦截返回事件
        boolean isReturn = false;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BaseFragment) {
                if ( ((BaseFragment) fragment).onBackPressed() ) {
                    isReturn = true;
                }
            }
            if (fragment instanceof BaseWebFragment) {
                if ( ((BaseWebFragment) fragment).onBackPressed() ) {
                    isReturn = true;
                }
            }
        }
        if (isReturn) {
            return;
        }
        // 若未拦截交给Activity处理
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // super中回调Fragment的onActivityResult有问题，需自己回调Fragment的onActivityResult方法
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
            //如果有嵌套fragment也进行回调
            for (Fragment childFragment : fragment.getChildFragmentManager().getFragments()) {
                childFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // super中回调Fragment的onRequestPermissionsResult有问题，需自己回调Fragment的onRequestPermissionsResult方法
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            //如果有嵌套fragment也进行回调
            for (Fragment childFragment : fragment.getChildFragmentManager().getFragments()) {
                childFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

}
