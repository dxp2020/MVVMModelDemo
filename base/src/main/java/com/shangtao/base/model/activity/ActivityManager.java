package com.shangtao.base.model.activity;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;


public class ActivityManager {
    private List<Activity> activityList = new LinkedList<>();
    private static ActivityManager instance;

    private ActivityManager() {
    }

    public static synchronized ActivityManager getInstance() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null) {
                    instance = new ActivityManager();
                }
            }
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    // 遍历所有Activity并finish
    public void finish() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

}
