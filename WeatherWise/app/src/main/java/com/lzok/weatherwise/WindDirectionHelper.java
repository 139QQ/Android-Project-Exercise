package com.lzok.weatherwise;

import static com.qweather.sdk.view.HeContext.context;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class WindDirectionHelper {
    private static final int NUM_DIRECTIONS = 8;

    private static final String[] DIRECTIONS = {
            "东", "东南", "南", "西南",
            "西", "西北", "北", "东北"
    };


    private static final int[] ICON_RESOURCES = {
            R.drawable.east, R.drawable.south_east,
            R.drawable.south, R.drawable.south_west,
            R.drawable.west, R.drawable.north_west,
            R.drawable.north, R.drawable.north_east
    };

    public static String getDirectionName(double windDegree) {
        int directionIndex = (int) Math.round(windDegree / 45) % 8;
        return DIRECTIONS[directionIndex];
    }

    public static int getDirectionIconResource(double windDegree) {
        int directionIndex = (int) Math.round(windDegree / 45) % 8;
        return ICON_RESOURCES[directionIndex];
    }
    public static  Drawable getWindIconDrawable( double windDegree) {
        try {
            if (windDegree == 0) {
                Log.d("WindIconDebug", "Returning north icon");
                return context.getResources().getDrawable(R.drawable.north);
            } else if (windDegree >= 337.5 || windDegree < 22.25) {
                Log.d("WindIconDebug", "Returning north icon");
                return context.getResources().getDrawable(R.drawable.north);
            } else if (windDegree >= 22.25 && windDegree < 67.5) {
                Log.d("WindIconDebug", "Returning north-east icon");
                return context.getResources().getDrawable(R.drawable.north_east);
            } else if (windDegree >= 67.5 && windDegree < 112.5) {
                Log.d("WindIconDebug", "Returning east icon");
                return context.getResources().getDrawable(R.drawable.east);
            } else if (windDegree >= 112.5 && windDegree < 157.5) {
                Log.d("WindIconDebug", "Returning south-east icon");
                return context.getResources().getDrawable(R.drawable.south_east);
            } else if (windDegree >= 157.5 && windDegree < 202.5) {
                Log.d("WindIconDebug", "Returning south icon");
                return context.getResources().getDrawable(R.drawable.south);
            } else if (windDegree >= 202.5 && windDegree < 247.5) {
                Log.d("WindIconDebug", "Returning south-west icon");
                return context.getResources().getDrawable(R.drawable.south_west);
            } else if (windDegree >= 247.5 && windDegree < 292.5) {
                Log.d("WindIconDebug", "Returning west icon");
                return context.getResources().getDrawable(R.drawable.west);
            } else if (windDegree >= 292.5 && windDegree < 337.5) {
                Log.d("WindIconDebug", "Returning north-west icon");
                return context.getResources().getDrawable(R.drawable.north_west);
            } else {
                Log.d("WindIconDebug", "Returning null for invalid angle: " + windDegree);
                return null; // 处理无效的角度
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("WindIconDebug", "Exception occurred: " + e.getMessage());
            return null; // 返回 null 表示处理异常情况
        }

    }
}

