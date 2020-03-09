package com.udacity.popularmovies.database;

import android.content.Context;

import com.udacity.popularmovies.utilies.SampleData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static  AppRepository ourInstance;

    public List<MovieEntity> mMovies;
    private AppDatabase mDb;

/*    All room database operations must be executed in a background thread
    One executor use for all db so that it has a queue if there are multiple db request*/
    private Executor executor = Executors.newSingleThreadExecutor();


    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mMovies = SampleData.getSampleMovieData();
        mDb = AppDatabase.getInstance(context);
    }

    // TODO: May need to remove once actual movie data is saved to database
    // Check if can be re-used for actual data
    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insertAll(SampleData.getSampleMovieData());
            }
        });
    }
}
