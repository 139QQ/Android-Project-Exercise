package com.lzok.rssread.Util;


import android.util.Log;

import com.lzok.rssread.Data.RssFeed;

    import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author lzok
 * @description 获取输入框的连接
 */
public class HtmlDownloader {
    private static final String TAG = String.valueOf(1);

    public RssFeed downloadRssFeed(String url) {
        try {
            Document document = Jsoup.connect(url)
                    .timeout(3000)
                    .get();
            if (document.html().contains("document.write")) {

                // 提取document.write中的XML
                String xml = document.html().replaceAll(".*document.write\\('", "")
                        .replaceAll("'\\);</script>", "");

                // 重新解析XML
                document = Jsoup.parse(xml);
                Log.i(TAG, "downloadRssFeed: " + document);
            }
            return parseHtmlToRssFeed(document);
        } catch (IOException e) {
            e.printStackTrace();
            // 处理网络请求异常
            return null;
        }
    }

    private RssFeed parseHtmlToRssFeed(Document document) {
        RssFeed rssFeed = new RssFeed();
        // 获取频道名称
        String channel = document.getElementsByTag("title").first().text();
        rssFeed.setChannel(channel);
        Log.i(TAG, "getChannel: " + channel);

        // 从HTML文档中提取信息并将其设置在rssFeed对象中
        Element channelElement = document.select("channel").first();

        if (channelElement != null) {
            rssFeed.setTitle(channelElement.select("title").text());
            rssFeed.setLink(channelElement.select("link").text());
            rssFeed.setDescription(channelElement.select("description").text());
            rssFeed.setGenerator(channelElement.select("generator").text());
            rssFeed.setLanguage(channelElement.select("language").text());
            rssFeed.setLastBuildDate(channelElement.select("lastBuildDate").text());



        }

        Elements itemElements = document.select("item");
        List<RssFeed> rssItems = new ArrayList<>();
        for (Element itemElement : itemElements) {
            RssFeed item = new RssFeed();
            item.setTitle(itemElement.select("title").text());
            item.setDescription(itemElement.select("description").text());
            item.setPubDate(itemElement.select("pubDate").text());
            // 提取图片（如果有的话）
            Element itemImageElement = itemElement.select("image").first();
            if (itemImageElement != null) {
                item.setImage(itemImageElement.select("url").text());
            }

            item.setLink(itemElement.select("link").text());
            rssItems.add(item);
        }
        rssFeed.setItems(rssItems);

        // 进一步解析并设置RSS Feed中的项

        return rssFeed;
    }
}