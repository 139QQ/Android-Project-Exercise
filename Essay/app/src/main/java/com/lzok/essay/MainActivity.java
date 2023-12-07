package com.lzok.essay;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.lzok.essay.Util.RealPathUtil.getRealPathFromURIAPI11to18;
import static com.lzok.essay.Util.RealPathUtil.getRealPathFromURIAPI19;
import static com.lzok.essay.Util.RealPathUtil.getRealPathFromURIBelowAPI11;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lzok.essay.Util.RealPathUtil;
import com.lzok.essay.databinding.FormatMenuBinding;
import com.lzok.essay.interfacex.FormatActions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends AppCompatActivity implements FormatActions {
    RichEditor richEditor;
    ImageView formatIcon , insert;
    private PopupWindow popupWindow;
    private FormatMenuBinding binding;
    private static final int IMAGE_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        richEditor  =findViewById(R.id.editor);
        richEditor.setEditorFontSize(22);
        formatIcon = findViewById(R.id.format_ic);
        insert = findViewById(R.id.insert_image);


        binding = FormatMenuBinding.inflate(getLayoutInflater());
        popupWindow = new PopupWindow(binding.getRoot());
        binding.bold.setOnClickListener(new FormatClick(richEditor,FormatActions.BOLD));
        binding.italic.setOnClickListener(new FormatClick(richEditor,FormatActions.ITALIC));
        binding.subscript.setOnClickListener(new FormatClick(richEditor,FormatActions.SUBSCRIPT));
        binding.superscript.setOnClickListener(new FormatClick(richEditor,FormatActions.SUPERSCRIPT));
        binding.strikethrough.setOnClickListener(new FormatClick(richEditor,FormatActions.STRIKETHROUGH));
        binding.underline.setOnClickListener(new FormatClick(richEditor,FormatActions.UNDERLINE));
        binding.justifyLeft.setOnClickListener(new FormatClick(richEditor,FormatActions.JUSTIFY_LEFT));
        binding.justifyCenter.setOnClickListener(new FormatClick(richEditor,FormatActions.JUSTIFY_CENTER));
        binding.justifyRight.setOnClickListener(new FormatClick(richEditor,FormatActions.JUSTIFY_RIGHT));
        binding.blockquote.setOnClickListener(new FormatClick(richEditor,FormatActions.BLOCKQUOTE));
        binding.H1.setOnClickListener(new FormatClick(richEditor,FormatActions.HEADING_1));
        binding.H2.setOnClickListener(new FormatClick(richEditor,FormatActions.HEADING_2));
        binding.H3.setOnClickListener(new FormatClick(richEditor,FormatActions.HEADING_3));
        binding.indent.setOnClickListener(new FormatClick(richEditor,FormatActions.INDENT));
        binding.outdent.setOnClickListener(new FormatClick(richEditor,FormatActions.OUTDENT));

        formatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFormatMenu();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开图库
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });
    }
    private void showFormatMenu(){
        // 设置PopupWindow布局
//        View view = getLayoutInflater().inflate(R.layout.format_menu, null);
//        popupWindow.setContentView(view);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = 100;
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);

        // 设置点击外部可消失
        popupWindow.setOutsideTouchable(true);
        // 显示PopupWindow
        popupWindow.showAsDropDown(formatIcon, 0, 0);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String uri = String.valueOf(data.getData());

            richEditor.insertImage(uri, "image.jpg",300,300);
        }

    }
}
