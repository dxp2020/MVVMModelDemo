package com.shangtao.base.model.jump;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.shangtao.base.activity.BaseFragment;
import com.shangtao.base.activity.CommonActivity;


public class Static {


    /**
     * 启动一个Activity并显示Fragment
     */
    public static void jumpToFragment(Context context, Class<? extends BaseFragment> fragmentClass) {
        jumpToFragment(context,fragmentClass,null);
    }

    /**
     * 启动一个Activity并显示Fragment
     */
    public static void jumpToFragment(Context context, Class<? extends BaseFragment> fragmentClass, IFragmentParams params) {
        ActivityParams activityParams = new ActivityParams(fragmentClass, params);
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra(CommonActivity.PARAMS, activityParams);
        context.startActivity(intent);
//        if (context instanceof Activity) {
//            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        }
    }

    /**
     * 启动一个Activity并显示Fragment
     */
    public static void jumpToFragmentForResult(Activity activity, Class<? extends BaseFragment> fragmentClass, int requestCode) {
        jumpToFragmentForResult(activity,fragmentClass,null,requestCode);
    }

    /**
     * 启动一个Activity并显示Fragment
     */
    public static void jumpToFragmentForResult(Activity activity, Class<? extends BaseFragment> fragmentClass, IFragmentParams mFragmentParams, int requestCode) {
        ActivityParams activityParams = new ActivityParams(fragmentClass, mFragmentParams);
        Intent intent = new Intent(activity, CommonActivity.class);
        intent.putExtra(CommonActivity.PARAMS, activityParams);
        activity.startActivityForResult(intent, requestCode);
//        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
