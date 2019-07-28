package com.shangtao.activity.home;

import com.shangtao.base.BR;
import com.shangtao.base.activity.BaseFragment;
import com.shangtao.test.R;
import com.shangtao.test.databinding.DialogHomeBinding;

public class TestDialogFragment extends BaseFragment<DialogHomeBinding, TestDialogViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }


    public static TestDialogFragment newInstance(){
        return new TestDialogFragment();
    }

}
