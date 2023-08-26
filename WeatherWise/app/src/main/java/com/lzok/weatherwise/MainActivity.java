package com.lzok.weatherwise;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;



import com.baidu.location.BDLocation;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import com.lzok.weatherwise.loaction.LocationCallback;
import com.lzok.weatherwise.loaction.WeatherLocationListener;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.io.IOException;
import java.io.InputStream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements LocationCallback {

    TextView temp,cclimatic,text_interval_temp,ds;
    Toolbar toolbar_district;
    List<Time> timeList = generateTimeList();
    List<Time> timeTemp = null;
//    连接
    private LocationClient mLocationClient = null;
    private WeatherLocationListener myListener = new WeatherLocationListener();
    LocationClientOption.FirstLocType FirstLocTypefirstLocType = null;
    /***
     * 定义一个观察者用来观察 onReceiveLocation()的数据
     */
    private MutableLiveData<BDLocation> locationLiveData = new MutableLiveData<>();
    String gps= null;
    SimpleDateFormat isoFormat;
    LocalDateTime date;
    // 创建温度数据集合
    List<Entry> temperatureEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = findViewById(R.id.text_temp);
        cclimatic = findViewById(R.id.text_climatic);
        text_interval_temp= findViewById(R.id.text_interval_temp);
        toolbar_district =findViewById(R.id.materialToolbar) ;
        ds = findViewById(R.id.ds);
        LocationClient.setAgreePrivacy(true);



        //   和风天气初始化
        HeConfig.init("HE2308121637531150", "d12188669df949bcac0967a6fd07b329");
//        百度地图初始化
        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
        SDKInitializer.initialize(getApplicationContext());
        LocationClient.setAgreePrivacy(true);
//        切换至免费版
        HeConfig.switchToDevService();
//        权限获取
        XXPermissions.with(this)
                .permission(Permission.Group.BLUETOOTH)
                .permission(Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .permission(Permission.CALL_PHONE)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        if (allGranted){
                            Toast.makeText(MainActivity.this, "部分全新获取成功", Toast.LENGTH_SHORT).show();
                            startLocation();
                        }
                        Toast.makeText(MainActivity.this, "所有权限获取成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        OnPermissionCallback.super.onDenied(permissions, doNotAskAgain);
                        if (doNotAskAgain){
                            Toast.makeText(MainActivity.this, "请手动获取", Toast.LENGTH_SHORT).show();
                            XXPermissions.startPermissionActivity(MainActivity.this,permissions);
                        }
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    }
                });
        RecyclerView recyclerView = findViewById(R.id.rec_seven_weather);

// 创建LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

// 创建适配器实例并传入数据列表
        SevenAdapter adapter = new SevenAdapter(timeList);

        recyclerView.setAdapter(adapter);
//        在这里观察
        locationLiveData.observe(this, bdLocation -> {




            //        北京实时天气
     QWeather.getWeatherNow(MainActivity.this,gps, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
        @Override
        public void onError(Throwable throwable) {
            Log.i(TAG, "getWeather onERROR:" + throwable);
        }

        @Override
        public void onSuccess(WeatherNowBean weatherBean) {
//                       在后台更新
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //                温度
                    temp.setText(weatherBean.getNow().getTemp() + "℃");
//                              天气

                    cclimatic.setText(weatherBean.getNow().getText());
                    text_interval_temp.setText(weatherBean.getNow().getTemp() + "/" + weatherBean.getNow().getFeelsLike() + "℃");
                }
            });

//                      24小时天气

            QWeather.getWeather24Hourly(MainActivity.this, gps, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherHourlyListener() {
                @Override
                public void onError(Throwable throwable) {
                    Log.i(TAG, "getWeather onERROR:" + throwable);
                }

                @Override
                public void onSuccess(WeatherHourlyBean weatherHourlyBean) {

                    timeTemp = new ArrayList<>();
                    Log.i(TAG, "onSuccess: "+ weatherBean.getNow().getObsTime());
                    List<WeatherHourlyBean.HourlyBean> hourly = weatherHourlyBean.getHourly();

                    Log.i(TAG, "onSuccess: "+ hourly.get(0).getFxTime());
//                                原本
                    for (int i = 0; i < timeList.size(); i++) {

                        Time time = timeList.get(i);
                        String fxTime = hourly.get(i).getFxTime();
                        String wind360 = hourly.get(i).getWind360();
                        Log.i(TAG, "onSuccess: "+wind360);

                        try {
                            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                            isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+8")); // Set your timezone if needed
                            Date date = isoFormat.parse(fxTime);

                            SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
                            String hour = hourFormat.format(date);

                            time.setSevenTime(hour);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        // 获取天气图标
                        String icon = hourly.get(i).getIcon();
                        // 根据天气图标设置相应的SVG图标文件给 Time 对象
                        Drawable iconDrawable = getIconDrawable(icon);  // 自定义方法，根据图标名称获取对应的SVG图标文件
                        time.setIconDrawable(iconDrawable);

                        time.setText_humidity_level(hourly.get(i).getPop());
                        time.setText_fig(hourly.get(i).getWindDir());
                        time.setTemperature(hourly.get(i).getTemp());


                        timeTemp.add(time);
                    }
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            adapter.updateData(timeTemp);
                        }
                    });


                }
            });
