package com.lzok.rssread;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lzok.rssread.Data.RssFeed;
import com.lzok.rssread.Util.HtmlDownloader;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzok
 */
public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton floating;
    RssFeed rssFeed = new RssFeed();
   List<RssFeed> rssFeeds = new ArrayList<>();

    TextView text_hint;
    AppBarLayout app_bar;
    NestedScrollView nestedScrollView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        floating = findViewById(R.id.fab);
        text_hint = findViewById(R.id.text_hint);
        nestedScrollView = findViewById(R.id.nested_view);



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

        new DownloadRssFeedTask().execute();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_item, menu);
        return true;
    }


    private class DownloadRssFeedTask extends AsyncTask<Void, Void, RssFeed> {
        @Override
        protected RssFeed doInBackground(Void... params) {
            Intent intent = getIntent();

            HtmlDownloader htmlDownloader = new HtmlDownloader();
            String url = intent.getStringExtra("url");
            if (url == null){
                return new RssFeed();
            }
            text_hint.setVisibility(View.GONE);
//            app_bar.setVisibility(View .VISIBLE);
            nestedScrollView.setVisibility(View.VISIBLE);
            Log.i(TAG, "doInBackground: "+ url);

            return htmlDownloader.downloadRssFeed(url);
        }

        @Override
        protected void onPostExecute(RssFeed rssFeed) {
            if (rssFeed != null) {
                toolbar.setTitle(rssFeed.getChannel());
                Log.i(TAG, "onCreate: " + rssFeed.getChannel());
                // 更新RecyclerView或其他UI组件
                RecyclerView recyclerView = findViewById(R.id.recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                Log.i(TAG, "onPostExecute: "+rssFeed.getItems());
                ThisAdapter adapter = new ThisAdapter(rssFeed.getItems()); // 使用正确的数据源 rssFeed.getItems()

                recyclerView.setAdapter(adapter);
            } else {
                // 处理下载失败的情况，例如显示错误消息
            }
        }
    }
}