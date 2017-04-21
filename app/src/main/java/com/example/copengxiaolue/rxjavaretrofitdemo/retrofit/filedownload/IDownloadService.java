package com.example.copengxiaolue.rxjavaretrofitdemo.retrofit.filedownload;

import com.example.copengxiaolue.rxjavaretrofitdemo.bean.TestResult;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public interface IDownloadService {
    @GET("/fileUrl")
    Observable<TestResult<String>> getDownloadFilePath();

    @Streaming
    @GET
    Observable<ResponseBody> doStartDownload(@Url String fileUrl);
}
