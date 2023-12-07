package com.lzok.testcompose.networking

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

class Network {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(12, TimeUnit.SECONDS)
        .build()

    // 异步获取连接函数
    fun getUrl(callback: (String?) -> Unit) {
        // 获取请求
        val request = Request.Builder()
            .url("https://v1.hitokoto.cn/") // 注意添加协议
            .build()

        // 通过客户端异步发起请求
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val yaoqiu = response.body?.string()
                        callback(yaoqiu)
                    } catch (e: IOException) {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback(null)
            }
        })
    }

}