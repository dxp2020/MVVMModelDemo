package com.shangtao.activity;

import android.view.View;

import androidx.databinding.Observable;

import com.shangtao.activity.home.HomeViewModel;
import com.shangtao.base.view.BaseActivity;
import com.shangtao.test.BR;
import com.shangtao.test.R;
import com.shangtao.test.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> {

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
        super.initViewObservable();
        viewModel.uc.clickObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                View view = viewModel.uc.clickObservable.get();
                if(view==null){
                    return;
                }
                switch (view.getId()){
                    case R.id.btn_mvp_activity:

                        break;
                    case R.id.btn_mvp_fragment:

                        break;
                    case R.id.btn_mvp_dialog:

                        break;
                }
            }
        });

    }
}
