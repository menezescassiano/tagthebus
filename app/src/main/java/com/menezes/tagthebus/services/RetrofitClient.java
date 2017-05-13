package com.menezes.tagthebus.services;

import com.menezes.tagthebus.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cassiano.menezes on 13/05/2017.
 */

public class RetrofitClient {

    private RetrofitModel model;

    public RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .build();
        model = retrofit.create(RetrofitModel.class);
    }

    public RetrofitModel getModel() {
        return  model;
    }
}
