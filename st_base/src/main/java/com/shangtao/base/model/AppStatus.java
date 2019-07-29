package com.shangtao.base.model;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.shangtao.base.model.activity.ActivityManager;
import com.shangtao.base.model.location.LocationManager;


/**
 * 统一处理Activity的生命周期回调，isBackground判断应用在前台还是后台
 */
public class AppStatus implements Application.ActivityLifecycleCallbacks {

    private int mActivityCount = 0;
    public static boolean isBackground = true;// 应用是否在后台

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityManager.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mActivityCount++;
        isBackground = false;
        LocationManager.getInstance().startLocation();
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mActivityCount--;
        if (mActivityCount == 0) {
            isBackground = true;
            LocationManager.getInstance().stopLocation();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityManager.getInstance().removeActivity(activity);
    }
}
