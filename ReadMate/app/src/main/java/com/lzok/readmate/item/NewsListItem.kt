package com.lzok.readmate.item

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * @author lzok
 * @descriptive 它包含了新闻项的标题、作者、发布日期和内容。
 */
data class NewsListItem(
    val title: String,
    val author: String,
    val pubDate: String,
    val content: String
)

@Composable
fun NewsListItemContent(newsItem: NewsListItem) {
    Row(modifier = Modifier.padding(16.dp)) {
        // 图片
        // Image(
        //     painter = painterResource(R.drawable.news_image),
        //     contentDescription = "News Image",
        //     modifier = Modifier.size(80.dp)
        // )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = newsItem.author,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = newsItem.pubDate,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = newsItem.content,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


