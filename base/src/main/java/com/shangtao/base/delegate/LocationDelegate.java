package com.shangtao.base.delegate;

import android.location.Location;

public interface LocationDelegate {
    void start();
    void stop();
    Location getLocation();
    long getLocationTimeOutTimeMill();
    boolean isObtainLocationTimeOut();
}
