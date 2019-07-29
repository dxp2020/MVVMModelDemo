package com.shangtao.base.viewModel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.shangtao.base.R;
import com.shangtao.base.binding.command.BindingCommand;

public class MessageDialogViewModel extends BaseViewModel {

    public MessageDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        if (view.getId() == R.id.tv_cancel||view.getId() == R.id.tv_confirm) {
            getLiveData().getClickEvent().call(view);
        }
    });

}
