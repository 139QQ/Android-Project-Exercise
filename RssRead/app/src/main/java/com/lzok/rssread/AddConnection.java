package com.lzok.rssread;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.search.SearchBar;



/**
 * @author lzok
 * @description 解析连接和历史记录
 */
public class AddConnection extends AppCompatActivity {

    ImageView back_image;
    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connection);
        back_image = findViewById(R.id.imageView_back);
        editText = findViewById(R.id.editText);
        button =findViewById(R.id.button_analysis);


//        返回上一个Activity
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String url = editText.getText().toString();

                if (TextUtils.isEmpty(url)) {
                    // URL为空
                    Toast.makeText(AddConnection.this, "请输入连接", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 支持http和https
                if (!url.matches("https?://.*")) {
                    Toast.makeText(AddConnection.this, "URL必须以http或https开头", Toast.LENGTH_SHORT).show();
                    return;
                }

                // URL不为空且满足https
                Intent intent = new Intent(AddConnection.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("url", url);
                Log.i(TAG, "onClick: "+url);
                startActivity(intent);

            }

        });

    }

}