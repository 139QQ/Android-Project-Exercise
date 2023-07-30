package com.lzok.materialdesign;

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
        String HeiJiaLun = "黑加仑，即黑茶藨子，果实近圆形，熟时黑色，性喜光、耐寒，生于湿润谷底、沟边或坡地云杉林、落叶松林或针、阔混交林下，适宜在中国北方寒冷地区培植。" +
                "黑加仑主要分布于中国黑龙江、内蒙古及新疆等省份，欧洲、俄罗斯、蒙古和朝鲜北部也有分布。";
         String ciLi ="即刺果茶藨子，是虎耳草科茶藨子属植物，果实圆球形，直径约一厘米，具多数黄褐色小刺，味酸，可供食用，但以制作果汁和果酒为宜。" +
                "刺梨主要分布于中国黑龙江、吉林、辽宁、内蒙古、河北、山西、陕西、甘肃、河南等地区，蒙古、朝鲜、俄罗斯远东地区也有分布";
        Fruit[] fruits ={new Fruit("神秘果",R.drawable.mysticfruit,"")
                ,new Fruit("刺梨",R.drawable.pricklypear,ciLi)
                ,new Fruit("黑加仑",R.drawable.blackcurrant, HeiJiaLun)
                ,new Fruit("醋栗",R.drawable.gooseberry,"")
                ,new Fruit("蓝靛果",R.drawable.indigo,"")};


        Random random = new Random();
        for (int i = 0; i <= 100; i++) {
            int index = random.nextInt(fruits.length);
            fruitsList.add(fruits[index]);
        }

    }
}