package com.lzok.materialdesign;

/**
 * @author Administrator
 */
public class Fruit {
    private String name;
    private int ImageId;
    private String Descriptions;

    public String getDescriptions() {
        return Descriptions;
    }

    public void setDescriptions(String descriptions) {
        Descriptions = descriptions;
    }

    public Fruit(String name, int imageId, String descriptions) {
        this.name = name;
        ImageId = imageId;
        Descriptions = descriptions;
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
