package com.shangtao.activity.home;

import android.view.View;

import androidx.databinding.Observable;

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
                    case R.id.btn_show_dialog:

                        break;
                    case R.id.btn_show_full_dialog:

                        break;
                }
            }
        });
    }

    public static TestDialogFragment newInstance(){
        return new TestDialogFragment();
    }

}
