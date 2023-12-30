package com.lzok.readmate

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.lzok.readmate.network.RssHubparse
import com.lzok.readmate.ui.theme.ReadMateTheme
import com.lzok.readmate.item.NewsListItem
import com.lzok.readmate.item.NewsListItemContent
import com.lzok.readmate.ui.theme.Read
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    val context = LocalContext.current
    // 打印 FloatingActionButton 的可见状态
    if (isVisible) {
        // 显示 FloatingActionButton，位于屏幕右下角
        FloatingActionButton(
            onClick = { Toast.makeText(context, "添加", Toast.LENGTH_LONG).show() },
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
                .height(56.dp)
                .width(56.dp)

        ) {
            Icon(Icons.Filled.Add, contentDescription = "添加", Modifier.size(24.dp))
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

val rssParser: RssParser = RssParser()

@Composable
fun RssTest() {
    val rssChannelState = remember { mutableStateOf<RssChannel?>(null) }

    LaunchedEffect(Unit) {
        try {
            val rssChannel: RssChannel? = withContext(Dispatchers.IO) {
                rssParser.getRssChannel("https://rsshub.rssforever.com/36kr/motif/452")
            }

            // 更新状态
            rssChannelState.value = rssChannel
        } catch (e: Exception) {
            // 处理异常
        }
    }

    // 在 Compose 中使用 rssChannelState.value
    val rssChannel = rssChannelState.value

    // 在这里可以根据 rssChannel 的值进行相应的UI操作
    rssChannel?.let {
        // 如果 rssChannel 不为 null，执行相应的操作，例如显示数据
        Column {
//            Text("Channel Title: ${it.channelTitle}")
            Text("Last Build Date: ${it.lastBuildDate}")

            // 遍历并显示每个 item
            it.items.forEach { item ->
                Text("Title: ${item.title}")
                Text("Author: ${item.author}")
                Text("Pub Date: ${item.pubDate}")
                // 其他项的显示方式
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WebViewPreview() {
    RssTest()
}