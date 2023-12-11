package com.lzok.readmate.network

import com.lzok.readmate.item.NewsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.util.concurrent.TimeUnit

class RssHubparse {
    // 解析 RSS Feed
    suspend fun parseRssFeed(url: String): List<NewsListItem> = withContext(Dispatchers.IO) {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS) // 设置连接超时时间为 10 秒
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        val response: Response = client.newCall(request).execute()
        val xmlString: String? = response.body?.string()

        if (xmlString != null) {
            parseXmlString(xmlString)
        } else {
            emptyList()
        }
    }
}

// 解析 XML 字符串
private fun parseXmlString(xmlString: String): List<NewsListItem> {
    val newsItems = mutableListOf<NewsListItem>()

    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()
    parser.setInput(StringReader(xmlString))

    var eventType = parser.eventType
    var newsItem: NewsListItem? = null

    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                if (parser.name == "item") {
                    newsItem = NewsListItem("", "", "", "")
                } else if (newsItem != null) {
                    when (parser.name) {
                        "title" -> newsItem.title = parser.nextText()
                        "author" -> newsItem.author = parser.nextText()
                        "pubDate" -> newsItem.pubDate = parser.nextText()
                        "description" -> newsItem.content = parser.nextText()


                    }
                }
            }

            XmlPullParser.END_TAG -> {
                if (parser.name == "item" && newsItem != null) {
                    newsItems.add(newsItem)
                    newsItem = null
                }
            }
        }
        eventType = parser.next()
    }

    return newsItems
}


