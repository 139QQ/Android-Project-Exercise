package com.lzok.testcompose.model

import HitokotoJSON
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lzok.testcompose.networking.JsonToGson
import kotlinx.coroutines.launch

class HitokotoViewModel : ViewModel() {
    private val jsonToGson = JsonToGson()

    // 使用 LiveData 来观察 hitokotoJson 的变化
    private val _hitokotoJson = MutableLiveData<HitokotoJSON?>()
    val hitokotoJson: LiveData<HitokotoJSON?> get() = _hitokotoJson

    // 异步加载数据的方法
    fun loadHitokotoJson() {
        viewModelScope.launch {
            jsonToGson.convertJsonToGson { result ->
                _hitokotoJson.value = result
            }
        }
    }
}
