package com.shangtao.base.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.databinding.ViewDataBinding;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.mvvm.architecture.view.MvvmFragment;
import com.shangtao.base.BaseApplication;
import com.shangtao.base.R;
import com.shangtao.base.dialog.LoadingDialog;
import com.shangtao.base.model.jump.Static;
import com.shangtao.base.model.utils.ImmersionBarUtil;
import com.shangtao.base.model.utils.L;
import com.shangtao.base.model.utils.WebViewSettings;
import com.shangtao.base.view.MulaTitleBar;
import com.shangtao.base.viewModel.BaseViewModel;
import com.squareup.leakcanary.RefWatcher;

public abstract class BaseWebFragment<V extends ViewDataBinding, VM extends BaseViewModel>  extends MvvmFragment<V,VM> {

    public BaseActivity mActivity;
    private Bundle savedInstanceState;
    private LoadingDialog loadingDialog;
    public MulaTitleBar titleBar;
    public WebView webView;
    public ProgressBar progressBar;
    public FrameLayout flContainer;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState);//处理Activity被杀死重建
        }else{
            init();
        }
    }

    public void init() {
        mActivity = (BaseActivity)getActivity();

        //初始化沉侵式状态栏
        ImmersionBarUtil.initImmersionBar(mActivity,mRootView);

        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();

        //私有的ViewModel与View的契约事件回调逻辑
        registerLiveDataCallBack();

    }

    private void initWebView() {
        titleBar = mRootView.findViewById(R.id.mtb_title_bar);
        flContainer = mRootView.findViewById(R.id.fl_container);
        progressBar = mRootView.findViewById(R.id.web_bar);

        mRootView.postDelayed(() -> {
            webView = new WebView(mActivity);
            flContainer.addView(webView, 0);
            WebViewSettings.initSettings(webView);

            setWebViewClient(webView);
            setWebChromeClient(webView);
            setDownloadListener(webView);

            initParam();
            initData();

        }, 100L);
    }

    private void setWebViewClient(WebView webView) {
        webView.setWebViewClient(new WebViewClient() {
            @Deprecated
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                L.e("shouldOverrideUrlLoading-->"+url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                onUrlChange(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                BaseWebFragment.this.onPageFinished();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                BaseWebFragment.this.onReceivedError();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(R.string.ssl_certificate_error);
                builder.setMessage(R.string.do_you_want_to_continue_anyway);
                builder.setPositiveButton(mActivity.getString(R.string.continue_), (dialog, which) -> handler.proceed());
                builder.setNegativeButton(mActivity.getString(R.string.cancel), (dialog, which) -> handler.cancel());
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void setDownloadListener(WebView webView) {
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            if (mActivity != null && !mActivity.isFinishing()) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mActivity.startActivity(intent);
            }
        });
    }

    private void setWebChromeClient(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                setProgressBar(progress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                setWebTitle(title);
            }
        });
    }

    public void setProgressBar(int progress) {
        if (progressBar == null) {
            return;
        }
        if (progress == 100) {
            progressBar.setVisibility(View.GONE);
        } else {
            if (View.GONE == progressBar.getVisibility()) {
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setProgress(progress);
        }
    }

    public void setWebTitle(String title) {
        if (titleBar!=null&&!TextUtils.isEmpty(title)) {
            titleBar.setTitle(title);
        }
    }

    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    //注册ViewModel与View的契约UI回调事件
    private void registerLiveDataCallBack() {
        //加载对话框显示
        viewModel.getLiveData().getShowDialogEvent().observe(this, this::showDialog);
        //加载对话框消失
        viewModel.getLiveData().getDismissDialogEvent().observe(this, v -> dismissDialog());
        //处理页面跳转事件
        viewModel.getLiveData().getPageJumpEvent().observe(this, transaction -> Static.jumpToFragment(mActivity,transaction));
    }

    public void showDialog() {
        showDialog("");
    }

    public void showDialog(String title) {
        if (loadingDialog != null) {
            loadingDialog.show();
        } else {
            loadingDialog = new LoadingDialog(mActivity,title);
            loadingDialog.show();
        }
    }

    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        WebViewSettings.destoryWebView(webView);
        super.onDestroy();
        KeyboardUtils.fixSoftInputLeaks(mActivity);
        //监控fragment泄露
        if (AppUtils.isAppDebug()) {
            RefWatcher refWatcher = BaseApplication.getRefWatcher(mActivity);
            refWatcher.watch(this);
        }
    }

    protected void initParam() {}
    protected void initViewObservable() {}
    protected void initData() {}

    protected void onUrlChange(String url){}
    protected void onPageFinished(){}
    protected void onReceivedError(){}

    //处理页面重建
    public void handleRebuild(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        init();
    }

    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

}
