package com.example.copengxiaolue.rxjavaretrofitdemo.view;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public interface IView {

    void onDownloadStart();

    void onUpdateTextView(String fileUrl);

    void onDownloadFinish(boolean result);
}
