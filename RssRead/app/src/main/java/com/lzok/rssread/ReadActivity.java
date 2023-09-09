package com.lzok.rssread;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.lzok.rssread.Data.RssFeed;
import com.lzok.rssread.Util.HtmlDownloader;

public class ReadActivity extends AppCompatActivity {
    MaterialToolbar title;
    RssFeed rssFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        title = findViewById(R.id.ma_toolbar);

        // 在主线程中获取传递的RssFeed
        Intent intent = getIntent();
        rssFeed = (RssFeed) intent.getSerializableExtra("rssFeed");

        // 创建并执行异步任务
        new LoadRssTask().execute();
    }

    private class LoadRssTask extends AsyncTask<Void, Void, RssFeed> {
        @Override
        protected RssFeed doInBackground(Void... voids) {
            return rssFeed;
        }

        @Override
        protected void onPostExecute(RssFeed rssFeed) {
            // 在这里更新UI
            if (rssFeed != null) {
                String description = rssFeed.getTitle();
                title.setTitle(description);
            }
        }
    }
}