package com.example.homedemo.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jingru on 2016/6/28.
 */
public class ILog {

    public static final String TAG = "lgd";

    @SuppressLint("LongLogTag")
    public static void i(Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                i("标签" + TAG + "的打印内容为空！");
            }
            Log.i(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void i(String tag, Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                i("标签" + TAG + "的打印内容为空！");
            }
            Log.i(tag, object.toString());
        }
    }

    @SuppressLint("LongLogTag")
    public static void d(Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                d("标签" + TAG + "的打印内容为空！");
            }
            Log.d(TAG, object.toString());
        }
    }

    @SuppressLint("LongLogTag")
    public static void d(String tag, Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                d("标签" + TAG + "的打印内容为空！");
            }
            Log.d(tag, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void e(Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                e("标签" + TAG + "的打印内容为空！");
            }
            Log.e(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void v(Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                v("标签" + TAG + "的打印内容为空！");
            }
            Log.v(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void w(Object object) {
        if (Config.DEBUG) {
            if (object == null) {
                w("标签" + TAG + "的打印内容为空！");
            }
            Log.w(TAG, object.toString());
        }
    }
    @SuppressLint("LongLogTag")
    public static void e(Object object, Throwable tr) {
        if (Config.DEBUG) {
            if (object == null) {
                e("标签" + TAG + "的打印内容为空！");
            }
            Log.e(TAG, object.toString(), tr);
        }
    }

    // 用于格式化日期,作为日志文件名的一部分
    private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    // 用于格式化日期,作为日志文件名的一部分
    private static DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HH");
    public static String saveLogFile(String str) {
        if (Config.DEBUG && isStringValid(str)) {
            final String MINENAVI_LOG = Environment.getExternalStorageDirectory().toString() + "/Log/";
            StringBuffer sb = new StringBuffer();
            sb.append(str);
            sb.append(" Time:" + format.format(new Date()) + "\n");
            try {
                String time = formatter.format(new Date());
                String fileName = "long_" + time + ".txt";
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    File dir = new File(MINENAVI_LOG);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(
                            (MINENAVI_LOG + fileName), true);
                    fos.write(sb.toString().getBytes());
                    fos.flush();
                    fos.close();
                }
                return fileName;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static boolean isStringValid(String string){
        if (string == null || string.trim().equals("")|| string.trim().equals("null")) {
            return false;
        }
        return true;
    }

}
