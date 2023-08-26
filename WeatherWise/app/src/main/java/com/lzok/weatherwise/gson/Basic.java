package com.lzok.weatherwise.gson;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Basic {
    /**
     * basic
     */
    @SerializedName("basic")
    public BasicDTO basic;

    /**
     * BasicDTO
     */
    public BasicDTO basicDTO;
    @NoArgsConstructor
    @Data
    public class BasicDTO {
        /**
         * fxLink
         */
        @SerializedName("fxLink")
        private String fxLink;
        /**
         * updateTime
         */
        @SerializedName("updateTime")
        public String updateTime;
    }
}
