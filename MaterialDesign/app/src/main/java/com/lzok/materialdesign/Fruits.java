package com.lzok.materialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;


import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class Fruits extends AppCompatActivity {

    public static final String FRUITS_NAME = "fruits_name";
    public static final String FRUITS_IMAGE_ID = "fruits_image_id";
    CoordinatorLayout collapsing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        Intent intent = getIntent();
        String fruitName = intent.getStringExtra(FRUITS_NAME);
        int fruitImageId = intent.getIntExtra(FRUITS_IMAGE_ID,0);
        //        获取ToolBar的id
        Toolbar toolbar = findViewById(R.id.fruit_toolbar_coll);
        toolbar.setTitle(fruitName);

        setSupportActionBar(toolbar);
//        获取折叠工具类ID
        collapsing = findViewById(R.id.collapsing_toolbr);
        ImageView fruitImageView = findViewById(R.id.fruit_image_view);
        TextView fruitContentText = findViewById(R.id.fruit_content_text);

//        动作
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        collapsing.setAccessibilityPaneTitle(fruitName);
        Glide.with(this).load(fruitImageId).into(fruitImageView);
        String fruitContent  =generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append(fruitName);
        }
        return  stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}