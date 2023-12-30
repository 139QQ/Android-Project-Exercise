package com.lzok.readmate.item

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 *  数据实体
 * @author lzok
 * @descriptive 它包含了新闻项的标题、作者、发布日期和内容。
 */
data class NewsListItem(
    var title: String,
    var author: String,
    var pubDate: String,
    var content: String,
    var link: String,
    var channel: String,
    var ChannelTitle: String,
//    频道内容上次改变的时间
    val lastBuildDate: String
)

data class Channel(
    var channel: String,
    var ChannelTitle: String,
    var link: String,
//    频道内容上次改变的时间
    val lastBuildDate: String

)

/**
 * 列表的item
 * @param newsItem:为解析后的数据
 * @param onItemClicked 点击item跳转
 */
@Composable
fun NewsListItemContent(newsItem: NewsListItem, onItemClicked: (NewsListItem) -> Unit) {
    Column(modifier = Modifier
        .padding(16.dp)
        .clickable { onItemClicked(newsItem) }
    ) {
        Row() {
//            Image(
//                painter = painterResource(R.drawable.news_image),
//                contentDescription = "News Image",
//                modifier = Modifier.size(80.dp)
//            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = newsItem.author,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
//               更新时间
                Text(
                    fontSize = 12.sp,
                    text = newsItem.pubDate,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = newsItem.link,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Log.i("TAG", "${newsItem.title} ")
            }
        }
        Divider()
    }
}







