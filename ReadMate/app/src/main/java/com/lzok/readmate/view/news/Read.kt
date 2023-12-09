package com.lzok.readmate.view.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lzok.readmate.view.news.ui.theme.ReadMateTheme

class Read : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadMateTheme {
            }
        }
    }
}
