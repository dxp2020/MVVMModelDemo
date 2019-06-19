package com.shangtao.base.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.mvvm.architecture.viewModel.MvvmViewModel;

public  class BaseViewModel  extends  MvvmViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }


}
