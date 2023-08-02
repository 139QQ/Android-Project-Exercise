package com.lzok.materialdesign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lzok
 *
 */
public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    FloatingActionButton fab;
    List<Fruit> fruitsList = new ArrayList<>();
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
//        设置悬浮按钮的点击监听
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "这是悬浮按钮", Toast.LENGTH_SHORT).show();替换为 Snackbar
                Snackbar.make(v,"这是消息提醒",Snackbar.LENGTH_SHORT)
                        .setAction("动作", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "跳过", Toast.LENGTH_SHORT).show();

                            }
                        }).show();
            }
        });
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
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//      调用方法
        initFruits();
//        获取RecyclerView 视图的id
        RecyclerView recyclerView  =findViewById(R.id.recycler);
//      设置布局样式网格布局
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2);
//        设置布局管理器，将布局样式放到形参中
        recyclerView.setLayoutManager(linearLayoutManager);
//        设置适配器
        recyclerView.setAdapter(new FruitAdapter(fruitsList));

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
/**
 * 将填充的数据和描述封装在一个方法中
 *
  */

    private void initFruits(){
        fruitsList.clear();

        Fruit[] fruits ={new Fruit("神秘果",R.drawable.mysticfruit)
                ,new Fruit("刺梨",R.drawable.pricklypear)
                ,new Fruit("黑加仑",R.drawable.blackcurrant)
                ,new Fruit("醋栗",R.drawable.gooseberry)
                ,new Fruit("蓝靛果",R.drawable.indigo)};


        Random random = new Random();
        for (int i = 0; i <= 100; i++) {
            int index = random.nextInt(fruits.length);
            fruitsList.add(fruits[index]);
        }

    }
}