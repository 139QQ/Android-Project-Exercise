package com.lzok.readmate.ui.theme


import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                    maxLines = 1,

                )
            },

            // 设置背景颜色
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),


        )
        WebViewComponent(content)
    }

}

/**
 * Web页面
 */
@Composable
private fun WebViewComponent(content: String) {
    // 使用 rememberUpdatedState 来保存传递给它的值，确保在值变化时更新
    val rememberedContent by rememberUpdatedState(content)

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                Log.d("context",content)
                // WebView 设置
                settings.apply {
                    loadsImagesAutomatically = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    javaScriptEnabled = true
                    // 允许使用 localStorage 和 sessionStorage
                    domStorageEnabled = true
                    // 允许缩放
                    setSupportZoom(true)
                    builtInZoomControls = true
                    displayZoomControls = false


                    blockNetworkImage = false

                    loadsImagesAutomatically = true
                    // 针对 Android 21 及以上的版本启用混合内容模式
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                    }
                }

                // 布局参数设置
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                webViewClient  = WebViewClient()
                loadUrl("https://rsshub.rssforever.com/36kr/motif/452")
                // 加载数据到 WebView
                loadData(rememberedContent, "text/html", "utf-8")
            }
        },

        update = { webView ->
            // 当内容变化时，更新 WebView
            webView.loadData(rememberedContent, "text/html", "utf-8")
        },
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    )
}




