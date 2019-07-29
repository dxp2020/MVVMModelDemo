package com.shangtao.activity.home;

import android.os.Bundle;

import com.shangtao.base.BR;
import com.shangtao.base.activity.BaseFragment;
import com.shangtao.base.dialog.MessageDialog;
import com.shangtao.base.model.bean.MessageParam;
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

    public void registerLiveDataCallBack() {
        super.registerLiveDataCallBack();
        viewModel.getLiveData().getClickEvent().observe(this, view -> {
            if(view==null){
                return;
            }
            switch (view.getId()){
                case R.id.btn_show_dialog:
                    MessageParam param = new MessageParam();
                    param.setContent("是否获取天气?");
                    MessageDialog.newInstance(param).show(mActivity.getSupportFragmentManager(), result -> {
                        if(result){
                            viewModel.loadWeatherData("101310222");
                        }
                    });
                    break;
                case R.id.btn_show_full_dialog:
                    break;
            }
        });
    }

    public static TestDialogFragment newInstance(){
        return new TestDialogFragment();
    }

}
