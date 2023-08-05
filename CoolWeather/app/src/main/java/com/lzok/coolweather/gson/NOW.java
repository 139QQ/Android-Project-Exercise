package com.lzok.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class NOW {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
