package com.lzok.testcompose

import HitokotoJSON
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.lzok.testcompose.model.HitokotoViewModel

import com.lzok.testcompose.ui.theme.TestComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val hitokotoViewModel: HitokotoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val hitokotoJson by hitokotoViewModel.hitokotoJson.observeAsState()

            LaunchedEffect(true) {
                // 在启动时进行异步加载数据
                hitokotoViewModel.loadHitokotoJson()
                while (true) {
                    delay(15000) // 每隔15秒刷新一次
                    hitokotoViewModel.loadHitokotoJson()
                }
            }

            TestComposeTheme {
                Button(onClick = { /*TODO*/ }, modifier = Modifier.width(38.dp)) {
                    Text(text = "按钮")
                }
                Hiokkto(hitokotoJson)
            }

        }
    }

    @Composable
    private fun Hiokkto(hitokotoJson: HitokotoJSON?) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            Card(
                modifier = Modifier
                    .padding(top = 1.dp) // 将"一言"文本与其父容器的顶部保持18dp的距离
                    .size(width = 360.dp, height = 360.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 4.dp,

                ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hiokkto",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (hitokotoJson != null) {
                        Text(
                            text = hitokotoJson.hitokoto,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(4.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = hitokotoJson.from,
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.subtitle1
                        )
                    } else {
                        Text(
                            text = hitokotoJson?.hitokoto ?: "加载中...",
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    TestComposeTheme {
        Card {
            Text(text = "")
        }
    }
}