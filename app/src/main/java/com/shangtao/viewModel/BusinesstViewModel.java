package com.shangtao.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.shangtao.base.retrofit.ApiClient;
import com.shangtao.base.viewModel.BaseViewModel;
import com.shangtao.retrofit.ApiStores;

public class BusinesstViewModel extends BaseViewModel {
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public BusinesstViewModel(@NonNull Application application) {
        super(application);
    }

}
