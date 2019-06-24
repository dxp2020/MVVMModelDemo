package com.mvvm.architecture.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MvvmViewModel extends AndroidViewModel implements LifecycleObserver {

    public MvvmViewModel(@NonNull Application application) {
        super(application);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner, Lifecycle.Event event){}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(){}

    private LifecycleProvider lifecycle;

    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle;
    }

}
