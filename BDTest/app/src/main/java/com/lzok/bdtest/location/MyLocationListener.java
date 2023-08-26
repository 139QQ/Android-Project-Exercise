package com.lzok.bdtest.location;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyLocationListener implements BDLocationListener {
    private final String TAG = MyLocationListener.class.getSimpleName();

    //定位回调
    private LocationCallback callback;

    //需要定位的页面调用此方法进行接口回调处理
    public void setCallback(LocationCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (callback == null) {
            Log.e(TAG, "callback is Null!");
            return;
        }
        callback.onReceiveLocation(bdLocation);
    }
}
