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
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class MainActivity extends AppCompatActivity {


    private ViewStub expandedViewStub,collapsedViewStub;
    private boolean isExpandedViewInflated = false;
    private boolean isCollapsedViewInflated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandedViewStub = findViewById(R.id.expanded_stub);
        collapsedViewStub = findViewById(R.id.collapsed_stub);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        final View[] expandedView = new View[1];
        final View[] collapsedView = new View[1];
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (expandedView[0] != null) {
                        expandedView[0].setVisibility(View.GONE);
                    }
                    if (collapsedView[0] == null) {
                        collapsedView[0] = collapsedViewStub.inflate();
                    }
                    collapsedView[0].setVisibility(View.VISIBLE);
                } else if (verticalOffset == 0) {
                    if (collapsedView[0] != null) {
                        collapsedView[0].setVisibility(View.GONE);
                    }
                    if (expandedView[0] == null) {
                        expandedView[0] = expandedViewStub.inflate();
                    }
                    expandedView[0].setVisibility(View.VISIBLE);
                }
                MainActivity.this.onOffsetChanged();
            }

        });
    }
    private void onOffsetChanged() {
        // 使用标志位判断是否需要inflate
        if(!isCollapsedViewInflated){
            // inflate collapsed view
        }

        if(!isExpandedViewInflated){
            // inflate expanded view
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}