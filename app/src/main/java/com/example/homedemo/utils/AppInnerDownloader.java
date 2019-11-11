package com.example.homedemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by baijunfeng on 2017/10/23.
 */

public class AppInnerDownloader {

    public final static String SD_FOLDER = Environment.getExternalStorageDirectory()+ "/VersionChecker/";
    private static final String TAG = AppInnerDownloader.class.getSimpleName();

    /**a
     * 从服务器中下载APK
     */
    @SuppressWarnings("unused")
    public static void downLoadApk(final Context mContext, final String downURL, final String appName ) {

        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(mContext);
        pd.setCancelable(false);// 必须一直下载完，不可取消
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载安装包，请稍后...");
        pd.setTitle("版本升级");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = downloadFile(downURL,appName, pd);
                    sleep(3000);
                    installApk(mContext, file);
                    // 结束掉进度条对话框
                    pd.dismiss();
                } catch (Exception e) {
                    pd.dismiss();

                }
            }
        }.start();
    }

    /**
     * 从服务器下载最新更新文件
     *
     * @param path
     *            下载路径
     * @param pd
     *            进度条
     * @return
     * @throws
     */
    private static File downloadFile(String path,String appName ,ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            pd.setProgressNumberFormat("%1d Mb/%2d Mb");

            Log.d(TAG,"安装包大小："+conn.getContentLength());
            // 获取到文件的大小
//            pd.setMax(conn.getContentLength());
            pd.setMax((conn.getContentLength()/1024)/1024);
            InputStream is = conn.getInputStream();
            String fileName = SD_FOLDER + appName+".apk";
            Log.d(TAG,"安装包路径："+fileName);
            File file = new File(fileName);
            // 目录不存在创建目录
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);

                total += len;
                // 获取当前下载量
                pd.setProgress(total/(1024*1024));
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            throw new IOException("未发现有SD卡");
        }
    }

    /**
     * 安装apk
     */
    private static void installApk(Context mContext, File file) {

        ILog.d("sdk_int = "+ Build.VERSION.SDK_INT + "Version_codes = "+Build.VERSION_CODES.N);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.fri.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            mContext.startActivity(intent);
            Log.d(TAG,"执行了");

        } else {
            Uri fileUri = Uri.fromFile(file);
            Intent it = new Intent();
            it.setAction(Intent.ACTION_VIEW);
            it.setDataAndType(fileUri, "application/vnd.android.package-archive");
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止打不开应用
            mContext.startActivity(it);

        }


    }

    /**
     * 获取应用程序版本（versionName）
     *
     * @return 当前应用的版本号
     */

    private static double getLocalVersion(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
            return 0.0;
        }

        return Double.valueOf(info.versionName);
    }
    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }
}
