package com.lzok.imagerapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import android.os.Handler;
import android.widget.Toast;

/**
 * @author lzok
 */
public class MainActivity extends AppCompatActivity {
    private TextView txtMenu, txtshow;
    private ImageView imgPic;
    private WebView webView;
    private ScrollView scroll;
    private Bitmap bitmap;
    private String detail = "";
    private boolean flag = false;
    private final static String PIC_URL = "https://cn.bing.com/images/search?view=detailV2&ccid=dU6Y6jUE&id=6D88301D79F0A57D0FD2EE92EA502462EC1016F5&thid=OIP.dU6Y6jUEsDD3k_tU1-pmmAHaEK&mediaurl=https%3a%2f%2fpic3.zhimg.com%2fv2-27776a6b6d1a30661f212c00e6e4597e_r.jpg&exph=1080&expw=1920&q=%e5%bf%85%e5%ba%94%e5%9b%be%e7%89%87&simid=608031455408637008&FORM=IRPRST&ck=38963E4F7682FDD9DDDA97FFB36EF3FD&selectedIndex=0&idpp=overlayview&ajaxhist=0&ajaxserp=0";
    private final static String HTML_URL = "https://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private Handler handler =new Handler(){
        public void handelMessage(android.os.Message message){
            switch (message.what) {
                case 0x001:
                    hideAllWidget();
                    imgPic.setVisibility(View.VISIBLE);
                    imgPic.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "图片加载完成", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    hideAllWidget();
                    scroll.setVisibility(View.VISIBLE);
                    txtshow.setText(detail);
                    Toast.makeText(MainActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    hideAllWidget();
                    webView.setVisibility(View.VISIBLE);
                    webView.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
                    Toast.makeText(MainActivity.this, "网页加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            }
        };

    private void hideAllWidget() {
        imgPic.setVisibility(View.GONE);
        scroll.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
        switch (item.getItemId()){
            case R.id.one:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            byte[] date = GetData.getIamge(PIC_URL);
                            bitmap = BitmapFactory.decodeByteArray(date, 0, date.length);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
        }
    }
}

