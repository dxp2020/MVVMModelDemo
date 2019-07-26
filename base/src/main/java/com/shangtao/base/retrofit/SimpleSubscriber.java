package com.shangtao.base.retrofit;


import com.shangtao.base.viewModel.BaseViewModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class SimpleSubscriber<T> implements Observer<T> {

    private BaseViewModel baseViewModel;
    private Disposable mDisposable;//相当于RxJava1.x中的Subscription,用于解除订阅

    public SimpleSubscriber() {
        super();
    }

    public SimpleSubscriber(BaseViewModel viewModel) {
        super();
        if (viewModel!=null) {
            baseViewModel = viewModel;
            baseViewModel.showDialog();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {
        if(baseViewModel!=null){
            baseViewModel.dismissDialog();
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if(baseViewModel!=null){
            baseViewModel.dismissDialog();
        }
        onFailure(e);
        onComplete();
    }

    /**
     * 解除订阅
     */
    public void unSubscription(){
        if (mDisposable!=null&&!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public abstract void onSuccess(T result);

    public void onFailure(Throwable e) {}
    @Override
    public void onComplete() {
        unSubscription();
    }

}
