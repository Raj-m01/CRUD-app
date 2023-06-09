package com.example.crudapp.architecture;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.crudapp.PostModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PostModel.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase {

    private static PostDatabase instance;
    private static final String DB_NAME = "post_db";
    public static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public abstract PostDao postDao();

    public static synchronized PostDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            PostDatabase.class, DB_NAME)
                    .build();
        }
        return instance;
    }


}
