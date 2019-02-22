package com.kesar.jetpackgank.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ApiClient
 *
 * @author andy <br/>
 * create time: 2019/2/20 11:54
 */
public class ApiClient {
    public static ApiStores retrofit() {
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpClientBuilder.buildOkHttpClient())
                .build();

        return retrofit.create(ApiStores.class);
    }
}
