package com.shangtao;

import com.shangtao.base.BaseApplication;
import com.shangtao.base.BuildConfig;
import com.shangtao.base.model.glide.GlideUtil;
import com.shangtao.base.model.location.LocationManager;
import com.shangtao.base.retrofit.RetrofitConfig;
import com.shangtao.base.model.AppStatus;
import com.shangtao.model.CommonValues;
import com.shangtao.retrofit.RetrofitCallback;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitConfig.getInstance().setCallBack(new RetrofitCallback());

        GlideUtil.setBaseUrl(CommonValues.API_SERVER_URL);

        //初始化定位
        if(LocationManager.getInstance().isExistLocationModule()){
            LocationManager.getInstance().initLocation(this,true);
        }
    }

    @Override
    public String getAppFlavor() {
        return BuildConfig.FLAVOR;
    }

}
