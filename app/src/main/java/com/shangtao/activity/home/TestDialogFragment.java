package com.shangtao.activity.home;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.Observable;

import com.shangtao.base.BR;
import com.shangtao.base.model.jump.Static;
import com.shangtao.base.view.BaseFragment;
import com.shangtao.test.R;
import com.shangtao.test.databinding.DialogHomeBinding;

public class TestDialogFragment extends BaseFragment<DialogHomeBinding, TestDialogViewModel> {

    private MessageDialog mMessageDialog;
    private FullScreenDialog mFullScreenDialog;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void handleRebuild(Bundle savedInstanceState) {
        super.handleRebuild(savedInstanceState);
        mMessageDialog = (MessageDialog) getFragmentManager().findFragmentByTag("MessageDialog");
        mFullScreenDialog = (FullScreenDialog) getFragmentManager().findFragmentByTag("FullScreenDialog");
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
                        if (mMessageDialog==null) {
                            mMessageDialog= new MessageDialog();
                            setMessageDialogListener(mMessageDialog);
                        }
                        mMessageDialog.show(getActivity().getSupportFragmentManager(), "MessageDialog");
                        break;
                    case R.id.btn_show_full_dialog:
                        if (mFullScreenDialog == null ) {
                            mFullScreenDialog = new FullScreenDialog();
                        }
                        mFullScreenDialog.show(getActivity().getSupportFragmentManager(), "FullScreenDialog");
                        break;
                }
            }
        });
    }

    public static TestDialogFragment newInstance(){
        return new TestDialogFragment();
    }

}
