package com.shangtao.activity.home;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.blankj.utilcode.util.ToastUtils;
import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.base.retrofit.ApiClient;
import com.shangtao.base.retrofit.SimpleSubscriber;
import com.shangtao.base.viewModel.BaseViewModel;
import com.shangtao.model.MainModel;
import com.shangtao.retrofit.ApiStores;
import com.shangtao.test.R;

public class TestActivityViewModel extends BaseViewModel {
    private ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public TestActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public ObservableField<View> clickObservable = new ObservableField<>();
    }

    public ObservableField<String> weatherObservable = new ObservableField<>();

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        switch (view.getId()){
            case R.id.btn_mvp_activity:
                loadData("101310222");
                break;
            case R.id.btn_mvp_fragment:
                uc.clickObservable.set(view);
                break;
            case R.id.btn_mvp_dialog:
                ToastUtils.showShort(((TextView)view).getText().toString());
                break;
        }
    });


}
