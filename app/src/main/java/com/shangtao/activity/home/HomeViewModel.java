package com.shangtao.activity.home;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ToastUtils;
import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.base.viewModel.BaseViewModel;
import com.shangtao.test.R;

public class HomeViewModel extends BaseViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public ObservableField<View> clickObservable = new ObservableField<>();
    }

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        uc.clickObservable.set(view);
        switch (view.getId()){
            case R.id.btn_mvp_activity:
                ToastUtils.showShort(((TextView)view).getText().toString());
                break;
            case R.id.btn_mvp_fragment:
                ToastUtils.showShort(((TextView)view).getText().toString());
                break;
            case R.id.btn_mvp_dialog:
                ToastUtils.showShort(((TextView)view).getText().toString());
                break;
        }
    });

}
