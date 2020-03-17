package com.udacity.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    //.db not required but for easier searching of file
    public static final String DATABASE_NAME = "AppDatabase.db";

    // Set as volatile so that it can only be referenced from main memory.
    private static volatile AppDatabase instance;

    // Use synchronization to make sure that two parts of the application don't try to create the database at the same time.
    private static final Object LOCK = new Object();

    /**
     * Provide an abstract method that returns an instance of the interface.
     Abstract, because this method will never be called directly.
     Room database generate code from the background
     Only 1 method right now since there is currently 1 table.
     For future reference, each table should have each abstract Dao method
     *
     * @return instance of the interface
     */
    public abstract MovieDao movieDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }

        return instance;


    }
}
