package com.shangtao.base.model.setting;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shangtao.base.BaseApplication;
import com.shangtao.base.model.AppStatus;
import com.shangtao.base.model.language.LanguageType;
import com.shangtao.base.model.language.LocaleManager;
import com.shangtao.base.model.utils.L;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AppSettings {

    public static Boolean isDebug = null;

    /**
     * @param application Application 上下文
     */
    public static void init(Application application) {
        if (isDebug == null) {
            isDebug = application.getApplicationInfo() != null && (application.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        //监听Activity生命周期
        application.registerActivityLifecycleCallbacks(new AppStatus());
    }

    /**
     * 设置状态栏文字颜色
     * @param darkMode 状态栏文字颜色是否为黑色
     */
    public static boolean setStatusBarTextDark(Activity activity, boolean darkMode) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上版本
            if (darkMode) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            result = true;
        } else if (setMeiZuStatusBarDark(activity, darkMode)) {
            result = true;
        } else if (setXiaoMiStatusBarDark(activity, darkMode)) {
            result = true;
        }
        return result;
    }

    /**
     * 是否将魅族手机状态栏文字颜色更改为黑色，true为黑色，false为白色
     */
    private static boolean setMeiZuStatusBarDark(Activity activity, boolean darkmode) {
        boolean result = false;
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkmode) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
            result = true;
        } catch (Exception e) {
            L.e("setMeiZuStatusBarDark: failed");
        }
        return result;
    }

    /**
     * 是否将小米手机状态栏文字颜色更改为黑色，true为黑色，false为白色
     */
    private static boolean setXiaoMiStatusBarDark(Activity activity, boolean darkmode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            L.e("setXiaoMiStatusBarDark: failed");
        }
        return result;
    }

    /**
     * 隐藏输入法
     */
    public static void hideIme(Context context) {
        try {
            final View v = ((Activity) context).getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *  隐藏输入法
     */
    public static void hideIme(Context context, EditText pEditText) {
        try {
            if (pEditText != null && pEditText.getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pEditText.getWindowToken(), 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 显示输入法
     */
    public static void showIme(EditText editText) {
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

    public static String getLanguage() {
        return LocaleManager.getLanguage(BaseApplication.getApplication());
    }

    public static LanguageType getLanguageType() {
        return LocaleManager.getLanguageType(BaseApplication.getApplication());
    }

    /**
     * 针对 Android 27 的情况进行处理
     * 横竖屏设置了方向会崩溃的问题
     */
    public static boolean isTranslucentOrFloating(Activity activity) {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            TypedArray ta = activity.obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean)m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    /**
     * 修复横竖屏 crash 的问题
     */
    public static boolean fixOrientation(Activity activity){
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo)field.get(activity);
            o.screenOrientation = -1;
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
