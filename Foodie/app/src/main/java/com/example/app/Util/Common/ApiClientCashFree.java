package com.example.app.Util.Common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientCashFree {

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit
                .Builder()
                .baseUrl(AppConstants.BASEURL_CASHFREE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
