package com.example.app.MyOrders.Common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

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
                .baseUrl(AppConstants.BASEURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
