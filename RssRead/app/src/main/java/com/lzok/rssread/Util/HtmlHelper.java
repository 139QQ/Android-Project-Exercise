package com.lzok.rssread.Util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lzok
 * @description 将加载图片和处理超链接的功能添加在该类中
 */
public class HtmlHelper {
    private static final int corePoolSize = 5;
    private static final int maxPoolSize = 10;
    private static final long keepAliveTime = 60L;
    private static final ExecutorService executorService = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>()
    );

    public static void loadHtmlContent(final TextView textView, final String htmlContent) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                final Spanned spanned = Html.fromHtml(htmlContent, new Html.ImageGetter() {
                    @Override
                    public Drawable getDrawable(String source) {
                        try {
                            // 使用 Picasso 加载图片
                            InputStream is = new URL(source).openStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            Drawable drawable = new BitmapDrawable(textView.getResources(), bitmap);

                            // 设置图片的宽度和高度
                            int desiredWidth = drawable.getIntrinsicWidth(); // 你希望的图片宽度
                            int desiredHeight = drawable.getIntrinsicHeight(); // 你希望的图片高度
                            drawable.setBounds(0, 0, desiredWidth, desiredHeight);

                            return drawable;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                }, new Html.TagHandler() {
                    /**
                     * @param opening
                     * @param tag
                     * @param output
                     * @param xmlReader
                     */
                    @Override
                    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                        // 处理自定义标签
                        if ("a".equalsIgnoreCase(tag)) {
                            if (opening) {
                                // 处理超链接开始标签
                                final String href = getHrefAttribute(xmlReader);
                                final int len = output.length();
                                output.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        // 处理超链接的点击事件
                                        if (href != null) {
                                            // 打开链接
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
                                            textView.getContext().startActivity(intent);
                                        }
                                    }
                                }, len, len, Spannable.SPAN_MARK_MARK);
                            } else {
                                // 处理超链接结束标签
                                int len = output.length();
                                Object obj = getLast(output, ClickableSpan.class);
                                int where = output.getSpanStart(obj);
                                output.removeSpan(obj);
                                if (where != len) {
                                    output.setSpan(new URLSpan("https://www.example.com"), where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                }
                            }
                        }
                    }



                    private String getHrefAttribute(XMLReader xmlReader) {
                        String href = null;
                        try {
                            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
                            elementField.setAccessible(true);
                            Object element = elementField.get(xmlReader);
                            Field attsField = element.getClass().getDeclaredField("theAtts");
                            attsField.setAccessible(true);
                            Object atts = attsField.get(element);
                            Method getValueMethod = atts.getClass().getMethod("getValue", String.class);
                            Method getLengthMethod = atts.getClass().getMethod("getLength");
                            int len = (int) getLengthMethod.invoke(atts);
                            for (int i = 0; i < len; i++) {
                                String attributeName = (String) getValueMethod.invoke(atts, i);
                                if ("href".equalsIgnoreCase(attributeName)) {
                                    href = (String) getValueMethod.invoke(atts, i + 1);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return href;
                    }

                    private <T> T getLast(Spanned text, Class<T> kind) {
                        Object[] spans = text.getSpans(0, text.length(), kind);
                        if (spans.length == 0) {
                            return null;
                        } else {
                            for (int i = spans.length - 1; i >= 0; i--) {
                                if (text.getSpanFlags(spans[i]) == Spannable.SPAN_MARK_MARK) {
                                    return (T) spans[i];
                                }
                            }
                            return null;
                        }
                    }

                });
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(spanned);
                        textView.setMovementMethod(LinkMovementMethod.getInstance()); // 设置TextView支持超链接点击
                    }
                });
            }
        });
    }
}
