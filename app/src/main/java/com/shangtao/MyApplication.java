package com.shangtao;

import com.shangtao.base.BaseApplication;
import com.shangtao.base.retrofit.RetrofitConfig;
import com.shangtao.retrofit.RetrofitCallback;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitConfig.getInstance().setCallBack(new RetrofitCallback());
    }
}
