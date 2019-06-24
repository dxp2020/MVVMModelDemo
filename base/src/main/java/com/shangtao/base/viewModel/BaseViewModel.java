package com.shangtao.base.viewModel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.mvvm.architecture.viewModel.MvvmViewModel;
import com.shangtao.base.model.livedata.SingleLiveEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public  class BaseViewModel extends  MvvmViewModel {
    private BaseLiveData uc;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //event bus 事件处理，必须重写
    @Subscribe
    public void onEventMainThread(Intent pIntent) {}

    public <T> void addSubscription(Observable<T> observable, io.reactivex.Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(getLifecycleProvider().bindToLifecycle())
                .subscribe(subscriber);
    }

    public BaseLiveData getLiveData() {
        if (uc == null) {
            uc = new BaseLiveData();
        }
        return uc;
    }

    public void showDialog() {
        showDialog("");
    }

    public void showDialog(String title) {
        uc.showDialogEvent.setValue(title);
    }

    public void dismissDialog() {
        uc.dismissDialogEvent.call();
    }

    public final class BaseLiveData extends SingleLiveEvent {
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        private SingleLiveEvent createLiveData(SingleLiveEvent liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

}
