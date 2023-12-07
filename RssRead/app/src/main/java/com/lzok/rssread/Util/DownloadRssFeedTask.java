package com.lzok.rssread.Util;


import android.os.AsyncTask;
import com.lzok.rssread.Data.RssFeed;
import com.lzok.rssread.MainActivity;
import java.lang.ref.WeakReference;

/**
 * @author lzok
 */
//public class DownloadRssFeedTask extends AsyncTask<Void, Void, RssFeed> {
//    private final WeakReference<MainActivity> activityReference;
//
//    public DownloadRssFeedTask(MainActivity activity) {
//        this.activityReference = new WeakReference<>(activity);
//    }
//
//    @Override
//    protected RssFeed doInBackground(Void... params) {
//        MainActivity activity = activityReference.get();
//        if (activity != null) {
//            HtmlDownloader htmlDownloader = new HtmlDownloader();
//            // 获取 RSS Feed URL
//            String url = activity.getRssFeedUrl();
//            return htmlDownloader.downloadRssFeed(url);
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(RssFeed rssFeed) {
//        MainActivity activity = activityReference.get();
//        if (activity != null) {
//            activity.handleRssFeedResult(rssFeed);
//        }
//    }
//}
//
