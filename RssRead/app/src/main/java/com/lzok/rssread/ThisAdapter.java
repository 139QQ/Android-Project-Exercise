package com.lzok.rssread;






import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzok.rssread.Data.RssFeed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author lzok
 * @description RecyclerView 绑定
 */
public class ThisAdapter extends RecyclerView.Adapter<ThisAdapter.ViewHolder>{

    private List<RssFeed.Item> rssItems;


    public ThisAdapter(List<RssFeed.Item> rssItems) {
        this.rssItems = rssItems;
    }

    /**
     * @param parent   新视图绑定后将被添加到其中的ViewGroup
     *                                  适配器位置。
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(inflate);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RssFeed.Item rssItem = rssItems.get(position);
        // 根据rssFeed中的数据设置标题、作者和时间
        holder.text_title.setText(rssItem.getTitle());
        // 从rssItem设置作者数据
        holder.text_author.setText("author");
//        将获取的时间进行转换
        SimpleDateFormat gmtDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            // 解析GMT时间字符串为Date对象
            Date gmtDate = gmtDateFormat.parse(rssItem.getPubDateAsDate());
            // 创建一个SimpleDateFormat，用于将时间显示为中国时区的格式
            SimpleDateFormat chinaDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");
//            设置时区
            chinaDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            String chinaTime = chinaDateFormat.format(gmtDate);
            //设置时间
            holder.text_time.setText("更新时间： "+chinaTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return rssItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        TextView text_author;
        /**
         * 最后更新时间
         */
        TextView text_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.text_title);
            text_time = itemView.findViewById(R.id.text_time);
            text_author = itemView.findViewById(R.id.text_author);

        }
    }


}


