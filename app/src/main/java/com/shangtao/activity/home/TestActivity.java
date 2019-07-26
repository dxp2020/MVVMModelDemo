package com.shangtao.activity.home;

import android.view.View;

import androidx.databinding.Observable;

import com.blankj.utilcode.util.ToastUtils;
import com.shangtao.base.model.jump.Static;
import com.shangtao.base.view.BaseActivity;
import com.shangtao.test.BR;
import com.shangtao.test.R;
import com.shangtao.test.databinding.ActivityHomeBinding;

public class TestActivity extends BaseActivity<ActivityHomeBinding, TestActivityViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.clickObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                View view = viewModel.uc.clickObservable.get();
                if(view==null){
                    return;
                }
                switch (view.getId()){
                    case R.id.btn_mvp_fragment:
                        Static.jumpToFragment(mActivity,TestFragment.class,null);
                        break;
                    case R.id.btn_mvp_dialog:
                        ToastUtils.showShort("testing...");
                        break;
                }
            }
        });
    }
}
