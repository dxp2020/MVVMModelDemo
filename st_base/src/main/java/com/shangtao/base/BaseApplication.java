package com.shangtao.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.AppUtils;
import com.shangtao.base.crash.CaocConfig;
import com.shangtao.base.model.AppStatus;
import com.shangtao.base.model.language.LocaleManager;
import com.shangtao.base.model.setting.AppSettings;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public abstract class BaseApplication extends MultiDexApplication {
    private RefWatcher mRefWatcher;
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        AppSettings.init(this);

        String appFlavor = getAppFlavor();
        if(appFlavor!=null&&!appFlavor.contains("product")){
            initCrash();
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        if (AppUtils.isAppDebug()) {
            mRefWatcher = LeakCanary.install(this);
        }
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(getResources().getIdentifier( "ic_launcher" , "mipmap", getPackageName())) //错误图标
//                .restartActivity(SplashActivity.class) //重新启动后的activity     默认为启动页
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    @Override
    protected final void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public final void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }

    public abstract String getAppFlavor();

    public static Application getApplication() {
        return mApplication;
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
