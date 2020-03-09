package com.udacity.popularmovies.database;

import com.udacity.popularmovies.utilies.SampleData;

import java.util.List;

public class AppRepository {
    private static final AppRepository ourInstance = new AppRepository();

    public List<MovieEntity> mMovies;

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
        mMovies = SampleData.getSampleMovieData();
    }



}
