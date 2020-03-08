package com.udacity.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.udacity.popularmovies.database.MovieEntity;
import com.udacity.popularmovies.utilies.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<MovieEntity> movieData = SampleData.getSampleMovieData();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
