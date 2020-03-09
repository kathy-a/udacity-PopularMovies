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
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        movieData = mRepository.mMovies;

    }

    // TODO: May need to remove the method once actual movie data is used
    // Adding sample data in database.
    public void addSampleData() {
        mRepository.addSampleData();
    }
}
