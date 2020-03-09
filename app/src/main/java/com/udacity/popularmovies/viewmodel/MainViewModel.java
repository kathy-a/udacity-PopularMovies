package com.udacity.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.udacity.popularmovies.database.AppRepository;
import com.udacity.popularmovies.database.MovieEntity;
import com.udacity.popularmovies.utilies.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<MovieEntity> movieData;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        // Get data from repository instead of declaring in view model
        mRepository = AppRepository.getInstance();
        movieData = mRepository.mMovies;

    }
}
