package com.udacity.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.udacity.popularmovies.database.AppRepository;
import com.udacity.popularmovies.database.MovieEntity;

public class DetailViewModel extends AndroidViewModel {

    private AppRepository mRepository;

    public DetailViewModel(@NonNull Application application) {

        super(application);

        // Get data from repository instead of declaring in view model
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void addMovieData() {
        mRepository.addMovieData();

    }
}
