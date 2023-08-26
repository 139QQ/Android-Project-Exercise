package com.lzok.imagerapplication;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.lzok.imagerapplication.SteramTool;
import java.net.URLConnection;

public class GetData {
//    定义一个获取网络图片的方法
    public static byte[] getIamge(String path)throws Exception{
//        获取连接
        URL url = new URL(path);
//        打开连接
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//        连接超时
        urlConnection.setConnectTimeout(2000);
//        设置请求类型为Get类型
        urlConnection.setRequestMethod("GET");
//        判断请求是否成功
    if (urlConnection.getResponseCode() !=200){
            throw new RuntimeException("请求失败") ;
        }
        InputStream inStream = urlConnection.getInputStream();
        byte[] bt = SteramTool.red(inStream);
        inStream.close();
        return bt;
    }
    public static String getHtml(String path)throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(20000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            byte[] data = SteramTool.red(inputStream);
            String html = new String(data, "UTF-8");
            return html;
        }
        return null;
    }
}
