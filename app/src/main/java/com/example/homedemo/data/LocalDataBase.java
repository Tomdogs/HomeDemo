package com.example.homedemo.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.homedemo.data.dao.UserDao;
import com.example.homedemo.data.entity.User;

/**
 * Created by Tomdog on 2020/1/15.
 */
@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class LocalDataBase extends RoomDatabase {

    private static final String DB_NAME = "HomeDemoDatabase.db";
    private static volatile LocalDataBase instance;

    public static LocalDataBase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context,LocalDataBase.class,DB_NAME).build();
        }
        Log.i("数据库：",instance+"");
        return instance;
    }

    public abstract UserDao userDao();
}
