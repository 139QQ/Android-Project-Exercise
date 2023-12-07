package com.lzok.rssread.Data;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzok
 * @description 数据持久化
 */
public class DataPersistenceManager {
    private static final String PREF_NAME = "MyAppPrefs";
    // 新增频道链接的键
    private static final String CHANNEL_LINKS_KEY = "channelLinks";

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

    // 添加新的链接到链接列表
    public void addChannelLink(String link) {
        List<String> channelLinks = getChannelLinks();
        if (channelLinks == null) {
            channelLinks = new ArrayList<>();
        }
        channelLinks.add(link);
        saveChannelLinks(channelLinks);
    }

}
