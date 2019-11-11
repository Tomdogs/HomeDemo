package com.example.homedemo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ToolUtils {
  /**
   * 手机
   *
   * @param mobiles
   *            手机
   * @return 真假
   */
  public static boolean isMobile(String mobiles) {
    Pattern p = Pattern
        .compile("(^[1][3][0-9]{9}$)|(^[1][5][0-9]{9}$)|(14[57]\\d{8})|(17[06789]\\d{8})|(^[1][8][0-9]{9}$)");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }
  /**
   * 获取日期 * @return String
   */
  public static String getCurrentTime() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      Date date = new Date();
      String c = df.format(date);
      return c;

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  /**
   * 获取日期 * @return String
   */
  public static String getCurrentDate() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date date = new Date();
      String c = df.format(date);
      return c;

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
  /**
   * 判断是否联网
   *
   * @param act
   *            Activity
   * @return 是否成功
   */
  public static boolean isNetworkConnected(Context act) {

    ConnectivityManager manager = (ConnectivityManager) act
        .getApplicationContext().getSystemService(
            Context.CONNECTIVITY_SERVICE);

    if (manager == null) {
      return false;
    }

    NetworkInfo networkinfo = manager.getActiveNetworkInfo();

    if (networkinfo == null || !networkinfo.isAvailable()) {
      return false;
    }

    return true;
  }
  /**
   * 获取本地软件版本名称
   */
  public static String getLocalVersionName(Context ctx) {
    String localVersion = "";
    try {
      PackageInfo packageInfo = ctx.getApplicationContext()
          .getPackageManager()
          .getPackageInfo(ctx.getPackageName(), 0);
      localVersion = packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return localVersion;
  }

  /**
   * 获取当前软件版本号
   */
  public static int getLocalVersion(Context ctx) {
    int localVersion = 0;
    try {
      PackageInfo packageInfo = ctx.getApplicationContext()
              .getPackageManager()
              .getPackageInfo(ctx.getPackageName(), 0);
      localVersion = packageInfo.versionCode;
      ILog.e("当前版本号：" + localVersion);

    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return localVersion;
  }
}
