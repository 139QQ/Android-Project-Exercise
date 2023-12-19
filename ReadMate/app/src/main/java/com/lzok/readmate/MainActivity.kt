package com.lzok.readmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lzok.readmate.DataBase.RssHub
import com.lzok.readmate.network.RssHubparse
import com.lzok.readmate.ui.theme.ReadMateTheme
import com.lzok.readmate.item.NewsListItem
import com.lzok.readmate.item.NewsListItemContent
import com.lzok.readmate.ui.theme.Read
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


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

    val rssUrl = "https://rsshub.rssforever.com/36kr/motif/452" // 替换为你的 RSS 订阅源链接
    // item 状态
    var newsItems by rememberSaveable { mutableStateOf<List<NewsListItem>>(emptyList()) }
    /**
     * 启用 协程加载item
     */
    LaunchedEffect(Unit) {
        val items = rssHubparse.parseRssFeed(rssUrl)
        newsItems = items

    }

    MainScreen(newsItems, navController)
}


/**用于显示新闻列表*/
@Composable
fun NewsList(newsList: List<NewsListItem>, navController: NavController) {
    // 记录 LazyColumn 的滚动状态
    val state = rememberLazyListState()

    // 使用 Box 将 FloatingActionButton 叠加在 LazyColumn 之上
    Box(modifier = Modifier.fillMaxSize()) {
        // LazyColumn 使用 nestedScrollConnection
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = state,
        ) {
            items(newsList) { news ->
                NewsListItemContent(news) {
                    // 导航到读取页面
                    val encodedTitle =
                        URLEncoder.encode(news.title, StandardCharsets.UTF_8.toString())
                    val encodedContent =
                        URLEncoder.encode(news.content, StandardCharsets.UTF_8.toString())
                    navController.navigate("read/${encodedTitle}/${encodedContent}")

                }
            }
        }

        // AddButton 将显示在 LazyColumn 之上
        AddButton(isVisible = state.firstVisibleItemIndex == 0)
    }
}

@Composable
fun AddButton(isVisible: Boolean) {
    // 打印 FloatingActionButton 的可见状态
    if (isVisible) {
        // 显示 FloatingActionButton，位于屏幕右下角
        FloatingActionButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .padding(16.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.place(
                            constraints.maxWidth - placeable.width,
                            constraints.maxHeight - placeable.height
                        )
                    }
                }
        ) {
            Icons.Default.Add
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


