package com.lzok.appbarlayouttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView collapsingText;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        window.setStatusBarColor(Color.TRANSPARENT);

        AppBarLayout appbarLayout = findViewById(R.id.dd);
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f)/appBarLayout.getTotalScrollRange());
            }
        });
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);

        collapsingToolbarLayout.setTitle("My Collapsing Toolbar");
        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();

                if (Math.abs(verticalOffset) == totalScrollRange) {
                    // Fully collapsed
                    collapsingToolbarLayout.setTitle("Collapsed Title");
                    // Hide or show your content views here
                } else if (verticalOffset == 0) {
                    // Fully expanded
                    collapsingToolbarLayout.setTitle("Expanded Title");
                    // Hide or show your content views here
                } else {
                    // In between states
                    collapsingToolbarLayout.setTitle("Title");
                    // Hide or show your content views here
                }
            }
        });



    }
}