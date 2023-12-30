package com.lzok.readmate.network

import android.util.Log
import com.lzok.readmate.item.NewsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.util.concurrent.TimeUnit

class RssHubparse {
    // 解析 RSS Feed
    suspend fun parseRssFeed(url: String): List<NewsListItem> = withContext(Dispatchers.IO) {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(url)
            .build()

        val response: Response = client.newCall(request).execute()
        val htmlString: String? = response.body?.string()

        if (htmlString != null) {
            parseHtmlString(htmlString)
        } else {
            emptyList()
        }
    }

    // 解析 HTML 字符串
    private fun parseHtmlString(htmlString: String): List<NewsListItem> {
        val newsItems = mutableListOf<NewsListItem>()
        val doc = Jsoup.parse(htmlString)

        val items = doc.select("item")
        for (item in items) {
            val channel = item.selectFirst("channel")?.text()?:""
            val title = item.selectFirst("title")?.text() ?: ""
            val author = item.selectFirst("author")?.text() ?: ""
            val pubDate = item.selectFirst("pubDate")?.text() ?: ""
            val description = item.selectFirst("description")?.text() ?: ""
            Log.d("description", "parseHtmlString:$description ")
            val link = item.selectFirst("link")?.text() ?: ""
            val ChannelTitle = item.selectFirst("channeltitle")?.text()?.trim() ?: ""
            val lastBuildDate=item.selectFirst("lastBuildDate")?.text() ?: ""
            val newsItem = NewsListItem(title, author, pubDate, description, link,channel, ChannelTitle, lastBuildDate  )
            newsItems.add(newsItem)
        }

        return newsItems
    }
}


