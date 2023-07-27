package com.lzok.materialdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        获取顶部导航栏id
        Toolbar toolbar = findViewById(R.id.materialToolbar);
//        设置toolbar的标题
        toolbar.setTitle("这是ToolBar");
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar !=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);

        }
//        设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.WHITE);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() ==R.id.backup){
            Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.delete){
            Toast.makeText(this, "You clicked delete", Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.setting){
            Toast.makeText(this, "You clicked settings", Toast.LENGTH_SHORT).show();
        }else {
            drawerLayout.openDrawer(GravityCompat.START);

        }
        return true;

    }
}