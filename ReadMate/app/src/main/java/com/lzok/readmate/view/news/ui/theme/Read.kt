package com.lzok.readmate.view.news.ui.theme


import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Read(title: String, content: String) {

    Column {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        WebViewComponent(content)


        val TAG = "Read"
        Log.i(TAG, "Read: ${content}")

    }

}

/**
 * Web页面
 */
@Composable
private fun WebViewComponent(content: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.loadsImagesAutomatically = true

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                settings.javaScriptEnabled = true

                loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
            }
        },
        update = { webView ->
            webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    )
}

