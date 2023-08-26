package com.lzok.weatherwise.gson;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lzok
 */
@NoArgsConstructor
@Data
public class Now {

        /**
         * code
         */
        @SerializedName("code")
        public String code;
        /**
         * now
         */
        @SerializedName("now")
        public NowDTO now;

        /**
         * NowDTO
         */
        public NowDTO nowDTO;
        @NoArgsConstructor
        @Data
        public  class NowDTO {
                /**
                 * cloud 多云
                 */
                @SerializedName("cloud")
                public String cloud;
                /**
                 * dew
                 */
                @SerializedName("dew")
                public String dew;
                /**
                 * feelsLike
                 */
                @SerializedName("feelsLike")
                public String feelsLike;
                /**
                 * humidity
                 */
                @SerializedName("humidity")
                public String humidity;
                /**
                 * icon
                 */
                @SerializedName("icon")
                public String icon;
                /**
                 * obsTime
                 */
                @SerializedName("obsTime")
                public String obsTime;
                /**
                 * precip
                 */
                @SerializedName("precip")
                public String precip;
                /**
                 * pressure
                 */
                @SerializedName("pressure")
                public String pressure;
                /**
                 * temp
                 */
                @SerializedName("temp")
                public String temp;
                /**
                 * text
                 */
                @SerializedName("text")
                public String text;
                /**
                 * vis
                 */
                @SerializedName("vis")
                public String vis;
                /**
                 * wind360
                 */
                @SerializedName("wind360")
                public String wind360;
                /**
                 * windDir
                 */
                @SerializedName("windDir")
                public String windDir;
                /**
                 * windScale
                 */
                @SerializedName("windScale")
                public String windScale;
                /**
                 * windSpeed
                 */
                @SerializedName("windSpeed")
                public String windSpeed;
        }
}
