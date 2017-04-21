package com.example.copengxiaolue.rxjavaretrofitdemo.retrofit.filedownload;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class DownloadApi {
    private static final String BASE_URL = "http://retrofit.devwiki.net";
    private static DownloadApi sInstance;
    private IDownloadService service;

    private DownloadApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(IDownloadService.class);
    }

    public static DownloadApi getInstance() {
        if (sInstance == null) {
            sInstance = new DownloadApi();
        }

        return sInstance;
    }

    public IDownloadService getService() {
        return service;
    }
}
