package com.shangtao.base.activity;


import android.os.Bundle;

import com.shangtao.base.BR;
import com.shangtao.base.R;
import com.shangtao.base.model.bean.WebParam;
import com.shangtao.base.model.jump.IFragmentParams;

public class CommonWebFragment extends BaseWebFragment {

    private WebParam webParam;

    @Override
    protected void initParam() {
        assert getArguments() != null;
        webParam = (WebParam) getArguments().getSerializable("WebParam");
    }

    @Override
    protected void initData() {
        if(webParam.getUrl()!=null){
            webView.loadUrl(webParam.getUrl());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.webview_common;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    public static CommonWebFragment newInstance(IFragmentParams<WebParam> param){
        CommonWebFragment fragment= new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("WebParam",param.params);
        fragment.setArguments(bundle);
        return fragment;
    }

}
