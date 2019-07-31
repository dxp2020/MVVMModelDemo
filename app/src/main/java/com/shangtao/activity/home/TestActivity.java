package com.shangtao.activity.home;

import android.annotation.SuppressLint;
import android.view.KeyEvent;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shangtao.base.activity.BaseActivity;
import com.shangtao.base.dialog.MessageDialog;
import com.shangtao.base.model.bean.MessageParam;
import com.shangtao.test.BR;
import com.shangtao.test.R;
import com.shangtao.test.databinding.ActivityHomeBinding;

import io.reactivex.disposables.Disposable;

public class TestActivity extends BaseActivity<ActivityHomeBinding, TestActivityViewModel> {

    private Disposable subscribe;
    private long firstTime = 0;

    public void registerLiveDataCallBack() {
        super.registerLiveDataCallBack();
        viewModel.getLiveData().getClickEvent().observe(this, view -> {
            if(view==null){
                return;
            }
            if (view.getId() == R.id.iv_back) {
                mActivity.finish();

            }else if(view.getId() == R.id.btn_mvp_permission){
                requestPermissions();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        PermissionUtils.permission(PermissionConstants.STORAGE,
                PermissionConstants.LOCATION,
                PermissionConstants.PHONE,
                PermissionConstants.CAMERA).
                callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    ToastUtils.show("授权成功");
                }
                @Override
                public void onDenied() {
                    MessageDialog.newInstance(MessageParam
                            .newBuilder()
                            .hiddenTitle()
                            .hiddenCancel()
                            .setContent("授权失败，请进入设置页面开启权限")
                            .build())
                            .show(getSupportFragmentManager(), result -> {
                        if(result){
                            PermissionUtils.launchAppDetailsSettings();
                            mActivity.finish();
                        }
                    });
                }
        }).request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscribe!=null&&!subscribe.isDisposed()){
            subscribe.dispose();
            subscribe = null;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtils.show("再按一次退出程序");
                firstTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
