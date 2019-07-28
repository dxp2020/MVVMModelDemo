package com.shangtao.retrofit;


import com.shangtao.model.MainModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiStores{

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxJava(@Path("cityId") String cityId);
}
