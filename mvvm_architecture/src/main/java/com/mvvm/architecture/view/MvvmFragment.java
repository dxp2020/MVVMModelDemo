package com.mvvm.architecture.view;

import androidx.databinding.ViewDataBinding;

import com.mvvm.architecture.viewModel.MvvmViewModel;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class MvvmFragment<V extends ViewDataBinding, VM extends MvvmViewModel> extends RxFragment {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(binding != null){
            binding.unbind();
        }
        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
    }

}
