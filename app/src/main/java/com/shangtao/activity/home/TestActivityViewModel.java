package com.shangtao.activity.home;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.shangtao.base.activity.CommonWebFragment;
import com.shangtao.base.binding.command.BindingCommand;
import com.shangtao.base.model.bean.WebParam;
import com.shangtao.base.model.jump.IFragmentParams;
import com.shangtao.base.model.jump.Transaction;
import com.shangtao.base.retrofit.SimpleSubscriber;
import com.shangtao.base.viewModel.BaseViewModel;
import com.shangtao.model.MainModel;
import com.shangtao.model.cache.AppCache;
import com.shangtao.test.R;

public class TestActivityViewModel extends BaseViewModel {

    public TestActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> weatherObservable = new ObservableField<>();

    public BindingCommand<View> buttonClickCommand = new BindingCommand<>(view -> {
        if(view.getId() == R.id.iv_back){
            getLiveData().getClickEvent().call(view);

        } else if (view.getId() == R.id.btn_mvp_activity) {
            loadWeatherData("101310222");

        } else if (view.getId() == R.id.btn_mvp_fragment) {
            getLiveData().getPageJumpEvent().setValue(new Transaction(TestFragment.class));

        } else if (view.getId() == R.id.btn_mvp_dialog) {
            getLiveData().getPageJumpEvent().setValue(new Transaction(TestDialogFragment.class));

        } else if (view.getId() == R.id.btn_mvp_webview) {
            getLiveData().getPageJumpEvent().setValue(new Transaction(CommonWebFragment.class,new IFragmentParams<>(new WebParam("http://www.baidu.com"))));

        }
    });

    private void loadWeatherData(String cityId) {
        addSubscription(AppCache.getApiStores().loadDataByRetrofitRxJava(cityId),
                new SimpleSubscriber<MainModel>(this) {
                    @Override
                    public void onSuccess(MainModel model) {
                        MainModel.WeatherInfoBean weatherInfo = model.getWeatherInfo();
                        String showData = getApplication().getResources().getString(R.string.city) + weatherInfo.getCity()
                                + getApplication().getResources().getString(R.string.wd) + weatherInfo.getWD()
                                + getApplication().getResources().getString(R.string.ws) + weatherInfo.getWS()
                                + getApplication().getResources().getString(R.string.time) + weatherInfo.getTime();
                        weatherObservable.set(showData);
                    }
                    @Override
                    public void onFailure(Throwable e) {
                        weatherObservable.set("数据加载失败");
                    }
                });
    }

}
