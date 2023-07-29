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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

/**
 * @author lzok
 *
 */
public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FloatingActionButton fab;
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
        NavigationView navView = findViewById(R.id.nav_view);
        fab = findViewById(R.id.fab);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar !=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);

        }
//      设置导航项目选定监听器
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                当点击某个item 时提示
                if (item.getItemId() == R.id.nac_call){
                    Toast.makeText(MainActivity.this, "电话", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.nac_friends) {
                    Toast.makeText(MainActivity.this, "朋友", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == R.id.nac_location) {
                    Toast.makeText(MainActivity.this, "位置", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "工作", Toast.LENGTH_SHORT).show();
                }
//                关闭侧滑菜单
                drawerLayout.closeDrawers();

                return true;
            }
        });
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