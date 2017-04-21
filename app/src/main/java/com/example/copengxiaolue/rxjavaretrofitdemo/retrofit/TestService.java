package com.example.copengxiaolue.rxjavaretrofitdemo.retrofit;

import com.example.copengxiaolue.rxjavaretrofitdemo.bean.TestResult;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public interface TestService {
    @GET("/param")
    Call<TestResult> getResult(@Query("id") String id);

    @GET("/param")
    Observable<TestResult> getResultRxJava(@Query("id") String id);
}
