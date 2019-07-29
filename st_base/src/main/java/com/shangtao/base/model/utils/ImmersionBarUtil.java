package com.shangtao.base.model.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.shangtao.base.R;
import com.shangtao.base.model.setting.AppSettings;

import java.lang.reflect.Field;

public class ImmersionBarUtil {

    /**
     * 初始化沉侵式状态栏
     * @param activity
     */
    public static void initImmersionBar(Activity activity) {
        if(isImmersionBarEnabled()){
            //设置透明状态栏
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
                } catch (Exception e) {}
            }
            AppSettings.setStatusBarTextDark(activity,true);
        }
    }

    /**
     * 初始化沉浸式
     */
    public static void initImmersionBar(Activity mActivity,View mRootView) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return;
        }
        if(mRootView.findViewById(R.id.mtb_title_bar)!=null){
            return;
        }
        View view = mRootView.findViewById(R.id.v_status_bar);
        if(view!=null){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight();
            view.setLayoutParams(params);
        }
        if(view!=null&&view.getBackground()==null){
            view.setBackgroundColor(Color.WHITE);
        }
    }

    /**
     * 是否可以使用沉浸式
     */
    private static boolean isImmersionBarEnabled() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

}
