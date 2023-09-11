package com.lzok.rssread;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import com.lzok.rssread.Data.RssFeed;
import com.lzok.rssread.Util.HtmlHelper;


public class ReadActivity extends AppCompatActivity {


    MaterialToolbar title;
    RssFeed rssFeed;
    TextView dest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        title = findViewById(R.id.material_toolbar);

        dest =findViewById(R.id.desp_text);
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
                // 在后台线程中加载图像
                HtmlHelper.loadHtmlContent(dest,rssFeed.getDescription());

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}