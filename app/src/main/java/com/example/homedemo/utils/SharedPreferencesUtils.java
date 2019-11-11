package com.example.homedemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
* @Description:做本地缓存的工具类，可以存储int型和
* @author  作者 :likun
*/
public class SharedPreferencesUtils {
    private static SharedPreferences mSharedPreferences;


    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putString(Context context, String key, String values) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                .putString(key, values)
                .apply();
    }


    /**
     * @param context
     * @param key
     * @param defaultValues
     * @return
     */
    public static String getString(Context context, String key, String defaultValues) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getString(key, defaultValues);
    }


    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putInt(Context context, String key, int values) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                .putInt(key, values)
                .apply();
    }


    /**
     * @param context
     * @param key
     * @param defaultValues
     * @return
     */
    public static int getInt(Context context, String key, int defaultValues) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt(key, defaultValues);
    }

    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putLong(Context context, String key, long values) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                .putLong(key, values)
                .apply();
    }


    /**
     * @param context
     * @param key
     * @param defaultValues
     * @return
     */
    public static long getLong(Context context, String key, long defaultValues) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getLong(key, defaultValues);
    }


    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putBoolean(Context context, String key, boolean values) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                .putBoolean(key, values)
                .apply();
    }


    /**
     * @param context
     * @param key
     * @param defaultValues
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValues) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getBoolean(key, defaultValues);
    }

    /**
     * @param context
     * @param key
     * @param values
     */
    public static void putFloat(Context context, String key, float values) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit()
                .putFloat(key, values)
                .apply();
    }


    /**
     * @param context
     * @param key
     * @param defaultValues
     * @return
     */
    public static float getFloat(Context context, String key, float defaultValues) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getFloat(key, defaultValues);
    }
}
