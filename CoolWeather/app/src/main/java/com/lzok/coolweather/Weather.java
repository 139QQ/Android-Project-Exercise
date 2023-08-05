package com.lzok.coolweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzok.coolweather.gson.Forecast;
import com.lzok.coolweather.uitl.HttpUtil;
import com.lzok.coolweather.uitl.JsonUtility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Weather extends AppCompatActivity {
    private TextView titleCity,titleUpdateTime,degreeText,weatherInfoText,apiText,pm25Text,comfortText,
                    carWashText,sportText;
    private ScrollView weatherLayout;
    private LinearLayout forecastLayout;
    com.lzok.coolweather.gson.Weather weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherLayout = findViewById(R.id.weather_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText =findViewById(R.id.degree_text);
        weatherInfoText =findViewById(R.id.weather_info_text);
        forecastLayout =findViewById(R.id.forecast_layout);
        apiText = findViewById(R.id.api_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comfort_text);
        carWashText =findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString !=null){
//            有缓存时直接解析天气数据
             weather = JsonUtility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        }else {
//            无缓存
            String weatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }



    /**
     * 根据天气Id请求城市天气信息
     * @param weatherId 天气Id
     */
    private void requestWeather(String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" +
                weatherId+"&key =bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Weather.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                 weather = JsonUtility.handleWeatherResponse(responseText);
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         if (weather != null&&"ok".equals(weather.status)){
                             SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(Weather.this).edit();
                             editor.putString("weather",responseText    );
                             editor.apply();
                             showWeatherInfo(weather);
                         }else {
                             Toast.makeText(Weather.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
            }
        });

    }
    private void showWeatherInfo(com.lzok.coolweather.gson.Weather weather) {
        String cityName = weather.basic.cityName;
        String update = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(update);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecastList){

            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);

            TextView dateText = findViewById(R.id.date_text);
            TextView infoText = findViewById(R.id.info_text);
            TextView maxText = findViewById(R.id.max_text);
            TextView minText= findViewById(R.id.min_text);

            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);

        }
        if (weather.api !=null){
            apiText.setText(weather.api.ciy.api);
            pm25Text.setText(weather.api.ciy.pm25);

        }
        String comfort = "舒适度"+weather.suggestion.comfort.info;
        String carWash = "洗车指数" + weather.suggestion.carWash.info;
        String sport = "运动建议"+ weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
    }
}