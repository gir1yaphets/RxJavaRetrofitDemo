package com.example.copengxiaolue.rxjavaretrofitdemo.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.copengxiaolue.rxjavaretrofitdemo.MainActivity;
import com.example.copengxiaolue.rxjavaretrofitdemo.R;
import com.example.copengxiaolue.rxjavaretrofitdemo.RxJavaRetrofitApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class DbUtil {
    private static final String TAG = "DbUtil";
    public static final String DB_NAME_CITY = "city.db";
    private Context mContext = RxJavaRetrofitApplication.getRxApplication();

    private void importDbFile() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                long threadId = Thread.currentThread().getId();
                Log.d(TAG, "subscribe: threadId = " + threadId);
                e.onNext("DBファイル保存中");
                initAppData();
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        long threadId = Thread.currentThread().getId();
                        Log.d(TAG, "onSubscribe: threadId = " + threadId);
                    }

                    @Override
                    public void onNext(String s) {
                        long threadId = Thread.currentThread().getId();
                        Log.d(TAG, "onNext: threadId = " + threadId);
                        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(mContext, "保存完成", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void importCityData() {

        // 判断保持城市的数据库文件是否存在
        File file = new File(mContext.getDatabasePath(DB_NAME_CITY).getAbsolutePath());
        if (!file.exists()) {// 如果不存在，则导入数据库文件
            //数据库文件
            File dbFile = mContext.getDatabasePath(DB_NAME_CITY);
            try {
                if (!dbFile.getParentFile().exists()) {
                    dbFile.getParentFile().mkdir();
                }
                if (!dbFile.exists()) {
                    dbFile.createNewFile();
                }
                //加载欲导入的数据库
                InputStream is = mContext.getResources().openRawResource(R.raw.city);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                fos.write(buffer);
                is.close();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String initAppData() {
        importCityData();
        Log.d(TAG, "importCityData end");
        return null;
    }
}
