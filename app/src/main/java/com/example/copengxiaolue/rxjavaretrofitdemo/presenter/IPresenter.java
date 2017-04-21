package com.example.copengxiaolue.rxjavaretrofitdemo.presenter;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public interface IPresenter {

    void onGetFilePath();

    void onStartDownload(String fileUrl);
}
