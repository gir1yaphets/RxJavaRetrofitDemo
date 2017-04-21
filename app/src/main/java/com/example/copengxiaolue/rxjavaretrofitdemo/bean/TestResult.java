package com.example.copengxiaolue.rxjavaretrofitdemo.bean;

/**
 * Created by copengxiaolue on 2017/04/19.
 */

public class TestResult<T> {
    private int code;
    private String desc;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
