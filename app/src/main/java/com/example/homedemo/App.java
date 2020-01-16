package com.example.homedemo;

import android.app.Application;

import com.example.homedemo.data.LocalDataBase;

/**
 * Created by Tomdog on 2020/1/15.
 */
public class App extends Application {

    private static LocalDataBase localDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        localDataBase = LocalDataBase.getInstance(getApplicationContext());
    }


    public static LocalDataBase getLocalDataBase(){
        return localDataBase;
    }


}
