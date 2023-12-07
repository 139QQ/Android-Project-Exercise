package com.lzok.essay.Util;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class RealPathUtil {


    /**
     * SDK < 11
     * @param context
     * @return 文件路径
     */
    public static String getRealPathFromURIBelowAPI11(Context context, Uri uri) {

        String filePath = null;

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            if (columnIndex >= 0) {
                filePath = cursor.getString(columnIndex);
            } else {
                // 列名不存在,添加日志或其他处理
            }
            cursor.close();
        }
        return filePath;
    }


        /**
         * SDK >= 11 && SDK < 19
         * @param context
         * @param uri
         * @return
         */
        public static String getRealPathFromURIAPI11to18(Context context, Uri uri){
            String filePath = null;
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();

            if(cursor != null){
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                if (columnIndex >= 0) {
                    filePath = cursor.getString(columnIndex);
                } else {
                    // 处理索引为-1的情况
                }
                cursor.close();
            }
            return filePath;
        }

    /**
     * SDK > 19 (Android 4.4)
     * @param context
     * @param uri
     * @return
     */
    public static String getRealPathFromURIAPI19(Context context, Uri uri){
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = { MediaStore.Images.Media.DATA };

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{ id }, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }
        return filePath;
    }

}
