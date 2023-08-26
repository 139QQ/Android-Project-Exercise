package com.lzok.weatherwise;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;

import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

public class SevenAdapter extends RecyclerView.Adapter<SevenAdapter.ViewHolder> {
    private List<Time> timeList;
    private Context context;

    public SevenAdapter(List<Time> timeList) {
        this.timeList = timeList;

    }

    public void updateData(List<Time> timeTemp) {
        // 清空原有数据
        this.timeList.clear();


        // 添加新的数据
        this.timeList.addAll(timeTemp);

        // 通知数据变更
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text_24hour_time,rec_text_temp,text_humidity_level,text_fig;
        public ImageView temp_roundabout,ico_climatic;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            text_24hour_time = itemView.findViewById(R.id.text_24hour_time);
            ico_climatic =itemView.findViewById(R.id.ico_climatic);
            rec_text_temp = itemView.findViewById(R.id.rec_text_temp);
            text_humidity_level = itemView.findViewById(R.id.text_humidity_level);
            text_fig =itemView.findViewById(R.id.text_fig);
            temp_roundabout =itemView.findViewById(R.id.temp_roundabout);

            // 获取父布局的布局参数
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) text_24hour_time.getLayoutParams();

            // 设置 sevenTimeTextView 之间的左右间距（示例：16dp）
            int horizontalMargin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.seven_time_horizontal_margin);
            layoutParams.leftMargin = horizontalMargin;
            layoutParams.rightMargin = horizontalMargin;

            text_24hour_time.setLayoutParams(layoutParams);
            Log.i(TAG, "ViewHolder: " + text_humidity_level);
        }
    }

    @NonNull
    @Override
    public SevenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.titme_item, parent, false);


        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SevenAdapter.ViewHolder holder, int position) {
        Time time = timeList.get(position);

        String sevenTime = time.getSevenTime();
        Log.i(TAG, "onBindViewHolder: 获取时间" +sevenTime);
        if(!TextUtils.isEmpty(sevenTime)) {
            holder.text_24hour_time.setText(sevenTime);
        } else {
            holder.text_24hour_time.setText("时间未获取");
        }
        holder.text_24hour_time.setText(time.getSevenTime());
        holder.rec_text_temp.setText(time.getTemperature()+ "℃");

        holder.ico_climatic.setImageDrawable(time.getIconDrawable());
        holder.text_humidity_level.setText(time.getText_humidity_level() + "%");

        holder.text_fig.setText(time.getText_fig());


    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }


}
