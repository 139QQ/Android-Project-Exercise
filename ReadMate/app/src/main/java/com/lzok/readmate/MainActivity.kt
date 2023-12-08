package com.lzok.readmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lzok.readmate.network.RssHubparse
import com.lzok.readmate.ui.theme.ReadMateTheme
import com.lzok.readmate.item.NewsListItem
import com.lzok.readmate.item.NewsListItemContent
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadMateTheme {
                MyScreen()

            }
        }
    }
}

/*
 * 用于显示主界面的内容
 *
 */
@Composable
fun MyScreen() {
    val rssHubparse = RssHubparse()
    val rssUrl = "https://rsshub.rssforever.com/36kr/information/web_news" // 替换为你的 RSS 订阅源链接
    var newsItems by remember { mutableStateOf<List<NewsListItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val items = rssHubparse.parseRssFeed(rssUrl)
        newsItems = items
    }

    MainScreen(newsItems)
}
/*用于显示新闻列表*/
@Composable
fun NewsList(newsList: List<NewsListItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(newsList) { news ->
            NewsListItemContent(news)
        }
    }
}
/*用于显示主界面的内容*/
@Composable
fun MainScreen(newsList: List<NewsListItem>) {
    Column {
        // 其他主界面组件
        NewsList(newsList)
    }
}