//                        获取七天
            QWeather.getWeather7D(MainActivity.this, gps, Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherDailyListener() {

                @Override
                public void onError(Throwable throwable) {
                    Log.i(TAG, "getWeather onERROR:" + throwable);
                }

                @Override
                public void onSuccess(WeatherDailyBean weatherDailyBean) {
                    updateTemperatureTexts( weatherDailyBean);
                }
            });
            //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
            if (Code.OK == weatherBean.getCode()) {
                WeatherNowBean.NowBaseBean now = weatherBean.getNow();
            } else {
                //在此查看返回数据失败的原因
                Code code = weatherBean.getCode();
                Log.i(TAG, "failed code: " + code);
            }
        }
    });


        });

//    设置状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }


    }

    private void startLocation() {
        try {
            mLocationClient = new LocationClient(getApplicationContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (mLocationClient !=null){
            myListener.setCallback(this);
            //声明LocationClient类
            mLocationClient.registerLocationListener(myListener);
            //注册监听函数
            LocationClientOption option = new LocationClientOption();

            option.setFirstLocType(FirstLocTypefirstLocType);
            option.setOpenGnss(true);
            option.setWifiCacheTimeOut(5*60*1000);
            option.setIsNeedAddress(true);
            //可选，是否需要地址信息，默认为不需要，即参数为false
            //如果开发者需要获得当前点的地址信息，此处必须为true

            option.setNeedNewVersionRgc(true);
            //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
            option.setCoorType("bd09ll");
            option.setScanSpan(1000);
            mLocationClient.setLocOption(option);
            //mLocationClient为第二步初始化过的LocationClient对象
            //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
            //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
            mLocationClient.start();
        }
    }
    private void updateTemperatureTexts(WeatherDailyBean weatherDailyBean) {

        for (WeatherDailyBean.DailyBean dailyBean : weatherDailyBean.getDaily()) {
            Log.d(TAG, "onSuccess: TextDat" + dailyBean.getTempMin() + "℃" + "/" + dailyBean.getTempMax() + "℃");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            try {
                String dateString = dailyBean.getFxDate(); // 获取日期

                Date date = inputFormat.parse(dateString);
                // 使用 outputFormat 将日期转换为星期几的字符串
                String dayOfWeek = outputFormat.format(date);
                // 根据星期几设置相应的TextView
                TextView textView;

                switch (dayOfWeek) {
                    case "星期日":
                        textView = findViewById(R.id.text_day7);
                        break;
                    case "星期一":
                        textView = findViewById(R.id.text_day1);
                        break;
                    case "星期二":
                        textView = findViewById(R.id.text_day2);

                        break;
                    case "星期三":
                        textView = findViewById(R.id.text_day3);
                        break;
                    case "星期四":
                        textView = findViewById(R.id.text_day4);
                        break;
                    case "星期五":
                        textView = findViewById(R.id.text_day5);
                        break;
                    case "星期六":
                        textView = findViewById(R.id.text_day6);
                        break;
                    default:
                        Log.d(TAG, "Unrecognized day of week: " + dayOfWeek);
                        continue;

                }

                // 设置星期几到相应的TextView
                textView.setText(dayOfWeek);
                final String temperatureText = dailyBean.getTempMax() + "°/" + dailyBean.getTempMin() + "°";
                Log.d(TAG, "Temperature text: " + temperatureText);


                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

//                                                Log.d(TAG, "temperatureText " +temperatureText );
                        TextView tempTextView = findViewById(getTemperatureTextViewId(dayOfWeek));
                        Log.d(TAG, "Today's TextView ID: " + getTemperatureTextViewId(dayOfWeek));
                        if ("星期五".equals(dayOfWeek)) {

                            //打印温度
                            Log.d("Temperature", temperatureText);

                        }
                        if (tempTextView != null) {
                            tempTextView.setText(temperatureText);
                            tempTextView.invalidate();
                        }
                    }

                });
                textView.invalidate();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private Drawable getIconDrawable(String icon) {
        // 假设你的SVG图标文件名与天气图标代码相匹配
        String iconFilename = icon + ".svg";
        // 获取AssetManager对象
        AssetManager assetManager = getAssets();

        try {
            // 打开SVG图标文件作为InputStream
            InputStream inputStream = assetManager.open(iconFilename);
            // 使用AndroidSVG解析SVG文件
            SVG svg = SVG.getFromInputStream(inputStream);
            // 将SVG渲染为PictureDrawable
            PictureDrawable pictureDrawable = new PictureDrawable(svg.renderToPicture());
            return pictureDrawable;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SVGParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Time> generateTimeList() {
        List<Time> timeList = new ArrayList<>();
        // 格式化小时为两位数字，例如 01、02、...、23
        for (int hour = 0; hour < 24; hour++) {
            String formattedHour = String.format("%02d", hour);
            String timeString = formattedHour + ":00";
            timeList.add(new Time(timeString));
        }

        return timeList;
    }
    private int getTemperatureTextViewId(String dayOfWeek) {
        switch (dayOfWeek) {
            case "星期日":
                return R.id.text_day7_temp;
            case "星期一":
                return R.id.text_day1_temp;
            case "星期二":
                return R.id.text_day2_temp;
            case "星期三":
                return R.id.text_day3_temp;
            case "星期四":
                return R.id.text_day4_temp;
            case "星期五":
                return R.id.text_day5_temp;
            case "星期六":
                return R.id.text_day6_temp;
            default:
                return 0; // 返回一个无效的ID
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {


        toolbar_district.setTitle(bdLocation.getDistrict());
        locationLiveData.postValue(bdLocation);

        gps = bdLocation.getLongitude()+","+bdLocation.getLatitude();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }


}

