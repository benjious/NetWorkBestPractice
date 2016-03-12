package com.example.administrator.networkbestpractice;

/**
 * Created by Administrator on 2016/3/12.
 */
public interface HttpBackListener {
    public abstract  void onFinish(String response);
    public abstract void  onError(Exception e);





}
