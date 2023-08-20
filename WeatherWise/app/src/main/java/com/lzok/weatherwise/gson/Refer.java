package com.lzok.weatherwise.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Refer {
    /**
     * refer
     */
    @SerializedName("refer")
    public ReferDTO refer;

    /**
     * ReferDTO
     */
    public ReferDTO referDTO;
    @NoArgsConstructor
    @Data
    public  class ReferDTO {
        /**
         * licenseList
         */
        @SerializedName("licenseList")
        public List<String> licenseList;
        /**
         * sourcesList
         */
        @SerializedName("sourcesList")
        public List<String> sourcesList;
    }
}
