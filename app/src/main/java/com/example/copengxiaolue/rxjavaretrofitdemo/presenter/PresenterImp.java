package com.example.copengxiaolue.rxjavaretrofitdemo.presenter;

import com.example.copengxiaolue.rxjavaretrofitdemo.bean.TestResult;
import com.example.copengxiaolue.rxjavaretrofitdemo.module.IModule;
import com.example.copengxiaolue.rxjavaretrofitdemo.module.ModuleImp;
import com.example.copengxiaolue.rxjavaretrofitdemo.retrofit.filedownload.DownloadApi;
import com.example.copengxiaolue.rxjavaretrofitdemo.retrofit.filedownload.IDownloadService;
import com.example.copengxiaolue.rxjavaretrofitdemo.view.IView;

import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class PresenterImp implements IPresenter {

    private IView mView;
    private IModule mModule;
    private IDownloadService mService;

    public PresenterImp(IView view) {
        this.mView = view;
        this.mModule = new ModuleImp();
        mService = DownloadApi.getInstance().getService();
    }

    @Override
    public void onGetFilePath() {
        mService.getDownloadFilePath()
                .map(new Function<TestResult<String>, String>() {
                    @Override
                    public String apply(@NonNull TestResult<String> testResult) throws Exception {
                        String data = testResult.getData();
                        return data;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        mView.onUpdateTextView(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void onStartDownload(String fileUrl) {
        mView.onDownloadStart();
        mService.doStartDownload(fileUrl)
                .map(new Function<ResponseBody, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull ResponseBody responseBody) throws Exception {
                        InputStream in = responseBody.byteStream();
                        boolean result = mModule.onFileSave(in);
                        return result;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean result) {
                        mView.onDownloadFinish(result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
