package com.lzok.rssread.Data;

public class Channel {
    private String name;
    private String description;
    private String link;

    public Channel(String name, String description, String link) {
        this.name = name;
        this.description = description;
        this.link = link;
    }

    // 添加必要的 getter 和 setter 方法以及其他自定义方法

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
