package com.example.copengxiaolue.rxjavaretrofitdemo.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class TestApi {
    public  static final String BASE_URL = "http://retrofit.devwiki.net";
    private TestService service;

    private TestApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(TestService.class);
    }

    public static TestApi sInstance;

    public static TestApi getInstance() {
        if (sInstance == null) {
            sInstance = new TestApi();
        }

        return sInstance;
    }

    public TestService getService() {
        return service;
    }
}
