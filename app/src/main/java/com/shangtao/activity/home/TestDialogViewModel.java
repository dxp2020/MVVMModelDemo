package com.shangtao.activity.home;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.base.viewModel.BaseViewModel;
import com.shangtao.test.R;

public class TestDialogViewModel extends BaseViewModel {

    public ObservableField<String> weatherObservable = new ObservableField<>();

    public TestDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        if (view.getId() == R.id.btn_show_dialog||view.getId() == R.id.btn_show_full_dialog) {
        }
    });


}
