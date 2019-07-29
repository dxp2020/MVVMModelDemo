package com.shangtao.base.binding.viewadapter.titlebar;


import android.annotation.SuppressLint;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.jakewharton.rxbinding2.view.RxView;
import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.base.view.CommTitleBar;

import java.util.concurrent.TimeUnit;

public class ViewAdapter {

    //防重复点击间隔(秒)
    public static final int CLICK_INTERVAL = 1;

    /**
     * 为CommonTitleBar绑定事件
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(CommTitleBar view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        setViewOnClickListener( view.getBackImage(),clickCommand,isThrottleFirst);
        setViewOnClickListener( view.getRightImage(),clickCommand,isThrottleFirst);
        setViewOnClickListener( view.getRightText(),clickCommand,isThrottleFirst);
    }

    @SuppressLint("CheckResult")
    private static void setViewOnClickListener(View view,final BindingCommand clickCommand, final boolean isThrottleFirst){
        if (isThrottleFirst) {
            RxView.clicks(view)
                    .subscribe(object -> {
                        if (clickCommand != null) {
                            clickCommand.execute();
                            clickCommand.execute(view);
                        }
                    });
        } else {
            RxView.clicks(view)
                    .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)//1秒钟内只允许点击1次
                    .subscribe(object -> {
                        if (clickCommand != null) {
                            clickCommand.execute();
                            clickCommand.execute(view);
                        }
                    });
        }
    }

}
