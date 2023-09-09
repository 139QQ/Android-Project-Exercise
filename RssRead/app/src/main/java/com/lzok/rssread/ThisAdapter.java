package com.lzok.rssread;






import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
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

    private List<RssFeed> rssFeedList;
    private Context context;

    public ThisAdapter(List<RssFeed> rssFeedList) {
        this.rssFeedList = rssFeedList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(view);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RssFeed rssFeed = rssFeedList.get(position);

        // 根据rssFeed中的数据设置标题、作者和时间
        holder.text_title.setText(rssFeed.getTitle());
        holder.text_author.setText("作者"); // 你需要设置正确的作者信息

        SimpleDateFormat gmtDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            // 解析GMT时间字符串为Date对象
            Date gmtDate = gmtDateFormat.parse(rssFeed.getPubDate().toString()); // 将Date对象转换为String
            // 创建一个SimpleDateFormat，用于将时间显示为中国时区的格式
            SimpleDateFormat chinaDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");
            chinaDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            String chinaTime = chinaDateFormat.format(gmtDate);
            holder.text_time.setText("更新时间： " + chinaTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 在RecyclerView项目点击监听器中
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取点击的项目
                RssFeed clickedItem = rssFeedList.get(holder.getAdapterPosition());

                // 创建一个Intent来启动ReadActivity
                Intent intent = new Intent(v.getContext(), ReadActivity.class);

                // 将点击的RssFeed对象传递给ReadActivity
                intent.putExtra("rssFeed", clickedItem);

                // 启动ReadActivity
                v.getContext().startActivity(intent);
            }
        });

    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return rssFeedList.size();
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


