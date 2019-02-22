package com.kesar.jetpackgank.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * AppDataBase
 *
 * @author andy <br/>
 * create time: 2019/2/22 11:36
 */
@Database(entities = {Gank.class}, version = 1, exportSchema = false)
@TypeConverters(value = {Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private final static String DATABASE_NAME = "gank-db";

    public abstract GankDao gankDao();

    private static AppDatabase sInstance = null;

    public static AppDatabase getInstance(Context context) {
        if(sInstance == null) {
            synchronized (AppDatabase.class) {
                if(sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, DATABASE_NAME).build();
    }
}
