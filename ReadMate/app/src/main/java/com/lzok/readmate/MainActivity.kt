package com.lzok.readmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.lzok.readmate.network.RssHubparse
import com.lzok.readmate.ui.theme.ReadMateTheme
import com.lzok.readmate.item.NewsListItem
import com.lzok.readmate.item.NewsListItemContent
import com.lzok.readmate.view.news.ui.theme.Read


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadMateTheme {
                JumpRead()

            }
        }
    }
}

/**
 * 跳转到Read页面
 */
@Composable
private fun JumpRead() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MyScreen(navController)
        }
        navigation(
            startDestination = "read/{title}/{content}",
            route = "read_nav_graph"
        ) {
            composable("read/{title}/{content}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                val content = backStackEntry.arguments?.getString("content")
                if (title != null && content != null) {
                    Read(title, content)
                }
            }
        }
    }

}

/*
 * 用于显示主界面的内容
 *
 */
@Composable
fun MyScreen(navController: NavController) {
    val rssHubparse = RssHubparse()
    val rssUrl = "https://rsshub.rssforever.com/36kr/information/web_news" // 替换为你的 RSS 订阅源链接
    var newsItems by remember { mutableStateOf<List<NewsListItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        val items = rssHubparse.parseRssFeed(rssUrl)
        newsItems = items
    }

    MainScreen(newsItems, navController)
}

/**用于显示新闻列表*/
@Composable
fun NewsList(newsList: List<NewsListItem>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(newsList) { news ->
            NewsListItemContent(news) {
                navController.navigate("read/${news.title}/${news.content}")
                val TAG = "NewsList"
                Log.i(TAG, "NewsList: ${news.title}")

            }
        }
    }
}


/*用于显示主界面的内容*/
@Composable
fun MainScreen(newsList: List<NewsListItem>, navController: NavController) {

    Column {
        // 其他主界面组件
        NewsList(newsList, navController)
    }
}


