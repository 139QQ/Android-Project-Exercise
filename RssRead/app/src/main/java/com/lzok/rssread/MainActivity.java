package com.lzok.rssread;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lzok.rssread.Data.DataPersistenceManager;
import com.lzok.rssread.Data.RssFeed;
import com.lzok.rssread.Util.HtmlDownloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzok
 */
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton floating;
    RssFeed rssFeed = new RssFeed();
    DrawerLayout drawerLayout ;
    List<RssFeed> rssItems = rssFeed.getItems();
    private DataPersistenceManager dataPersistenceManager = null;
    TextView text_hint,like;
    NestedScrollView nestedScrollView ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        floating = findViewById(R.id.fab);
        text_hint = findViewById(R.id.text_hint);
        nestedScrollView = findViewById(R.id.nested_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        dataPersistenceManager = DataPersistenceManager.getInstance(this);

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddConnection.class);
                startActivity(intent);

            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.setting){
                    Toast.makeText(MainActivity.this, "dd", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });


        new DownloadRssFeedTask().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_item, menu);
        return true;
    }


    private class DownloadRssFeedTask extends AsyncTask<Void, Void, RssFeed> {
        Intent intent = getIntent();

        @Override
        protected RssFeed doInBackground(Void... params) {
            HtmlDownloader htmlDownloader = new HtmlDownloader();
            String url = null;

            // 尝试从DataPersistenceManager获取最后一次打开的连接
            List<String> savedLinks = dataPersistenceManager.getChannelLinks();
            if (savedLinks != null && !savedLinks.isEmpty()) {
                // 如果有保存的链接，选择最后一个链接
                url = savedLinks.get(savedLinks.size() - 1);
                Log.i(TAG, "Last opened link: " + url);
            } else {
                // 如果没有保存的链接，尝试从Intent获取
                Intent intent = getIntent();
                url = intent.getStringExtra("url");
                // 如果从Intent中获取到链接，将其保存为最后一次打开的连接
                if (url != null && !url.isEmpty()) {
                    dataPersistenceManager.addChannelLink(url);
                    Log.i(TAG, "OpenedLink: "+dataPersistenceManager);
                }
            }
            if (url == null || url.isEmpty()) {
                return new RssFeed();
            }
            // 下载新的 RSS 数据
            RssFeed newRssFeed = htmlDownloader.downloadRssFeed(url);

            if (newRssFeed != null) {
                // 将新的 RSS 数据添加到数据源
                rssItems.clear();
                rssItems.addAll(newRssFeed.getItems());
            }



            return newRssFeed;
        }

        @Override
        protected void onPostExecute(RssFeed rssFeed) {
            if (rssFeed != null) {
                // 更新RecyclerView或其他UI组件
                text_hint.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.VISIBLE);
                RecyclerView recyclerView = findViewById(R.id.recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                ThisAdapter adapter = new ThisAdapter(rssFeed.getItems()); // 使用正确的数据源 rssFeed.getItems()
                recyclerView.setAdapter(adapter);

                // 设置标题
                toolbar.setTitle(rssFeed.getChannel());
                Log.i(TAG, "onPostExecute: " + rssFeed.getChannel());


            } else {
                // 处理下载失败的情况，例如显示错误消息
            }
        }
    }
}