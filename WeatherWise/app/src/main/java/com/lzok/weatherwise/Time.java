package com.lzok.weatherwise;

import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.data.LineData;

public class Time {
    private String time;

    public Time() {
    }


    private double windDegree;



    private Drawable iconDrawable;
    private String sevenTime;
    private String rec_text_temp;
    private String temp_roundabout;
    private String text_humidity_level;
    private String text_fig;

    public String getIco_climatic() {
        return ico_climatic;
    }

    public void setIco_climatic(String ico_climatic) {
        this.ico_climatic = ico_climatic;
    }

    private String ico_climatic;
    private String temperature;
    private String weather;

    private String humidity;


    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Time(String sevenTime) {
        this.sevenTime = sevenTime;
    }

    public Time(String sevenTime, String rec_text_temp, String temp_roundabout, String text_humidity_level, String text_fig) {
        this.sevenTime = sevenTime;
        this.rec_text_temp = rec_text_temp;
        this.temp_roundabout = temp_roundabout;
        this.text_humidity_level = text_humidity_level;
        this.text_fig = text_fig;
    }

    public String getSevenTime() {
        return sevenTime;
    }

    public void setSevenTime(String sevenTime) {
        this.sevenTime = sevenTime;
    }

    public String getRec_text_temp() {
        return rec_text_temp;
    }

    public void setRec_text_temp(String rec_text_temp) {
        this.rec_text_temp = rec_text_temp;
    }

    public String getTemp_roundabout() {
        return temp_roundabout;
    }

    public void setTemp_roundabout(String temp_roundabout) {
        this.temp_roundabout = temp_roundabout;
    }

    public String getText_humidity_level() {
        return humidity;
    }

    public void setText_humidity_level(String humidity) {
        this.humidity = humidity;
    }

    public String getText_fig() {
        return text_fig;
    }

    public void setText_fig(String text_fig) {
        this.text_fig = text_fig;
    }

    public String getTime() {
        return this.time;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }
}
