package com.shangtao.activity.home;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.base.retrofit.SimpleSubscriber;
import com.shangtao.base.viewModel.BaseViewModel;
import com.shangtao.model.MainModel;
import com.shangtao.model.cache.AppCache;
import com.shangtao.test.R;

public class TestFragmentViewModel extends BaseViewModel {

    public TestFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> weatherObservable = new ObservableField<>();

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        if (view.getId() == R.id.btn_get) {
            loadWeatherData("101310222");
        }
    });

    private void loadWeatherData(String cityId) {
        addSubscription(AppCache.getApiStores().loadDataByRetrofitRxJava(cityId),
                new SimpleSubscriber<MainModel>(this) {
                    @Override
                    public void onSuccess(MainModel model) {
                        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
                        String showData = getApplication().getResources().getString(R.string.city) + weatherinfo.getCity()
                                + getApplication().getResources().getString(R.string.wd) + weatherinfo.getWD()
                                + getApplication().getResources().getString(R.string.ws) + weatherinfo.getWS()
                                + getApplication().getResources().getString(R.string.time) + weatherinfo.getTime();
                        weatherObservable.set(showData);
                    }
                    @Override
                    public void onFailure(Throwable e) {
                        weatherObservable.set("数据加载失败");
                    }
                });
    }

}
