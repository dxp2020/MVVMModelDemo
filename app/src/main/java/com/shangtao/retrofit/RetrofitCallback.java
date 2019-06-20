package com.shangtao.retrofit;

import android.app.Dialog;
import android.content.Context;

import com.shangtao.base.dialog.LoadingDialog;
import com.shangtao.base.retrofit.RetrofitConfig;
import com.shangtao.model.CommonValues;

public class RetrofitCallback implements RetrofitConfig.CallBack {

    @Override
    public Dialog getLoadingDialog(Context context, String message, boolean cancelable) {
        return new LoadingDialog(context,message,cancelable);
    }

    @Override
    public String getBaseUrl() {
        return CommonValues.API_SERVER_URL;
    }
}
