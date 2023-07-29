package com.lzok.materialdesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.List;

/**
 * @author Administrator
 * Fruit类的适配器，用来填充和显示数据
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context mContext;
    private List<Fruit> mFruit;

    /**
     * 视图文件夹
     * 最好第一步就用来获取
     * 用来获取item中的id
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView fruitName;
        ImageView fruitImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            cardView = (CardView) itemView;
//            获取ID
            fruitName = itemView.findViewById(R.id.fruit_name);
            fruitImage = itemView.findViewById(R.id.fruit_image);
        }
    }
//    创建FruitAdapter构造器

    public FruitAdapter(List<Fruit> mFruit) {
        this.mFruit = mFruit;
    }

    /**
     *View指定是为RecyclerView显示item
     * @param parent 新 View 绑定到 一个适配器位置。
     *
     * @param viewType T新建视图的视图类型
     *
     * @return
     */
    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);

        return new ViewHolder(inflate);
    }

    /**
     *
     * @param holder 应更新的 ViewHolder，以表示数据集中给定位置上的项目内容。
     * @param position item在适配器数据集中的位置。
     */
    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {
//        将获取到的item放到 fruit中
        Fruit fruit = mFruit.get(position);
        holder.fruitName.setText(fruit.getName());

        holder.fruitImage.setBackgroundResource(R.drawable.add);
    }

    @Override
    public int getItemCount() {
        return mFruit.size();
    }


}
