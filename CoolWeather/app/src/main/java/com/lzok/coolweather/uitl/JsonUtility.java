package com.lzok.coolweather.uitl;

import android.text.TextUtils;

import com.lzok.coolweather.db.City;
import com.lzok.coolweather.db.County;
import com.lzok.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lzok
 * @descriptive JSON数据解析
 */
public class JsonUtility {
    /**
     *
     * @param response
     * @return 返回和处理服务器的省级数据
     */
    public static boolean handleProvinceResponse(String response){
    if (!TextUtils.isEmpty(response)){
        try{
            JSONArray allProvinces = new JSONArray(response);
            for (int i = 0; i < allProvinces.length(); i++) {
                JSONObject provinceObj =   allProvinces.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObj.getString("name"));
                province.setProvinceCode(provinceObj.getInt("id"));
                province.save();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    return false;
    }

    /**
     *
     * @param response
     * @param provinceId
     * @return 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response ,int provinceId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObj =   allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObj.getString("name"));
                    city.setCityCode(cityObj.getInt("iD"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     *
     * @param response
     * @param cityId 县Id
     * @return 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountyResponse(String response ,int cityId){
        if (!TextUtils.isEmpty(response)){
            try{
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    JSONObject countyObj =   allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObj.getString("Name"));
                    county.setWeatherId(countyObj.getString("Weather_ID"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
