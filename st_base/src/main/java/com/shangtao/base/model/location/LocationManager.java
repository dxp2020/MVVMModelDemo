package com.shangtao.base.model.location;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;

import com.shangtao.base.delegate.LocationDelegate;
import com.shangtao.base.model.utils.L;

import java.lang.reflect.Constructor;

public class LocationManager {
    private static LocationManager mLocationManager;
    //是否存在定位模块
    private int isExistLocationModule = -1;//-1未检查 0不存在 1存在
    //是否初始化了定位模块
    private boolean isInitedLocation;
    //定位服务
    private LocationDelegate locationService;

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static synchronized LocationManager getInstance() {
        if (mLocationManager == null) {
            synchronized (LocationManager.class) {
                if (mLocationManager == null) {
                    mLocationManager = new LocationManager();
                }
            }
        }
        return mLocationManager;
    }

    public boolean isExistLocationModule() {
        try {
            if (isExistLocationModule==-1) {
                Class.forName("com.baidu.location.LocationClient");
                isExistLocationModule = 1;
            }
        } catch (ClassNotFoundException e) {
            isExistLocationModule = 0;
            L.e("《《《《《《《《没有定位模块》》》》》》》》");
        }
        return isExistLocationModule==1;
    }


    public void initLocation(Context context,boolean isGoogleMap) {
        if(isExistLocationModule()&&!isInitedLocation){
            try {
                Class clazz = Class.forName("com.mula.lbsloc.LocationService");
                //参数类型数组
                Class[] parameterTypes={Context.class,Boolean.class};
                //根据参数类型获取相应的构造函数
                Constructor constructor=clazz.getConstructor(parameterTypes);
                //参数数组
                Object[] parameters={context,isGoogleMap};
                //创建定位服务
                locationService = (LocationDelegate) constructor.newInstance(parameters);

                isInitedLocation = true;
            } catch (Exception e) {
                L.e(e);
            }
        }
    }

    /**
     * 开启定位
     */
    public synchronized void startLocation() {
        if (locationService!=null&&locationService.isObtainLocationTimeOut()) {
            if (locationService!=null) {
                locationService.stop();
            }
            mHandler.postDelayed(() -> {
                if (locationService!=null) {
                    locationService.start();
                }
                //检查定位状态
                checkLocationStatus();
            },500L);
        }
    }

    /**
     * 关闭定位
     */
    public void  stopLocation(){
        if (locationService!=null) {
            locationService.stop();
        }
    }

    /**
     * 获取定位
     */
    public Location getLocation(){
        if (locationService!=null) {
           return locationService.getLocation();
        }
        return null;
    }

    /**
     * 检查定位功能状态
     */
    private void checkLocationStatus(){
        mHandler.removeCallbacks(checkLocationRunnable);
        mHandler.postDelayed(checkLocationRunnable,locationService.getLocationTimeOutTimeMill());
    }

    private Runnable checkLocationRunnable = () -> {
        System.out.println("检查定位功能状态");
        startLocation();
    };

}
