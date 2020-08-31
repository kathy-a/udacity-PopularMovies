package com.udacity.popularmovies.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.udacity.popularmovies.DetailsActivity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static  AppRepository ourInstance;

    public LiveData<List<MovieEntity>> mMovies;
    private AppDatabase mDb;
    private static final String TAG = "AppRepository";

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
        mDb = AppDatabase.getInstance(context);
        mMovies = getAllMovies();
        Log.d(TAG, "AppRepository: local data " + mMovies.getValue());
    }


    // Get data from DB
    private LiveData<List<MovieEntity>> getAllMovies(){
        return mDb.movieDao().getAll();
    }

    public void addMovieData() {

        final MovieEntity movie = DetailsActivity.getMovieDetails();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().insertMovie(movie);


            }
        });
    }

    public MovieEntity getMovieById(int movieId) {
        return mDb.movieDao().getMovieById(movieId);

    }

    public void deleteMovie(final MovieEntity movie) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().deleteMovie(movie);
            }
        });
    }
}
