package com.shangtao.activity.home;

import com.shangtao.base.activity.BaseActivity;
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

}
