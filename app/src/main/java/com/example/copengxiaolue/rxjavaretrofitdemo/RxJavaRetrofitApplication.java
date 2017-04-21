package com.example.copengxiaolue.rxjavaretrofitdemo;

import android.app.Application;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class RxJavaRetrofitApplication extends Application {
    private static RxJavaRetrofitApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static RxJavaRetrofitApplication getRxApplication() {
        return sInstance;
    }
}
