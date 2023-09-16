package com.lzok.rssread.Data;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.channels.Channel;
import java.util.List;

/**
 * @author lzok
 * @description 数据持久化
 */
public class DataPersistenceManager {
    private static final String PREF_NAME = "MyAppPrefs";
    private static final String CHANNELS_KEY = "channels";
    private static final String LAST_OPENED_CHANNEL_KEY = "lastOpenedChannel";
    private static final String RSS_ITEMS_KEY = "rssItems";
    // 新增频道链接的键
    private static final String CHANNEL_LINKS_KEY = "channelLinks";
    private static final String LAST_OPENED_LINK_KEY = "lastOpenedLink";

    private SharedPreferences preferences;
    private Gson gson;

    public DataPersistenceManager(Context context) {
        Log.i(TAG, "DataPersistenceManager constructor called");
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }
    private static DataPersistenceManager instance;

    public static synchronized DataPersistenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataPersistenceManager(context);
        }
        return instance;
    }

    // 保存频道列表
    public void saveChannels(List<String> channels) {
        String channelsJson = gson.toJson(channels);
        preferences.edit().putString(CHANNELS_KEY, channelsJson).apply();
    }

    // 获取频道列表
    public List<String> getChannels() {
        String channelsJson = preferences.getString(CHANNELS_KEY, "");
        Type type = new TypeToken<List<Channel>>() {}.getType();
        return gson.fromJson(channelsJson, type);
    }

    // 保存最后打开的频道
    public void saveLastOpenedChannelName(String channelName) {
        preferences.edit().putString(LAST_OPENED_CHANNEL_KEY, channelName).apply();
    }

    /** 获取最后打开的频道
     *
     * @return gson.fromJson(channelJson, Channel.class);
     */
    public String getLastOpenedChannelName(String channelName) {
        return preferences.getString(LAST_OPENED_CHANNEL_KEY, "");
    }

    // 保存 rssItems 列表
    public void saveRssItems(List<RssFeed> rssItems) {
        String rssItemsJson = gson.toJson(rssItems);
        preferences.edit().putString(RSS_ITEMS_KEY, rssItemsJson).apply();
    }

    // 获取 rssItems 列表
    public List<RssFeed> getRssItems() {
        String rssItemsJson = preferences.getString(RSS_ITEMS_KEY, "");
        Type type = new TypeToken<List<RssFeed>>() {}.getType();
        return gson.fromJson(rssItemsJson, type);
    }
    // 保存频道链接列表
    public void saveChannelLinks(List<String> channelLinks) {
        String channelLinksJson = gson.toJson(channelLinks);
        preferences.edit().putString(CHANNEL_LINKS_KEY, channelLinksJson).apply();
    }

    // 获取频道链接列表
    public List<String> getChannelLinks() {
        String channelLinksJson = preferences.getString(CHANNEL_LINKS_KEY, "");
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(channelLinksJson, type);
    }

    // 保存最后打开的链接
    public void saveLastOpenedLink(String link) {
        preferences.edit().putString(LAST_OPENED_LINK_KEY, link).apply();
    }

    // 获取最后打开的链接
    public String getLastOpenedLink() {
        return preferences.getString(LAST_OPENED_LINK_KEY, "");
    }
}
