package com.shangtao.base.model.utils;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewSettings {

    /**
     * webview 通用设置
     * @param webView webView
     */
    public static void initSettings(WebView webView){
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString()+ "mulacar");
        webView.getSettings().setJavaScriptEnabled(true);// 开启JavaScript支持
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY); //设置滚动条样式
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webView.getSettings().setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webView.getSettings().setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webView.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
        //解决卡顿、滑动回弹问题
        webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);//开启硬件加速
        webView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示\
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    /**
     * 此方法在onDestory方法中调用，且必须在super.onDestory前调用
     *
     * 参考引用自https://www.jianshu.com/p/3e8f7dbb0dc7
     * @param webView webView
     */
    public static void destoryWebView(WebView webView){
        if(webView!=null){
            ((ViewGroup)webView.getParent()).removeView(webView);
            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);// 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            //清除缓存
            webView.clearCache(true);
            webView.clearFormData();
            //销毁
            webView.destroy();
        }
    }

}
