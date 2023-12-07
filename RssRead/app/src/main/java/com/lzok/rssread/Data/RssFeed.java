package com.lzok.rssread.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzok
 * @description Html属性
 */

public class RssFeed implements Serializable {
    private String title;
    private String link;
    private String description;
    private String generator;
    private String language;
    private String lastBuildDate;
    private String pubDate;
    private String channel;
    private String image;
    private List<RssFeed> items;

    public RssFeed() {
        items = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getLanguage() {


        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getImage() {
        return image;
    }




    public void setImage(String image) {
        this.image = image;
    }

    public List<RssFeed> getItems() {
        return items;
    }

    public void setItems(List<RssFeed> items) {
        this.items = items;
    }

    public void addItem(RssFeed item) {
        items.add(item);
    }

    @Override
    public String toString() {
        return "RssFeed{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", generator='" + generator + '\'' +
                ", language='" + language + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", pubDate=" + pubDate +
                ", channel='" + channel + '\'' +
                ", image='" + image + '\'' +
                ", items=" + items +
                '}';
    }
}


