package com.example.copengxiaolue.rxjavaretrofitdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.copengxiaolue.rxjavaretrofitdemo.bean.TestResult;
import com.example.copengxiaolue.rxjavaretrofitdemo.presenter.IPresenter;
import com.example.copengxiaolue.rxjavaretrofitdemo.presenter.PresenterImp;
import com.example.copengxiaolue.rxjavaretrofitdemo.retrofit.TestApi;
import com.example.copengxiaolue.rxjavaretrofitdemo.retrofit.TestService;
import com.example.copengxiaolue.rxjavaretrofitdemo.view.IView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IView {

    private Button mRetrofit;
    private Button mRxJava;
    private Button mGetPath;
    private Button mDownload;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    private String mFileUrl;

    private TestService mService;

    private IPresenter mPresenter;

    private int REQUEST_CODE = 100;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        mService = TestApi.getInstance().getService();

        mPresenter = new PresenterImp(this);
    }

    private void initView() {
        mRetrofit = (Button) findViewById(R.id.retrofit);
        mRxJava = (Button) findViewById(R.id.rxJava);
        mGetPath = (Button) findViewById(R.id.getPath);
        mDownload = (Button) findViewById(R.id.download);
        mTextView = (TextView) findViewById(R.id.filePath);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mRetrofit.setOnClickListener(this);
        mRxJava.setOnClickListener(this);
        mGetPath.setOnClickListener(this);
        mDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofit:
                doRetrofitRequest();
                break;
            case R.id.rxJava:
                doRxJavaRequest();
                break;
            case R.id.getPath:
                mPresenter.onGetFilePath();
                break;
            case R.id.download:
                if (checkPermission()) {
                    mPresenter.onStartDownload(mFileUrl);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDownloadStart() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUpdateTextView(String fileUrl) {
        mFileUrl = fileUrl;
        mTextView.setText(fileUrl);
    }

    @Override
    public void onDownloadFinish(boolean result) {
        mProgressBar.setVisibility(View.GONE);
        String text = result ? "下载成功" : "下载失败";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private boolean checkPermission() {
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_CODE);
                result = false;
            }
        }

        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mPresenter.onStartDownload(mFileUrl);
        }
    }

    private void doRxJavaRequest() {
        mService.getResultRxJava("abcd")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TestResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TestResult testResult) {
                        int code = testResult.getCode();
                        Toast.makeText(RxJavaRetrofitApplication.getRxApplication(), code + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void doRetrofitRequest() {
        Call<TestResult> call = mService.getResult("abc");
        call.enqueue(new Callback<TestResult>() {
            @Override
            public void onResponse(Call<TestResult> call, Response<TestResult> response) {
                TestResult result = response.body();
                Log.d(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<TestResult> call, Throwable t) {

            }
        });
    }
}
