package com.lzok.imagerapplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SteramTool {
    public static byte[] red(InputStream inputStream)throws Exception   {
        ByteArrayOutputStream outSteram = new ByteArrayOutputStream();
      byte[] buffer=  new byte[1024];

      int len =0;
      while ((len = inputStream.read(buffer)) != -1){
          outSteram.write(buffer,0,len);

      }
      inputStream.close();
      return outSteram.toByteArray();
    }
}
