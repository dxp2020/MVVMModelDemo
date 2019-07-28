package com.shangtao.activity.home;

import com.shangtao.base.BR;
import com.shangtao.base.activity.BaseFragment;
import com.shangtao.test.R;
import com.shangtao.test.databinding.FragmentHomeBinding;

public class TestFragment extends BaseFragment<FragmentHomeBinding, TestFragmentViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public static TestFragment newInstance(){
        return new TestFragment();
    }
}
