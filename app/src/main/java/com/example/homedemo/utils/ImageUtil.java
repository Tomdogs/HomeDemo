package com.example.homedemo.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/11/23 0023.
 */

public class ImageUtil {
    private static final String TAG = "ImageUtil";

    /**
     * 根据图片uri获取本地图片文件路径
     *
     * @param context Context
     * @param uri     本地图片uri
     * @return 图片完整路径
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String handleImage(Context context, @Nullable Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri conuri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(context, conuri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            path = getImagePath(context, uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path = uri.getPath();
        }
        return path;
    }

    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver()
                .query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                path = cursor.getString(index);
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 根据文件路径获取uri
     *
     * @param context Context
     * @param path    文件完整路径
     * @return 对应uri
     */
    public static Uri getUriFormFile(Context context, String path) {
        Uri uri;
        File file = new File(path);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(
                    context,
                    context.getApplicationInfo().processName + ".fileprovider",
                    file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 采样率压缩图片
     *
     * @param path      图片完整路径
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap compressImageBySample(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //计算采样率
        options.inSampleSize = caclculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int caclculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSamplesize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSamplesize) >= reqHeight
                    && (halfWidth / inSamplesize) >= reqWidth) {
                inSamplesize *= 2;
            }
        }
        return inSamplesize;
    }


    /**
     * 质量压缩
     *
     * @param image bitmap
     * @return 压缩后图片文件路径
     */
    public static File compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;

        while (baos.toByteArray().length / 1024 > 700) {  //循环判断如果压缩后图片是否大于700kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 20;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }

        String path = SDFileConfig.getAppSDKPath() + "/compress_own/picture_" + System.currentTimeMillis() + ".jpg";
        File file = new File(path);//压缩后文件
        if (!file.getParentFile().exists()) {//创建父文件夹
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static File compressImage2(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;

        do {
            baos.reset();//重置baos即清空baos
            quality -= 50;//每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);//这里压缩options%，把压缩后的数据存放到baos中
        } while (baos.toByteArray().length / 1024 > 700);

        String path = SDFileConfig.getAppSDKPath() + "/compress_own/picture_" + System.currentTimeMillis() + ".jpg";
        File file = new File(path);//压缩后文件
        if (!file.getParentFile().exists()) {//创建父文件夹
            file.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }
}
