package com.shangtao.base.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.mvvm.architecture.viewModel.MvvmViewModel;
import com.shangtao.base.model.livedata.SingleLiveEvent;

public  class BaseViewModel extends  MvvmViewModel {
    private BaseLiveData uc;

    public BaseViewModel(@NonNull Application application) {
        super(application);
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
