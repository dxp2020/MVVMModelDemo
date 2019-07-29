package com.shangtao.activity.home;

import com.shangtao.base.activity.BaseActivity;
import com.shangtao.test.BR;
import com.shangtao.test.R;
import com.shangtao.test.databinding.ActivityHomeBinding;

public class TestActivity extends BaseActivity<ActivityHomeBinding, TestActivityViewModel> {

    public void registerLiveDataCallBack() {
        super.registerLiveDataCallBack();
        viewModel.getLiveData().getClickEvent().observe(this, view -> {
            if(view==null){
                return;
            }
            if (view.getId() == R.id.iv_back) {
                mActivity.finish();
            }
        });
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
