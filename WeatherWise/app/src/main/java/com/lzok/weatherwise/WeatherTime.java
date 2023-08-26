package com.lzok.weatherwise;

import android.content.Context;
import android.location.Location;

import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.view.QWeather;

/**
 * @author Administrator
 */
public class WeatherTime {
    private QWeather qWeather;
    private Context weatherContext;
    private Lang lang;
    Location location;
    private com.qweather.sdk.bean.base.Unit unit;
    public void getWeahterNow(){
        Object listener = null;
    }


}
