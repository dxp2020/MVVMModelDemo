package com.shangtao.base.model.jump;




import com.shangtao.base.activity.BaseFragment;

import java.io.Serializable;

public class ActivityParams implements Serializable {
    private Class<? extends BaseFragment> mFragmentClazz;
    private IFragmentParams mFragmentParams;

    public ActivityParams(Class<? extends BaseFragment> mFragmentClazz, IFragmentParams mFragmentParams) {
        this.mFragmentClazz = mFragmentClazz;
        this.mFragmentParams = mFragmentParams;
    }

    public Class<? extends BaseFragment> getFragmentClazz() {
        return mFragmentClazz;
    }

    public IFragmentParams getFragmentParams() {
        return mFragmentParams;
    }

}