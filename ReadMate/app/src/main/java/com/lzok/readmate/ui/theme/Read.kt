package com.lzok.readmate.ui.theme


import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import java.io.ByteArrayInputStream


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
        Log.d("contents","$content")
        WebViewComponent(content)
    }

}

/**
 * Web页面
 */
@Composable
fun WebViewComponent(content: String) {
    val webViewState = rememberUpdatedState(content)

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    blockNetworkImage = false
                    allowContentAccess = true

                }
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        Log.d("WebViewComponent", "Loading progress: $newProgress%")
                    }
                }

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("WebViewComponent", "Page started loading. URL: $url")
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        Log.d("WebViewComponent", "Page finished loading. URL: $url")
                    }

                    override fun shouldInterceptRequest(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): WebResourceResponse? {
                        Log.d("WebViewComponent", "Intercepting request: ${request?.url}")
                        return super.shouldInterceptRequest(view, request)
                    }

                }

                // 在加载数据之前进行判断
                    // 加载数据到 WebView
                    loadDataWithBaseURL(
                        null,
                        webViewState.value,
                        "text/html",
                        "utf-8",
                        null
                    )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}

