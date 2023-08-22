package com.lzok.bdtest;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lzok.bdtest.location.LocationCallback;
import com.lzok.bdtest.location.MyLocationListener;
import com.lzok.bdtest.location.S;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationCallback {

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    TextView jh,city,country,addrstr,Longitude,Latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
        SDKInitializer.initialize(getApplicationContext());

        LocationClient.setAgreePrivacy(true);

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

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", "value");
        editor.apply();



                jh = findViewById(R.id.jh);
                city = findViewById(R.id.text_City);
                country = findViewById(R.id.text_Country);
                addrstr  = findViewById(R.id.text_addrstr);
                Longitude = findViewById(R.id.text_Longitude);
                Latitude = findViewById(R.id.text_Latitude);

                Latitude.setText( S.LATI_TUDE + "纬度");

        }

            public  void startLocation(){

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


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        jh.setText(bdLocation.getDistrict());
        city.setText(bdLocation.getCity());
        country.setText(bdLocation.getCountry());
        addrstr.setText(bdLocation.getAddrStr());


        // 将经度保存到S类的LATI_TUDE静态变量中
        S.LATI_TUDE = bdLocation.getLatitude();
        Log.i(TAG, "onReceiveLocation: "+S.LATI_TUDE );
    }
}