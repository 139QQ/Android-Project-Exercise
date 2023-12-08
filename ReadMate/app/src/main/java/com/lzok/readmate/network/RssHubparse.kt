package com.lzok.readmate.network

import com.lzok.readmate.item.NewsListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.util.concurrent.TimeUnit
import javax.xml.parsers.DocumentBuilderFactory

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
            parseXmlString(xmlString)// 调用解析 XML 字符串的函数
        } else {
            emptyList()
        }
    }
    // 解析 XML 字符串
    private fun parseXmlString(xmlString: String): List<NewsListItem> {
        val documentBuilderFactory = DocumentBuilderFactory.newInstance()
        val documentBuilder = documentBuilderFactory.newDocumentBuilder()
        val inputSource = InputSource(xmlString.reader())
        val document = documentBuilder.parse(inputSource)
        document.documentElement.normalize()

        val items = document.getElementsByTagName("item")
        val newsItems = mutableListOf<NewsListItem>()

        for (i in 0 until items.length) {
            val itemNode = items.item(i) as Element

            val titleNode = itemNode.getElementsByTagName("title").item(0)
            val title = titleNode?.textContent

            val authorNode = itemNode.getElementsByTagName("author").item(0)
            val author = authorNode?.textContent

            val pubDateNode = itemNode.getElementsByTagName("pubDate").item(0)
            val pubDate = pubDateNode?.textContent

            val contentNode = itemNode.getElementsByTagName("description").item(0)
            val content = contentNode?.textContent

            val newsItem = NewsListItem(
                title = title ?: "",
                author = author ?: "",
                pubDate = pubDate ?: "",
                content = content ?: ""
            )
            newsItems.add(newsItem)
        }


        return newsItems
    }
}
