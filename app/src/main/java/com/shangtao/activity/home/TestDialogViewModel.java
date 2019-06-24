package com.shangtao.activity.home;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.test.R;
import com.shangtao.viewModel.BusinesstViewModel;

public class TestDialogViewModel extends BusinesstViewModel {

    public TestDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public ViewObservable uc = new ViewObservable();

    public class ViewObservable {
        ObservableField<View> clickObservable = new ObservableField<>();
    }

    public ObservableField<String> weatherObservable = new ObservableField<>();

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        if (view.getId() == R.id.btn_show_dialog) {

        }else if (view.getId() == R.id.btn_show_full_dialog) {

        }
    });


}
