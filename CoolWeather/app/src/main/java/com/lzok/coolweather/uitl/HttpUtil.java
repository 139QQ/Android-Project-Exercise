package com.lzok.coolweather.uitl;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author lzok
 * @descriptive 获取全国省市县的数据
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }
}
