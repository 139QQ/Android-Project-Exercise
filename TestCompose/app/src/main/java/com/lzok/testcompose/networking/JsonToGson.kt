package com.lzok.testcompose.networking

import HitokotoJSON
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class JsonToGson {
    private val network = Network()

    suspend fun convertJsonToGson(callback: (HitokotoJSON?) -> Unit) {
        withContext(Dispatchers.IO) {
            network.getUrl { url ->
                if (url != null) {
                    try {
                        val gson = Gson()
                        // 将 JSON 字符串转换为 HitokotoJSON 对象
                        val hitokotoJson = gson.fromJson(url, HitokotoJSON::class.java)
                        // 切换到主线程执行回调
                        GlobalScope.launch {
                            withContext(Dispatchers.Main) {
                                callback(hitokotoJson)
                            }
                        }

                    } catch (e: Exception) {
                        // 切换到主线程执行回调
                        GlobalScope.launch {
                            withContext(Dispatchers.Main) {
                                callback(null)
                            }
                        }
                    }
                } else {
                    // 切换到主线程执行回调
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            callback(null)
                        }
                    }
                }
            }
        }
    }

}
