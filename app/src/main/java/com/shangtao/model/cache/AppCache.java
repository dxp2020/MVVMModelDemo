package com.shangtao.model.cache;

import com.shangtao.base.retrofit.ApiClient;
import com.shangtao.retrofit.ApiStores;

public class AppCache {

    private static ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public static ApiStores getApiStores() {
        return apiStores;
    }

}
