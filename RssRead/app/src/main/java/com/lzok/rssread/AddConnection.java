package com.lzok.rssread;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.search.SearchBar;
import com.lzok.rssread.Data.DataPersistenceManager;
import com.lzok.rssread.Data.RssFeed;
import com.lzok.rssread.Util.ConnectionParser;


/**
 * @author lzok
 * @description 解析连接和历史记录
 */
public class AddConnection extends AppCompatActivity {
    Handler handler = new Handler();
    ImageView back_image;
    EditText editText;
    TextView channel,text_jiexi_channel;
    RssFeed feed = new RssFeed();
    Button button;
    DataPersistenceManager dataPersistenceManager ;
    /**
     * 定义超时消息的常量
      */

    private static final int TIMEOUT_MESSAGE = 1;
    private String url; // 声明为成员变量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connection);
        back_image = findViewById(R.id.imageView_back);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button_analysis);
        text_jiexi_channel = findViewById(R.id.text_jiexi_channel);
        channel = findViewById(R.id.text_jiexi_channel);
        channel.setText(feed.getChannel());
        dataPersistenceManager = new DataPersistenceManager(this);
        // 返回上一个Activity
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 隐藏text_jiexi_channel刚开始
        text_jiexi_channel.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                url = editText.getText().toString(); // 设置url的值

                if (TextUtils.isEmpty(url)) {
                    // URL为空
                    Toast.makeText(AddConnection.this, "请输入连接", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 支持http和https
                if (!url.matches("https?://.*")) {
                    Toast.makeText(AddConnection.this, "URL必须以http或https开头", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 执行ConnectionParser.parseConnection(...) 以解析URL
                ConnectionParser.parseConnection(url, new ConnectionParser.ConnectionListener() {
                    @Override
                    public void onConnectionSuccess(String url) {
                        // 解析成功，显示text_jiexi_channel，并设置频道名称
                        text_jiexi_channel.setVisibility(View.VISIBLE);
                        text_jiexi_channel.setText("频道名称: " + feed.getChannel());
                        // 设置频道名称到RssFeed对象中
                        channel.setText("频道名称: " + feed.getChannel());
                    }

                    @Override
                    public void onConnectionTimeout() {
                        // 解析超时，通知用户
                        handler.sendEmptyMessage(TIMEOUT_MESSAGE);
                    }
                });
            }
        });

        text_jiexi_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在text_jiexi_channel点击事件中跳转到主活动
                if (url != null) {
                    Intent intent = new Intent(AddConnection.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("url", url);
                    channel.setText(feed.getChannel());
                    startActivity(intent);
                }
            }
        });

    }
}