package com.kesar.jetpackgank.http;

import com.kesar.jetpackgank.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * HttpClientBuilder
 *
 * @author andy <br/>
 * create time: 2019/2/20 14:05
 */
public class HttpClientBuilder {
    public static OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
}
