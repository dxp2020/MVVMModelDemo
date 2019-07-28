package com.shangtao.base.model.language;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;


import java.util.Locale;

public class LocaleManager {
    private static final String LANGUAGE_KEY = "language_key";

    public static Context setLocale(Context c) {
        return updateResources(c, getLanguageType(c));
    }

    //根据手机版本号进行设置语言环境
    public static Context setNewLocale(Context c,LanguageType language) {
        persistLanguage(c, language);
        return updateResources(c, language);
    }

    //SharedPreferences获取语言环境
    public static LanguageType getLanguageType(Context c) {
        return LanguageType.getLanguageType(getLanguage(c));
    }

    //SharedPreferences获取语言环境
    public static String getLanguage(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        return prefs.getString(LANGUAGE_KEY, getSystemLanguage());
    }

    //获取系统语言，如果非en、zh，默认为en
    private static String getSystemLanguage(){
        String language = Locale.getDefault().getLanguage();
        if("en".equals(language)||"zh".equals(language)){
            return language;
        }
        return "en";
    }

    //设置app语言
    public static void setLocalLanguage(Context c) {
        setNewLocale(c,LocaleManager.getLanguageType(c));
    }

    @SuppressLint("ApplySharedPref")
    private static void persistLanguage(Context c, LanguageType language) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language.getType()).commit();
    }

    private static Context updateResources(Context context, LanguageType language) {
        Locale locale = new Locale(language.getType());
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        //做版本兼容性判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            //点进去看方法详情
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        setApplicationLanguage(context,locale);
        return context;
    }

    /**
     * 我们都会在代码中调用context.getResource().getString()这句代码看起来没什么问题，
     * 但是你这个context要是用的是applicationContext那么问题就来了。
     * 你会发现当你切换语言后用这样方式设置的string没有改变，所以我们需要改动我们的代码。
     * 解决方法就是，在切换语言后把application的updateConfiguration也要更新了
     */
    public static void setApplicationLanguage(Context context,Locale locale) {
        if(context.getApplicationContext()==null){
            return;
        }
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
        }
        resources.updateConfiguration(config, dm);
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }

}
