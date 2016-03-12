package com.example.administrator.networkbestpractice;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/3/12.
 *
 * 这个类似"GET"是希望从服务器那里得到数据
 *
 *
 *
 * 为啥进行第二次的修改呢???
 *   因为网络请求都是属于耗时事件,sendHttpRequest()方法中并没有开启线程
 *   当进行处理时会出现主线程别阻塞的情况
 */
public class HttpUri {

    public static void sendHttpRequest(final String address,final HttpBackListener listener) {

        //这里创建子线程,让子线程去执行耗时的工作,发送网络请求并获取数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    //这里使用回调的原因是在子线程返回的数据返回之前sendHttpRequest()方法早已结束,那么
                    //这时利用回调方法就可以解决了
                    if(listener!=null){
                        listener.onFinish(response.toString());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    if(listener!=null){
                        listener.onError(e);
                    }

                    //这个地方需要return的原因是如果try{}肯定执行,若是执行了一半,那么进入了catch{},那么不许要有返回值

                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }


                }


            }

        }).start();

    }
}
