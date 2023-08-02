package com.lzok.materialdesign;

import android.content.Intent;

/**
 * @author Administrator
 */
public class Fruit extends Intent {
    private String name;
    private int ImageId;





    public Fruit(String name, int imageId) {
        this.name = name;
        ImageId = imageId;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

}
