package com.example.mymusicplayerapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mymusicplayerapplication.dao.SongDao;
import com.example.mymusicplayerapplication.data.model.Song;
import com.example.mymusicplayerapplication.data.model.SongEntity;
import com.example.mymusicplayerapplication.helper.AppDbHelper;

@Database(entities = {SongEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase appDatabase;
    public static AppDatabase getInstance(Context context){
        if(appDatabase==null){
            synchronized (AppDatabase.class){
                if(appDatabase==null){
                    appDatabase= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME).addMigrations().build();
                }
            }
        }
        return appDatabase;
    }
    private static final String DB_NAME="app.db";
    public abstract SongDao SongDao();

}
